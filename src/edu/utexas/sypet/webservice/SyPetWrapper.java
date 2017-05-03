package edu.utexas.sypet.webservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import edu.utexas.sypet.synthesis.NlpService;
import edu.utexas.sypet.synthesis.PathFinder;
import edu.utexas.sypet.synthesis.Sketcher;
import edu.utexas.sypet.synthesis.SypetTestUtil;
import edu.utexas.sypet.synthesis.model.Benchmark;
import edu.utexas.sypet.synthesis.model.Pair;
import edu.utexas.sypet.synthesis.sat4j.PetrinetEncoding.Option;
import edu.utexas.sypet.synthesis.test.Cli;
import edu.utexas.sypet.util.SootUtil;
import edu.utexas.sypet.util.TimeUtil;
import edu.utexas.sypet.util.Util;
import soot.CompilationDeathException;
import soot.Scene;
import soot.options.Options;
import uniol.apt.adt.pn.PetriNet;
import uniol.apt.adt.pn.Place;

public class SyPetWrapper {

	public static String root = "";
	public static boolean VERBOSE = false;
	public static String benchLoc = null;
	public static long TIMEOUT = 600000;
	public static boolean enableNlpService = true;
	
	//true if we want Pre-pruning
	public static boolean prePruning = true;
	
	//true if we want backward pruning
	public static boolean pruning = false;
	//pruning up to K.
	public static int K = 10;

	public static final int maxTokens = 10;

	//public static final int maxTimeline = 3;

	protected static NlpService service = new NlpService();

	protected static List<Pair<String, Integer>> nlplist = new ArrayList<>();
	
	// More options
	public static int nlpLimit = 50;
	public static int nlptop = -1;
	
	public static Option objectiveOption = Option.AT_LEAST_ONE;
	public static int maxIterations = 5;
	
	public static List<String> clones;
	
	public static PathFinder initPetriNet(Benchmark qb, List<PetriNet> pNetList, int pn, int local){
		
		if (pNetList.size() == 1){
			pn = 0;
		}
		
		PetriNet pNet = pNetList.get(pn);
		
		if (!pruning)
			assert pNetList.size() == 1;
		
		System.out.println("PetriNet for path length: " + local + " [places: " + pNet.getPlaces().size()
				+ " ; transitions: " + pNet.getTransitions().size() + " ; edges: " + pNet.getEdges().size() + "]");

		List<Place> inits = new ArrayList<>();
		List<Pair<String, String>> vars = new ArrayList<>();
		int index = 0;
		for (String src : qb.getSrcTypes()) {
			Place srcPlace = pNet.getPlace(src);
			inits.add(srcPlace);
			String varName = qb.getParamNames().get(index);
			Pair<String, String> arg = new Pair<>(src, varName);
			vars.add(arg);
			index++;
		}
		// adding void to initial marking.
		inits.add(pNet.getPlace("void"));
		// tgt place.
		String tgt = qb.getTgtType();
		Place tgtPlace = pNet.getPlace(tgt);
		
		
		PathFinder pf = new PathFinder(pNet, inits, tgtPlace, local, maxTokens, nlplist, clones, 
				objectiveOption, maxIterations);
		pf.setVars(vars);
		pf.setTgt(tgt);
		return pf;
	
	}

	
	public static String callSyPet(String[] args) throws FileNotFoundException {
		long startSoot = System.nanoTime();
		int roundRobinPosition = 0;
		int roundRobinIterations = 0;
		int roundRobinIterationsLimit = 40;
		int roundRobinRange = 3;			
		boolean roundRobinFlag = true;
		
		Cli cmdOptions = new Cli(args);
		cmdOptions.parse();
		
		enableNlpService = cmdOptions.getNlp();
		VERBOSE = cmdOptions.getVerbose();
		TIMEOUT = cmdOptions.getTimeout();
		nlpLimit = cmdOptions.getNlpLimit();
		nlptop = cmdOptions.getNlpTop();
		//roundRobinIterationsLimit = cmdOptions.getRobinLimit();
		roundRobinFlag = cmdOptions.getRoundRobin();
		//roundRobinRange = cmdOptions.getRobinRange();
		//cmdOptions.getObjective();
		objectiveOption = objectiveOption.YUEPENG_WEIGHT;
		//objectiveOption = objectiveOption.AT_LEAST_ONE;
		maxIterations = cmdOptions.getSolverLimit();
		pruning = cmdOptions.getPruning();
		//K = cmdOptions.getPruningLimit();
		prePruning = cmdOptions.getPrePruning();
		
		cmdOptions.printOptions();
		
		benchLoc = cmdOptions.getFilename(); 
		if (! new File(benchLoc).exists()) {
			System.out.println("Cannot find json file: " + benchLoc);
			System.exit(2);
		}

		double timeGetPath = 0;
		double timeInitSketch = 0;
		double timeFillHoles = 0;
		double timeCompilation = 0;
		double timeRunTest = 0;
		double timeTotal = 0;
		long cntFillHoles = 0;
		Gson gson = new Gson();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(benchLoc));
			Benchmark qb = gson.fromJson(br, Benchmark.class);
			List<String> actLib = new ArrayList<>();
			for (String lib : qb.getLibs()) {
				String libPath = new File(root + "jars/", lib).getPath();
				if (! new File(libPath).exists()) {
					System.out.println("Cannot find lib file: " + libPath);
					System.exit(2);
				}
				actLib.add(libPath);
			}
			qb.setLibs(actLib);
			// generate the method header
			qb.setMethodHeader(Util.genMethodHeader(qb));
			// generate the test string
			qb.setTestBody(genTest(qb));

