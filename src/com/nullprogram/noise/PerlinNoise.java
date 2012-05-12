package com.nullprogram.noise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Abs;
import org.apache.commons.math3.analysis.function.Floor;
import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/**
 * Provides Perlin noise which can be sampled in O(1) at any location.
 */
public final class PerlinNoise implements Noise {

    private static final UnivariateFunction FLOOR = new Floor();
    private static final UnivariateFunction ABS = new Abs();
    private static final UnivariateFunction POW2 = new Power(2);
    private static final UnivariateFunction POW3 = new Power(3);

    /** The dimension of this noise. */
    private final int dimension;

    /** The PRNG for gradient generation (for now). */
    private final Random rng;

    /** Cache computed gradients. */
    private final Map<RealVector, RealVector> gradients =
        new HashMap<RealVector, RealVector>();

    /** Pre-calculated list of neighboring grid corner. */
    private final List<RealVector> corners = new ArrayList<RealVector>();

    /**
     * Create new Perlin noise.
     * @param seed       PRNG seed
     * @param dimension  the dimension of the noise
     */
    public PerlinNoise(final int seed, final int dimension) {
        this.rng = new Random(seed);
        this.dimension = dimension;

        /* Pre-calulate vectors to find corners. */
        for (int i = 0; i < 1 << dimension; i++) {
            double[] v = new double[dimension];
            for (int n = 0; n < dimension; n++) {
                if ((i & (1 << n)) != 0) {
                    v[n] = 1;
                } else {
                    v[n] = 0;
                }
            }
            corners.add(new ArrayRealVector(v));
        }
    }

    @Override
    public double sample(final RealVector p) {
        double sum = 0;
        RealVector pfloor = p.map(FLOOR);
        for (RealVector c : corners) {
            RealVector q = pfloor.add(c);
            RealVector g = gradient(q);
            double m = g.dotProduct(p.subtract(q));
            RealVector t = p.subtract(q).map(ABS).mapMultiply(-1.0).mapAdd(1.0);
            RealVector w = t.map(POW2).mapMultiply(3)
                .subtract(t.map(POW3).mapMultiply(2));
            double prod = 1;
            for (int i = 0; i < w.getDimension(); i++) {
                prod *= w.getEntry(i);
            }
            sum += prod * m;
        }
        return sum;
    }

    /**
     * Calculate the gradient at a given point.
     * @param p  the grid point
     * @return the gradient
     */
    private RealVector gradient(final RealVector p) {
        RealVector gradient = gradients.get(p);
        if (gradient == null) {
            double[] vector = new double[dimension];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = rng.nextDouble() - 0.5;
            }
            gradient = new ArrayRealVector(vector);
            gradient.unitize();
            gradients.put(p, gradient);
        }
        return gradient;
    }
}
