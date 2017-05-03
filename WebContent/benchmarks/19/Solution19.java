
public class Solution19 {

    public static int getDayFromString(
        java.lang.String arg0,
        java.lang.String arg1)
    {
        org.joda.time.format.DateTimeFormatter v1 = org.joda.time.format.DateTimeFormat.forPattern(arg1);
        org.joda.time.LocalDateTime v2 = org.joda.time.LocalDateTime.parse(arg0, v1);
        int v3 = v2.getDayOfMonth();
        return v3;
    }

}
