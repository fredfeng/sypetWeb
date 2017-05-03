public static boolean test() throws Throwable {
    org.joda.time.Chronology from = org.joda.time.chrono.GregorianChronology.getInstanceUTC();
    org.joda.time.Chronology to = org.joda.time.chrono.GJChronology.getInstanceUTC();
    long instant = from.getDateTimeMillis( 2015, 1, 1, 300);
    long res = to.getDateTimeMillis( 2015, 1, 1, 300);
    if (res == Source.convertByYear(instant, from, to))
	return true;
    else 
	return false;
}