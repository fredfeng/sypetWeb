public static boolean test0() throws Throwable {		
    
    String uri = "http://128.83.122.134:9093?cmd=reset";
	java.lang.String result = sendHttpGet(uri);
	java.lang.String target = "[UAV] Protocol= Get | Cmd= Reset";
	    
    return (result.contains(target));
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
