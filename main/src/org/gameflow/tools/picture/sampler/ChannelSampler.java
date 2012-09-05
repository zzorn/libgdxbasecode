package org.gameflow.tools.picture.sampler;

/**
 *
 */
public interface ChannelSampler {

    /**
     * @return name of the channel this sampler provides data for.
     */
    String getName();

    /**
     * @param x x coordinate, in range 0..1
     * @param y y coordinate, in range 0..1
     * @return the value at location x, y.  Result unspecified if trying to get data from outside boundaries.
     */
    float getRelativePixel(float x, float y);

}
