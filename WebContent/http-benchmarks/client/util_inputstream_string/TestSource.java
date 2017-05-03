public static boolean test0() throws Throwable {		

	java.lang.String source = "This is my stream";
	java.io.InputStream stream = new java.io.ByteArrayInputStream(source.getBytes());

	java.lang.String output = fromInputStreamToString(stream);
	
	return (source.equals(output));

}	

public static boolean test() throws Throwable {
		
    return test0();

} 
