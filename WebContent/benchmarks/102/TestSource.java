public static boolean test() throws Throwable {
	java.awt.geom.Area a1 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(10, 20, 10, 2));
	java.awt.geom.Area a2 = new java.awt.geom.Area(new java.awt.geom.Rectangle2D.Double(16, 21, 2, 10));
	java.awt.geom.Point2D p = new java.awt.geom.Point2D.Double(10, 20);
	java.awt.geom.Area a3 = translateAndRotate(a1, 8, 1, p, Math.PI/2);
	return a2.equals(a3);
}