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
    public void addTest() {
        Vector a = new Vector(9.3181,  4.5509,  5.3475);
        Vector b = new Vector(9.2859,  8.9444,  5.6423);
        Vector result = a.add(b);
        double delta = 1e-14;
        assertEquals(18.6040, result.get(0), delta);
        assertEquals(13.4953, result.get(1), delta);
        assertEquals(10.9898, result.get(2), delta);
    }

    @Test
    public void floorTest() {
        Vector v = new Vector(2.1388, -42.1785, 17.0530);
        Vector floor = v.floor();
        double delta = 1e-15;
        assertEquals(  2.0, floor.get(0), delta);
        assertEquals(-43.0, floor.get(1), delta);
        assertEquals( 17.0, floor.get(2), delta);
    }

    @Test
    public void absTest() {
        Vector v = new Vector(2.1, -42.1, 17.0);
        Vector abs = v.abs();
        double delta = 1e-15;
        assertEquals( 2.1, abs.get(0), delta);
        assertEquals(42.1, abs.get(1), delta);
        assertEquals(17.0, abs.get(2), delta);
    }

    @Test
    public void powTest() {
        Vector v = new Vector(2.0, -3.0, 2.5);
        Vector pow = v.pow(2.0);
        double delta = 1e-15;
        assertEquals(4.0,  pow.get(0), delta);
        assertEquals(9.0,  pow.get(1), delta);
        assertEquals(6.25, pow.get(2), delta);
    }

    @Test
    public void multiplyTest() {
        Vector v = new Vector(2.0, -3.0, 2.5);
        Vector mult = v.multiply(2.0);
        double delta = 1e-15;
        assertEquals( 4.0, mult.get(0), delta);
        assertEquals(-6.0, mult.get(1), delta);
        assertEquals( 5.0, mult.get(2), delta);
    }

    @Test
    public void scalarAddTest() {
        Vector v = new Vector(2.0, -3.0, 2.5);
        Vector add = v.add(2.5);
        double delta = 1e-15;
        assertEquals( 4.5, add.get(0), delta);
        assertEquals(-0.5, add.get(1), delta);
        assertEquals( 5.0, add.get(2), delta);
    }

    @Test
    public void productTest() {
        Vector v = new Vector(2.5, 3.0, -3.5);
        double prod = v.prod();
        assertEquals(2.5 * 3.0 * -3.5, prod, 1e-15);
    }

    @Test
    public void magnitudeTest() {
        Vector v = new Vector(3.43178, 0.27874, 4.32300);
        assertEquals(5.52658474611581, v.magnitude(), 1e-14);
    }

    @Test
    public void unitizeTest() {
        Vector v = new Vector(5.703441, 8.076563, 7.470319);
        assertEquals(1.0, v.unitize().magnitude(), 1e-15);
    }
}
