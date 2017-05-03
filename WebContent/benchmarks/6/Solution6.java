
public class Solution6 {

  public static double[] solveLinear(
        double[][] arg0,
        double[] arg1)
    {
        org.apache.commons.math3.linear.RealMatrix v1 = org.apache.commons.math3.linear.MatrixUtils.createRealMatrix(arg0);
        org.apache.commons.math3.linear.LUDecomposition v2 = new org.apache.commons.math3.linear.LUDecomposition(v1);
        org.apache.commons.math3.linear.DecompositionSolver v3 = v2.getSolver();
        org.apache.commons.math3.linear.RealVector v4 = org.apache.commons.math3.linear.MatrixUtils.createRealVector(arg1);
        org.apache.commons.math3.linear.RealVector v5 = v3.solve(v4);
        double[] v6 = v5.toArray();
        return v6;
    }

}
