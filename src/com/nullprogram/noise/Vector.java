package com.nullprogram.noise;

import java.io.Serializable;
import java.lang.StringBuilder;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import net.jcip.annotations.Immutable;

/**
 * Represents a vector or point of arbitrary dimension. This class is
 * immutable and therefore thread-safe.
 */
@Immutable
@EqualsAndHashCode
@SuppressWarnings("serial")
public final class Vector implements Serializable, Cloneable {

    /** The actual vector. */
    private final double[] vector;

    /**
     * Create a new vector from values.
     * @param values  the values of the new vector
     */
    public Vector(double... values) {
        vector = values.clone();
    }

    /**
     * Hidden constructor so we can create vectors without making the
     * defensive copy. Shhh!
     * @param clone   true if a defensive copy should be made
     * @param values  the array to be used directly (if clone is false)
     */
    private Vector(boolean clone, double[] values) {
        if (!clone) {
            vector = values;
        } else {
            vector = values.clone();
        }
    }

    /**
     * Get a value from this vector.
     * @param index  the index to query
     * @return the value at the index
     */
    public double get(int index) {
        return vector[index];
    }

    /**
     * Return the dimension of this vector.
     * @return the dimension of this vector
     */
    public int dimension() {
        return vector.length;
    }

    /**
     * Compute the magnitude of this vector.
     * @return the magnitude of this vector
     */
    public double magnitude() {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i] * vector[i];
        }
        return Math.sqrt(sum);
    }

    /**
     * Compute the dot product of this vector with another.
     * @param v  the other vector
     * @return the dot product
     */
    public double dot(Vector v) {
        check(v);
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i] * v.vector[i];
        }
        return sum;
    }

    /**
     * Compute a difference vector between this and another vector.
     * @param the other vector
     * @return a new vector with the difference
     */
    public Vector subtract(Vector v) {
        check(v);
        double[] diff = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            diff[i] = vector[i] - v.vector[i];
        }
        return new Vector(false, diff);
    }

    /**
     * Returns this object, as Vector is immutable.
     * @return this object
     */
    @Override
    public Object clone() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < vector.length; i++) {
            str.append(vector[i]);
            str.append(" ");
        }
        if (vector.length > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        str.append("]");
        return str.toString();
    }

    /**
     * Check that this vector length matches the other.
     * @param v  the other vector
     */
    private void check(Vector v) {
        if (vector.length != v.vector.length) {
            throw new IllegalArgumentException("Vector lengths must match");
        }
    }
}
