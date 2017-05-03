public static boolean test0() throws Throwable {		
	return (dayOfWeekOfMonth("2015/11", "yyyy/MM") == 7);	
}
	
public static boolean test1() throws Throwable {
	return (dayOfWeekOfMonth("2015/06", "yyyy/MM") == 1);					
}
	

public static boolean test() throws Throwable {
		
    return test0() && test1();

} 
