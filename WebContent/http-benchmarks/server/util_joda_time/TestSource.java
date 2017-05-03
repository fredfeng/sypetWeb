public static boolean test0() throws Throwable {

    java.lang.String output = getHTTPTime(31,"dd MM yyyy");
    
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    int days = calendar.get(java.util.Calendar.DAY_OF_MONTH);
    java.lang.String expected = Integer.toString(days+31-28) + " 03 2017";

    return output.equals(expected);

}    

public static boolean test() throws Throwable {

    return test0();
    
}
