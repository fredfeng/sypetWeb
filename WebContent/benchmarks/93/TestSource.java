public static boolean test() throws Throwable {
    
    final java.util.Random randomizer = new java.util.Random(53882150042L);
    final org.apache.commons.math3.distribution.RealDistribution rng = new org.apache.commons.math3.distribution.UniformRealDistribution(-100, 100);
    rng.reseedRandomGenerator(64925784252L);

    final double[] coeff = { 12.9, -3.4, 2.1 }; 
    final org.apache.commons.math3.analysis.polynomials.PolynomialFunction f = new org.apache.commons.math3.analysis.polynomials.PolynomialFunction(coeff);

    final org.apache.commons.math3.fitting.WeightedObservedPoints obs = new org.apache.commons.math3.fitting.WeightedObservedPoints();
    for (int i = 0; i < 100; i++) {
        final double x = rng.sample();
        obs.add(x, f.value(x) + 0.1 * randomizer.nextGaussian());
    }

    double[] initGuess = new double[] { -1e20, 3e15, -5e25 };
    org.apache.commons.math3.analysis.polynomials.PolynomialFunction pf = Source.fit(obs, initGuess);
    
    boolean flag = true;
    for (int i = 0; i < coeff.length; i++) {
        double delta = coeff[i] - pf.getCoefficients()[i];
        flag = flag && (delta < (2e-2));
    }

    if(flag) return true;
    else return false;
}
