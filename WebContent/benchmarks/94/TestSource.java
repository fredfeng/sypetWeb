public static boolean test() throws Throwable {
    java.lang.String xmlStr = "<MyXML id=\"pldi\">xml</MyXML>";
    java.lang.String tag = Source.getElementTagFromString(xmlStr);
    if(tag.equals("MyXML")) 
        return true;
    else 
        return false;
}
