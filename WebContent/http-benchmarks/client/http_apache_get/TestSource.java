public static boolean test0() throws Throwable {		
    
    java.net.URI uri = utopia.synthesized.util.Util.buildURI("http://128.83.122.134",9093,"cmd","reset");
    org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(uri);
    
    java.io.InputStream is = sendHttpGet(httpGet);
    
    java.lang.String result = utopia.synthesized.util.Util.fromInputStreamToString(is);
    java.lang.String target = "[UAV] Protocol= Get | Cmd= Reset";
    
    return (result.contains(target));   
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
