import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Solution {

    public static Rectangle2D getIntersection(Ellipse2D arg0, Ellipse2D arg1) {
        java.awt.geom.Area v1 = new java.awt.geom.Area(arg1);
        java.awt.geom.Rectangle2D v2 = v1.getBounds2D();
        java.awt.geom.Area v3 = new java.awt.geom.Area(arg0);
        java.awt.geom.Rectangle2D v4 = v3.getBounds2D();
        java.awt.geom.Rectangle2D v5 = v4.createIntersection(v2);
        return v5;
    }

}
