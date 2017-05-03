public static boolean test0() throws Throwable {

    java.lang.String uri_string = utopia.synthesized.util.Util.buildStringURI("http://128.83.122.134",9093,"waypoint","(1.0,2.0)");
    
    java.io.InputStream output = sendHttpPost(uri_string);
    java.lang.String result = utopia.synthesized.util.Util.fromInputStreamToString(output);	
    
    java.lang.String cmd = "[UAV] Protocol= Post | Cmd= AddWaypoint";
    java.lang.String point = "(1.0,2.0)";
	
	return (result.contains(cmd) && result.contains(point));	
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
