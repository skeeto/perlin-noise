package com.nullprogram.noise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Provides Perlin noise which can be sampled in O(1) at any location.
 */
public final class PerlinNoise implements Noise {

    /** The dimension of this noise. */
    private final int dimension;

    /** The PRNG for gradient generation (for now). */
    private final Random rng;

    /** Cache computed gradients. */
    private final Map<Vector, Vector> gradients = new HashMap<Vector, Vector>();

    /** Pre-calculated list of neighboring grid corner. */
    private final List<Vector> corners = new ArrayList<Vector>();

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
            corners.add(new Vector(v));
        }
    }

    @Override
    public double sample(final Vector p) {
        double sum = 0;
        Vector pfloor = p.floor();
        for (Vector c : corners) {
            Vector q = pfloor.add(c);
            Vector g = gradient(q);
            double m = g.dot(p.subtract(q));
            Vector t = p.subtract(q).abs().multiply(-1).add(1);
            Vector w = t.pow(2).multiply(3).subtract(t.pow(3).multiply(2));
            sum += w.prod() * m;
        }
        return sum;
    }

    /**
     * Calculate the gradient at a given point.
     * @param p  the grid point
     * @return the gradient
     */
    private Vector gradient(final Vector p) {
        Vector gradient = gradients.get(p);
        if (gradient == null) {
            double[] vector = new double[dimension];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = rng.nextDouble() - 0.5;
            }
            gradient = new Vector(vector).unitize();
            gradients.put(p, gradient);
        }
        return gradient;
    }
}
