public static boolean test0() throws Throwable {
	
    java.net.URI uri = utopia.synthesized.util.Util.buildURI("http://128.83.122.134",9093,"delwaypoint","(1.0,2.0)");
    java.lang.String request = com.jcabi.http.Request.DELETE;

    java.lang.String result = sendHttpDelete(uri, request);

    java.lang.String cmd = "[UAV] Protocol= Delete | Cmd= DeleteWaypoint";
    java.lang.String point = "(1.0,2.0)";

    return (result.contains(cmd) && result.contains(point));   
	
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
