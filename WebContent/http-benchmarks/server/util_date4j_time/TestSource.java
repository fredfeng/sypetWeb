public static boolean test0() throws Throwable {

	java.lang.String output = getHTTPTime(31,"DD MM YYYY","GMT",java.util.Locale.US);
    
    java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT"), java.util.Locale.US);
    calendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
    int days = calendar.get(java.util.Calendar.DAY_OF_MONTH);
    java.lang.String expected = Integer.toString(days+31-28) + " 03 2017";

    return output.equals(expected);
}

public static boolean test() throws Throwable {

    return test0();
}
