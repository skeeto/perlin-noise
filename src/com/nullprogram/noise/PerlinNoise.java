package com.nullprogram.noise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Random;

/**
 * Provides Perlin noise which can be sampled in O(1) at any location.
 */
public class PerlinNoise implements Noise {

    private final int dimension;

    private final Random rng;

    /** Use PRNG gradients for now, hashing some other time. */
    private final Map<Vector, Vector> gradients = new HashMap<>();

    /** Pre-calculated list of neighboring grid corner. */
    private final List<Vector> corners = new ArrayList<>();

    /**
     * Create new Perlin noise.
     * @param seed       PRNG seed
     * @param dimension  the dimension of the noise
     */
    public PerlinNoise(int seed, int dimension) {
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
    public double sample(Vector p) {
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
    private Vector gradient(Vector p) {
        Vector gradient = gradients.get(p);
        if (gradient == null) {
            double[] vector = new double[dimension];
            for (int i = 0; i < vector.length; i++) {
                vector[i] = rng.nextDouble();
            }
            gradient = new Vector(vector).unitize();
            gradients.put(p, gradient);
        }
        return gradient;
    }
}
