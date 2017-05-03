public static boolean test0() throws Throwable {		

    java.net.URI uri = utopia.synthesized.util.Util.buildURI("http://128.83.122.134",9093,"waypoint","(1.0,2.0)");
    java.lang.String request = com.jcabi.http.Request.POST;
    
    java.lang.String result = sendHttpPost(uri, request);
    
    java.lang.String cmd = "[UAV] Protocol= Post | Cmd= AddWaypoint";
    java.lang.String point = "(1.0,2.0)";

	return (result.contains(cmd) && result.contains(point));		
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
