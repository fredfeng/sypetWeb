public static boolean test0() throws Throwable {

	java.lang.String uri = "http://128.83.122.134:9093?waypoint=";
	java.lang.String body = "(1.0,2.0)";
	java.lang.String result = sendHttpPost(uri,body);
	java.lang.String server = "[UAV] Protocol= Post | Cmd= AddWaypoint";

    return (result.contains(server) && result.contains(body));	

}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
