package com.nullprogram.noise;

/**
 * Some space containing noise which can be sampled.
 */
public interface Noise {

    /**
     * Get the noise value at some position.
     * @param p  the position to query
     * @return the noise value at the selected position
     */
    double sample(Vector p);
}
