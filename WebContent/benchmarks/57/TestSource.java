public static boolean test() throws Throwable {
    org.w3c.dom.Document doc = readXML(new java.io.File("benchmarks/57/pldi.xml"));
    org.w3c.dom.Element e = (org.w3c.dom.Element) doc.getFirstChild();
    java.lang.String id = e.getAttribute("id");
    if(id.equals("pldi")) 
        return true;
    else 
        return false;
}
