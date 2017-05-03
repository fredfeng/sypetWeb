public static boolean test0() throws Throwable {		
    java.lang.String url = "http-benchmarks/server/util_file_list/uav.txt";
    java.io.File tmpFile = new java.io.File(url);
    boolean exists = tmpFile.exists();
    if (exists)
        tmpFile.delete();
    java.io.PrintWriter out = new java.io.PrintWriter(url);
    out.println("UAVs\nare\nawesome!");
    out.close();

    java.util.List actual = fileToList(url);
    
    java.util.List<java.lang.String> target = new java.util.ArrayList<java.lang.String>();
    target.add("UAVs");
    target.add("are");
    target.add("awesome!");

	return (actual.equals(target));	
}
	
public static boolean test() throws Throwable {
		
    return test0(); 

} 
