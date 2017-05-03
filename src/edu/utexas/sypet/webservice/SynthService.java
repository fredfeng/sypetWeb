package edu.utexas.sypet.webservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import edu.utexas.sypet.synthesis.model.Benchmark;
import edu.utexas.sypet.util.Util;

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /sypet
@Path("/sypet")
public class SynthService {

	@Context
	private ServletContext context; 
	
	int[] innerIds = { 14, 15, 92, 18, 19, 91, 17, 113, 114, 104, 105, 106, 2, 85, 86, 112, 67, 107, 95, 97, 98, 111,
			36, 40, 41, 108, 56, 57, 109, 110 };
	
	String[] jars = { "joda-time-2.8.2.jar", "rt7.jar", "commons-math-2.2.jar", "commons-math3-3.5.jar",
			"og-analytics-2.17.0.jar", "commons-lang-2.6.jar", "jsoup-1.8.3.jar", "unirest.jar" };
	@POST
	@Path("/synth")
	public Response doSypet(		
			@FormParam("benchmark") String benchmark,
			@FormParam("pkg") String pkg) throws FileNotFoundException {

		String root = context.getRealPath("/");
		SyPetWrapper.root = root;

//		String ben = root + "benchmarks/" + innerId + "/benchmark" + innerId + ".json";
		String ben = root + benchmark;
		String param = "-file " + ben + " -n -r -pp";
		System.out.println("benchmark location:" + ben);
		System.out.println("param: " + param);

		String code = new SyPetWrapper().callSyPet(param.split(" "));
		return Response.status(200).entity(code).build();
	}
	
	@POST
	@Path("/profile")
	public Response doProfile(		
			@FormParam("benchmark") String benchmark) throws IOException {

		String root = context.getRealPath("/");
		SyPetWrapper.root = root;

		String ben = root + benchmark;
		System.out.println("Do profile.......benchmark location:" + ben);
		
		if (! new File(ben).exists()) {
			System.out.println("Cannot find json file: " + ben);
			System.exit(2);
		}
		
		Gson gson = new Gson();
		BufferedReader br;
		br = new BufferedReader(new FileReader(ben));
		Benchmark benchObj = gson.fromJson(br, Benchmark.class);
			
		System.out.println("Method head" + Util.genMethodHeader(benchObj));
		benchObj.setMethodHeader(Util.genMethodHeader(benchObj));
		System.out.println(benchObj.getSrcTypes());
		String testCode = new String(Files.readAllBytes(Paths.get(root + benchObj.getTestPath())));
		benchObj.setTestBody(testCode);

		System.out.println(testCode);
		System.out.println(benchObj.getLibs());
		System.out.println(benchObj.getPackages());

		return Response.status(200).entity(gson.toJson(benchObj)).build();
	}

}
