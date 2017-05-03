public static boolean test() throws Throwable {
    java.lang.String s = "Hello!!!";
    java.util.regex.Pattern regex = java.util.regex.Pattern.compile("[!]");
    boolean res = Source.isMatchInRegion(s, regex, 5, 7);

    if (res)
        return true;
    else 
        return false;
}
