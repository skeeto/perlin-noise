package com.nullprogram.noise;

import org.junit.Test;
import static org.junit.Assert.*;

public class VectorTest {

    @Test(expected = IllegalArgumentException.class)
    public void lengthTest() {
        Vector a = new Vector(0, 1);
        Vector b = new Vector(2, 4, 5);
        a.dot(b);
    }

    @Test
    public void getTest() {
        Vector v = new Vector(3.0f, 2.1f);
        assertEquals(v.get(0), 3.0, 1e-7);
        assertEquals(v.get(1), 2.1, 1e-7);
    }

    @Test
    public void equalsTest() {
        Vector a = new Vector(1.1f, 2.5f, 0.9f);
        Vector b = new Vector(1.1f, 2.5f, 0.9f);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void cloneTest() {
        Vector a = new Vector(-1.0f, 2.0f, 9.9f);
        Vector b = (Vector) a.clone();
        assertEquals(a, b);
    }

    @Test
    public void dotTest() {
        Vector a = new Vector( 4.2f, -1.7f, 3.9f);
        Vector b = new Vector(-5.6f, -4.0f, 4.0f);
        assertEquals(-1.12, a.dot(b), 1e-5);
    }

    @Test
    public void subtractTest() {
        Vector a = new Vector(9.3181f,  4.5509f,  5.3475f);
        Vector b = new Vector(9.2859f,  8.9444f,  5.6423f);
        Vector result = a.subtract(b);
        double delta = 1e-6;
        assertEquals( 0.0322, result.get(0), delta);
        assertEquals(-4.3935, result.get(1), delta);
        assertEquals(-0.2948, result.get(2), delta);
    }

    @Test
    public void addTest() {
        Vector a = new Vector(9.3181f,  4.5509f,  5.3475f);
        Vector b = new Vector(9.2859f,  8.9444f,  5.6423f);
        Vector result = a.add(b);
        double delta = 1e-6;
        assertEquals(18.6040, result.get(0), delta);
        assertEquals(13.4953, result.get(1), delta);
        assertEquals(10.9898, result.get(2), delta);
    }

    @Test
    public void floorTest() {
        Vector v = new Vector(2.1388f, -42.1785f, 17.0530f);
        Vector floor = v.floor();
        double delta = 1e-7;
        assertEquals(  2.0, floor.get(0), delta);
        assertEquals(-43.0, floor.get(1), delta);
        assertEquals( 17.0, floor.get(2), delta);
    }

    @Test
    public void absTest() {
        Vector v = new Vector(2.1f, -42.1f, 17.0f);
        Vector abs = v.abs();
        double delta = 1e-5;
        assertEquals( 2.1, abs.get(0), delta);
        assertEquals(42.1, abs.get(1), delta);
        assertEquals(17.0, abs.get(2), delta);
    }

    @Test
    public void powTest() {
        Vector v = new Vector(2.0f, -3.0f, 2.5f);
        Vector pow = v.pow(2.0f);
        double delta = 1e-7;
        assertEquals(4.0,  pow.get(0), delta);
        assertEquals(9.0,  pow.get(1), delta);
        assertEquals(6.25, pow.get(2), delta);
    }

    @Test
    public void multiplyTest() {
        Vector v = new Vector(2.0f, -3.0f, 2.5f);
        Vector mult = v.multiply(2.0f);
        double delta = 1e-7;
        assertEquals( 4.0, mult.get(0), delta);
        assertEquals(-6.0, mult.get(1), delta);
        assertEquals( 5.0, mult.get(2), delta);
    }

    @Test
    public void scalarAddTest() {
        Vector v = new Vector(2.0f, -3.0f, 2.5f);
        Vector add = v.add(2.5f);
        double delta = 1e-7;
        assertEquals( 4.5, add.get(0), delta);
        assertEquals(-0.5, add.get(1), delta);
        assertEquals( 5.0, add.get(2), delta);
    }

    @Test
    public void productTest() {
        Vector v = new Vector(2.5f, 3.0f, -3.5f);
        float prod = v.prod();
        assertEquals(2.5 * 3.0 * -3.5, prod, 1e-7);
    }

    @Test
    public void magnitudeTest() {
        Vector v = new Vector(3.43178f, 0.27874f, 4.32300f);
        assertEquals(5.52658474611581, v.magnitude(), 1e-6);
    }

    @Test
    public void unitizeTest() {
        Vector v = new Vector(5.703441f, 8.076563f, 7.470319f);
        assertEquals(1.0, v.unitize().magnitude(), 1e-7);
    }
}
