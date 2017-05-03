public static boolean test0() throws Throwable {		
	
	java.net.URI uri = utopia.synthesized.util.Util.buildURI("http://128.83.122.134",9093,"cmd","reset");
	java.lang.String request = com.jcabi.http.Request.GET;

    java.lang.String actual = sendHttpGet(uri, request);

    java.lang.String target = "[UAV] Protocol= Get | Cmd= Reset";
	
	return (actual.contains(target));	
    
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
