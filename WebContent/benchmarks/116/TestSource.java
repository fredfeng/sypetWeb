public static boolean test() throws Throwable {
	java.awt.geom.QuadCurve2D qc = new java.awt.geom.QuadCurve2D.Double(0, 0, 10, 10, 20, 0);
	java.awt.geom.Line2D target = new java.awt.geom.Line2D.Double(5, 5, 15, 5);
	java.awt.geom.Line2D result = ctrlPointLine(qc);
    return (Math.abs(result.getX1() - target.getX1()) < 1e-4)
            && (Math.abs(result.getY1() - target.getY1()) < 1e-4)
        && (Math.abs(result.getX2() - target.getX2()) < 1e-4)
        && (Math.abs(result.getY2() - target.getY2()) < 1e-4);
}