			/////////////////////////////////////////////////////////////////////////////////
			System.out.println("----------" + benchLoc);
			System.out.println("Benchmark Id: " + qb.getId());
			System.out.println("Method name: " + qb.getMethodName());
			System.out.println("Packages: " + qb.getPackages());
			System.out.println("Libraries: " + qb.getLibs());
			System.out.println("Source type(s): " + qb.getSrcTypes());
			System.out.println("Target type: " + qb.getTgtType());
			System.out.println("--------------------------------------------------------");

			///////////////////////////////////////////////////
			Set<String> pkgs = qb.getPackages();
			String keyword = qb.getMethodName();

			StringBuilder options = new StringBuilder();
			options.append("-prepend-classpath");
			options.append(" -full-resolver");
			options.append(" -allow-phantom-refs");
			StringBuilder cp = new StringBuilder();
			for (String lib : qb.getLibs()) {
				cp.append(lib);
				cp.append(":");
				options.append(" -process-dir " + lib);
			}

			options.append(" -cp " + cp.toString());

			if (!Options.v().parse(options.toString().split(" "))) {
//				throw new CompilationDeathException(CompilationDeathException.COMPILATION_ABORTED,
//						"Option parse error");
			}

			Scene.v().loadBasicClasses();
			Scene.v().loadNecessaryClasses();

			List<PetriNet> pNetList = new ArrayList<>();
			
//			SootUtil.genDepGraph(qb.getLibs(), pkgs, qb.getTgtType());
			//FIXME: get lower bound. Will place with shortest path.
//			int low = Math.max(1, SootUtil.getLowerBound(qb));
			int low = 1;
			
			if (pruning) {
				assert K >= low;
				for (int i = low; i <= K; i++) {
					PetriNet petriNet = new PetriNet();
					SootUtil.updateReachableTypes(qb.getTgtType(), i);
					for (String lib : qb.getLibs()) {
						SootUtil.processJar(lib, pkgs, petriNet);
					}
					if (VERBOSE) {
//						System.out.println("lower bound: " + low);
//						System.out.println("Petri Net " + i + "-------------------");
//						System.out.println("Places: " + petriNet.getPlaces().size());
//						System.out.println("Transitions: " + petriNet.getTransitions().size());
//						System.out.println("Edges: " + petriNet.getEdges().size());
					}
					pNetList.add(petriNet);
				}
//				assert false : "Please integrate with pruning with round robin.";
			} else {
				PetriNet pNet = new PetriNet();
				//only one petrinet without pruning.
				for (String lib : qb.getLibs()) {
					SootUtil.processJar(lib, pkgs, pNet);
				}
				SootUtil.handlePolymorphism(pNet);
				System.out.println("#Classes: " + SootUtil.classNum);
				System.out.println("#Methods: " + SootUtil.methodNum);
				long endSoot = System.nanoTime();
				double sootTime = TimeUtil.computeTime(startSoot, endSoot);
				System.out.println("Soot Time: " + sootTime);
				pNetList.add(pNet);
			}

