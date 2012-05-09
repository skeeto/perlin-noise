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
        Vector v = new Vector(3.0, 2.1);
        assertEquals(v.get(0), 3.0, 1e-15);
        assertEquals(v.get(1), 2.1, 1e-15);
    }

    @Test
    public void equalsTest() {
        Vector a = new Vector(1.1, 2.5, 0.9);
        Vector b = new Vector(1.1, 2.5, 0.9);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void cloneTest() {
        Vector a = new Vector(-1.0, 2.0, 9.9);
        Vector b = (Vector) a.clone();
        assertEquals(a, b);
    }

    @Test
    public void dotTest() {
        Vector a = new Vector( 4.2, -1.7, 3.9);
        Vector b = new Vector(-5.6, -4.0, 4.0);
        assertEquals(-1.12, a.dot(b), 1e-15);
    }

    @Test
    public void subtractTest() {
        Vector a = new Vector(9.3181,  4.5509,  5.3475);
        Vector b = new Vector(9.2859,  8.9444,  5.6423);
        Vector result = a.subtract(b);
        double delta = 1e-15;
        assertEquals( 0.0322, result.get(0), delta);
        assertEquals(-4.3935, result.get(1), delta);
        assertEquals(-0.2948, result.get(2), delta);
    }

    @Test
    public void magnitudeTest() {
        Vector v = new Vector(3.43178, 0.27874, 4.32300);
        assertEquals(5.52658474611581, v.magnitude(), 1e-14);
    }
}
