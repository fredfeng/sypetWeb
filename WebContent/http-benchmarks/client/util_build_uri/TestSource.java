public static boolean test0() throws Throwable {		
 
    java.lang.String url = "http://127.0.0.1";
    int port = 8080;
    java.lang.String key = "cmd";
    java.lang.String value = "reset";

    java.net.URI uri = buildURI(url,port,key,value);
    
    return (uri.toString().equals("http://127.0.0.1:8080?cmd=reset"));

}

public static boolean test1() throws Throwable {		
 
    java.lang.String url = "http://127.0.0.1";
    int port = 8080;
    java.lang.String key = "waypoint";
    java.lang.String value = "(1.0,2.0)";

    java.net.URI uri = buildURI(url,port,key,value);
    
    return (uri.toString().equals("http://127.0.0.1:8080?waypoint=%281.0%2C2.0%29"));

}

public static boolean test() throws Throwable {
		
    return test0() && test1();

} 