			service.init(pkgs);
			service.setCutoff(nlpLimit);
			if (enableNlpService) {
				//nlplist = service.query(keyword);
				String keyword_lowercase = keyword.toLowerCase();
				nlplist = service.basicQuery(keyword_lowercase, qb, pNetList.get(0).getTransitions());
			}
			
			//System.out.println("nlplist size = " + nlplist.size());
			
			if (VERBOSE) {
				for (Pair<String, Integer> p : nlplist) {
					System.out.println("nlp: " + p);
				}
			}
				//nlplist.clear();
			
			if (nlplist.size() > nlptop && nlptop > 0) {
				nlplist.subList(nlptop, nlplist.size());
			}
			
			// Multiple args: check if we need put clone as initial constraints.
			clones = SootUtil.getClones(qb.getSrcTypes());

				int petriIterator = 0;
				
				//System.out.println("Petri nets: " + pNetList.size());

				int cnt = 0;
				//int localMax = 1 + clones.size();
				//System.out.println("Lower bound: " + low);
				int localMax = low;
				boolean flag = false;
				long start0 = System.nanoTime();

				assert (roundRobinRange < 7 );				
				ArrayList<PathFinder> roundRobin = new ArrayList<>();
				if (roundRobinFlag) {
					for (int i = 0; i < roundRobinRange; i++) {
						roundRobin.add(initPetriNet(qb, pNetList, petriIterator++, localMax++));							
					}
				}

