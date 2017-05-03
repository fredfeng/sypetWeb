
public class TestSource6 {
public static boolean test0() throws Throwable {
    double[] result = Solution6.solveLinear(new double[][] {{3,-1},{1,1}}, new double[] {3,5});
    return Util.dveq(result, new double[]{2, 3});
}

public static boolean test1() throws Throwable {
    double[] result = Solution6.solveLinear(new double[][]{{1,2,1},{2,-1,3},{3,1,2}}, new double[]{7,7,18});
    return Util.dveq(result, new double[]{7,1,-2});
}

public static boolean test() throws Throwable {
    return test0() && test1();
}
}