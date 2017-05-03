public static boolean test0() throws Throwable {
		
    if (daysBetweenDates("2013/08/14", "2013/10/03","yyyy/MM/dd") == 50)
	return true;
    else
	return false;
}
	
public static boolean test1() throws Throwable {
				
    if (daysBetweenDates("2013/08/14", "2014/04/03","yyyy/MM/dd") == 232)
	return true;
    else
	return false;
}
	

public static boolean test() throws Throwable {
		
    return test0() && test1();

} 
