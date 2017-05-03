public static boolean test0() throws Throwable {		
    
    java.net.URI uri = utopia.synthesized.util.Util.buildURI("http://128.83.122.134",9093,"waypoint","(1.0,2.0)");
    org.apache.http.client.methods.HttpPost post = new org.apache.http.client.methods.HttpPost(uri);
    
    org.apache.http.client.methods.HttpPost output = setEntity(post, "This is my body!");

    java.lang.String body = utopia.synthesized.util.Util.getEntity(output);

    return (body.equals("This is my body!"));
}
	

public static boolean test() throws Throwable {
		
    return test0();

} 
