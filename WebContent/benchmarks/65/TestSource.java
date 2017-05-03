public static boolean test() throws Throwable {
    String url = "https://www.google.com/";
    String text = Source.toText(url);
    if (text.contains("Google Search Images"))
        return true;
    else 
        return false;
}
