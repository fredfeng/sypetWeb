public static boolean test0() throws Throwable {

    java.lang.String uri_string = utopia.synthesized.util.Util.buildStringURI("http://128.83.122.134",9093,"cmd","reset");
    
    java.io.InputStream actual = sendHttpGet(uri_string);
    
    java.lang.String result = utopia.synthesized.util.Util.fromInputStreamToString(actual);
    java.lang.String target = "[UAV] Protocol= Get | Cmd= Reset";
	
	return (result.contains(target));	

}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
