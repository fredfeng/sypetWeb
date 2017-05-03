public static boolean test() throws Throwable {
    java.lang.String format = "mm/dd/yyyy";
    java.util.TimeZone timezone = java.util.TimeZone.getTimeZone("America/Los_Angeles");
    java.text.DateFormat res2 = Source.createDateFormat(format, timezone, java.util.Locale.US);
    java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat(format,new java.text.DateFormatSymbols(java.util.Locale.US));
    sdf.setTimeZone(timezone);
    java.text.DateFormat res = sdf;
    if(res.equals(res2))
        return true;
    else
        return false;
}
