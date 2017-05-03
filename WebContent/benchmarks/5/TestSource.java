public static boolean test() throws Throwable {
    java.awt.geom.Line2D line1 = new java.awt.geom.Line2D.Double(0, 0, 2, 3);
    java.awt.geom.Line2D line2 = new java.awt.geom.Line2D.Double(1, 1, 5, 5);
    java.awt.geom.Line2D res = new java.awt.geom.Line2D.Double(0, 0, 5, 5);
    java.awt.geom.Line2D actualRes = Source.mergeLine(line1, line2);
    if (res.getP1().equals(actualRes.getP1()) && (res.getP2().equals(actualRes.getP2()))) return true;
    else return false;
}
