import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class Solution {

    public static Line2D ctrlPointLine(QuadCurve2D arg0) {
        QuadCurve2D v1 = new QuadCurve2D.Double();
        QuadCurve2D v2 = new QuadCurve2D.Double();
        arg0.subdivide(v1, v2);
        Point2D v3 = v1.getCtrlPt();
        Point2D v4 = v2.getCtrlPt();
        Line2D v5 = new Line2D.Double(v3, v4);
        return v5;
    }

}
