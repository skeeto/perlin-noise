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
     * Compute the sum between this and another vector.
     * @param the other vector
     * @return a new vector with the element-by-element sum
     */
    public Vector add(Vector v) {
        check(v);
        double[] sum = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            sum[i] = vector[i] + v.vector[i];
        }
        return new Vector(false, sum);
    }

    /**
     * Compute Math.floor() on each element.
     * @return a new vector with each element floor()ed.
     */
    public Vector floor() {
        double[] floor = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            floor[i] = Math.floor(vector[i]);
        }
        return new Vector(false, floor);
    }

    /**
     * Compute Math.abs() on each element.
     * @return a new vector with each element abs()ed.
     */
    public Vector abs() {
        double[] abs = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            abs[i] = Math.abs(vector[i]);
        }
        return new Vector(false, abs);
    }

    /**
     * Compute Math.pow() on each element.
     * @param e  the exponent
     * @return a new vector with each element pow()ed.
     */
    public Vector pow(double e) {
        double[] pow = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            pow[i] = Math.pow(vector[i], e);
        }
        return new Vector(false, pow);
    }

    /**
     * Multiply each element by a scalar.
     * @param e  the scalar
     * @return a new vector
     */
    public Vector multiply(double s) {
        double[] mult = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            mult[i] = vector[i] * s;
        }
        return new Vector(false, mult);
    }

    /**
     * Add a scalar to each element.
     * @param s  the scalar
     * @return a new vector
     */
    public Vector add(double s) {
        double[] add = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            add[i] = vector[i] + s;
        }
        return new Vector(false, add);
    }

    /**
     * Calculate the product of the elements of this vector.
     * @return the product of the elements
     */
    public double prod() {
        double prod = 1;
        for (int i = 0; i < vector.length; i++) {
            prod *= vector[i];
        }
        return prod;
    }

    /**
     * Compute a unit vector for this vector.
     * @return a new vector of a magnitude of 1.0
     */
    public Vector unitize() {
        return multiply(1 / magnitude());
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
