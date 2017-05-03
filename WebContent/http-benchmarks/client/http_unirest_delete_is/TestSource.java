public static boolean test0() throws Throwable {		

	java.lang.String url = utopia.synthesized.util.Util.buildStringURI("http://128.83.122.134",9093,"delwaypoint","(1.0,2.0)");
    
    java.io.InputStream output = sendHttpDelete(url);
    
    java.lang.String result = utopia.synthesized.util.Util.fromInputStreamToString(output);
    java.lang.String cmd = "[UAV] Protocol= Delete | Cmd= DeleteWaypoint";
    java.lang.String point = "(1.0,2.0)";

    return (result.contains(cmd) && result.contains(point));
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