				while (!flag) {
					PathFinder pf;
					if (roundRobinFlag) {
						pf = roundRobin.get(roundRobinPosition);
						//if (VERBOSE)
							//System.out.println("Searching with local max: " + pf.getEncoding().getTimeline());
					} else {
//						pf = new PathFinder(pNet, inits, tgtPlace, localMax, maxTokens, nlplist, clones,
//								objectiveOption, maxIterations);
						if (pNetList.size() > 1) assert (petriIterator < pNetList.size());
						pf = initPetriNet(qb, pNetList, petriIterator++, localMax);
					}

					if (VERBOSE && !roundRobinFlag)
						System.out.println("Searching with local max: " + localMax);
					while (roundRobinIterations < roundRobinIterationsLimit || !roundRobinFlag) {
						long start1 = System.nanoTime();
						List<String> res = pf.get();
						long end1 = System.nanoTime();
						timeGetPath += TimeUtil.computeTime(start1, end1);
						if (VERBOSE)
							TimeUtil.reportTime(start1, end1, "get path: ");
						if (VERBOSE)
							System.out.println("call SAT." + cnt + " val: " + res);

						if (res.isEmpty())
							break;

						cnt++;

						List<String> solution = new ArrayList<>();
						for (String meth : res) {
							if (meth.startsWith("sypet_clone_"))
								continue;

							solution.add(meth);
						}
//						System.out.println("current sketch:" + res);
						// init sketcher.
						long start2 = System.nanoTime();
						Sketcher sk = new Sketcher(solution, pf.getVars(), pf.getTgt());
						boolean hasSketch = sk.initSketch();
						long end2 = System.nanoTime();
						timeInitSketch += TimeUtil.computeTime(start2, end2);
						if (VERBOSE)
							System.out.println("#holes: " + sk.getHolesNum());
						if (VERBOSE)
							TimeUtil.reportTime(start2, end2, "init sketch: ");
						while (true) {
							++cntFillHoles;
							long start3 = System.nanoTime();
							String snippet = sk.fillHoles();
							long end3 = System.nanoTime();
							timeFillHoles += TimeUtil.computeTime(start3, end3);
							if (VERBOSE)
								TimeUtil.reportTime(start3, end3, "fill hole: ");
							if (snippet.equals("UNSAT"))
								break;

							// invoke yuepeng's method.
							if (VERBOSE)
								System.out.println("snippet:" + snippet);
							qb.setBody(snippet);
							boolean passTest = SypetTestUtil.runTest(qb);
							timeCompilation += SypetTestUtil.getCompilationTime();
							timeRunTest += SypetTestUtil.getRunningTime();
							if (VERBOSE)
								System.out.println("Test result-------------" + passTest);
							if (VERBOSE)
								System.out.println("Compilation Time: " + SypetTestUtil.getCompilationTime());
							if (VERBOSE)
								System.out.println("Running Time: " + SypetTestUtil.getRunningTime());
							long end0 = System.nanoTime();
							// note: this should be = instead of +=
							timeTotal = TimeUtil.computeTime(start0, end0);
							if (passTest) {
								System.out.println("=========Statistics (time in milliseconds)=========");
								System.out.println("Benchmark Id: " + qb.getId());
								System.out.println("Sketch Generation Time: " + timeGetPath);
								System.out.println("Sketch Completion Time: " + (timeInitSketch + timeFillHoles));
								System.out.println("Compilation Time: " + timeCompilation);
								System.out.println("Running Test cases Time: " + timeRunTest);
								System.out.println("Synthesis Time: "
										+ (timeGetPath + timeInitSketch + timeFillHoles + timeRunTest));
							    System.out.println("Total Time: "
									+ (timeGetPath + timeInitSketch + timeFillHoles + timeRunTest + timeCompilation));
								System.out.println("Number of components: " + res.size());
								System.out.println("Number of holes: " + sk.getHolesNum());
								System.out.println("Number of completed programs: " + cntFillHoles);
								System.out.println("Number of sketches: " + cnt);
								String sol = snippet.replace(";", ";\n ");
								System.out.println("Solution:\n " + sol);
								System.out.println("============================");
								return getCompleteCode(qb,sol);
							} else if (timeTotal > TIMEOUT) {
								System.out.println("=========Statistics=========");
								System.out.println("Benchmark Id: " + qb.getId());
								System.out.println("Sketch Generation Time: " + timeGetPath);
								System.out.println("Sketch Completion Time: " + (timeInitSketch + timeFillHoles));
								System.out.println("Compilation Time: " + timeCompilation);
								System.out.println("Running Test cases Time: " + timeRunTest);
								System.out.println("Number of completed programs: " + cntFillHoles);
								System.out.println("Number of sketches: " + cnt);
								System.out.println("TIMEOUT after " + TIMEOUT + " ms");
								System.out.println("============================");
								return "";
							}
						}
						roundRobinIterations++;
					}
					if (roundRobinFlag) {
						if (roundRobinIterations != roundRobinIterationsLimit) {
//							roundRobin.set(roundRobinPosition, new PathFinder(pNet, inits, tgtPlace, localMax++,
//									maxTokens, nlplist, clones, objectiveOption, maxIterations));
							if (pNetList.size() > 1) assert (petriIterator < pNetList.size());
							roundRobin.set(roundRobinPosition, initPetriNet(qb, pNetList, petriIterator++, localMax++));
						}
						// roundRobinPosition = 1 - roundRobinPosition;
						roundRobinPosition = (roundRobinPosition + 1) % roundRobin.size();
						roundRobinIterations = 0;
					} else {
						localMax++;
					}
				}
				return "";
		} catch (CompilationDeathException e) {
			e.printStackTrace();
			if (e.getStatus() != CompilationDeathException.COMPILATION_SUCCEEDED)
				throw e;
			else
				return "";
		}
	}
	
	protected static String getCompleteCode(Benchmark bench, String body) {
		StringBuilder sb = new StringBuilder();
		sb.append(bench.getMethodHeader()).append(" {");
		sb.append(body);
		sb.append("}");
		return sb.toString();
	}

	protected static String genTest(Benchmark bench) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(root + bench.getTestPath()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
