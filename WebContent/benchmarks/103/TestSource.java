public static boolean test() throws Throwable {
    java.lang.String first = "ff";
    java.lang.String second = "ss";
    java.lang.String match = "aaabccdabddabofabb";
    java.util.regex.Pattern regex = java.util.regex.Pattern.compile("ab");
    java.lang.String res2 = Source.replaceDifferent(regex, first, second, match);
    return (res2.equals("aaffccdssddssofssb"));
}
