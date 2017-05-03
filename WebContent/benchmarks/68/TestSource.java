public static boolean test0() throws Throwable {
		
    if (getHoursWithZoneBetweenCities("Europe/Lisbon","Europe/Paris") == -1)
	return true;
    else
	return false;
}
	
public static boolean test1() throws Throwable {
				
    if (getHoursWithZoneBetweenCities("Europe/Lisbon","America/New_York") == 5)
	return true;
    else
	return false;
}
	

public static boolean test() throws Throwable {
				
    return test0() && test1();

}
