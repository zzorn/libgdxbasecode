package org.gameflow.tools.picture.sampler;

import org.gameflow.tools.picture.generator.GeneratorMember;

/**
 *
 */
public interface ChannelSampler extends GeneratorMember {

    /**
    * @param x x coordinate, in range 0..1
    * @param y y coordinate, in range 0..1
    * @return the value at location x, y.  Result unspecified if trying to get data from outside boundaries.
    */
    float getRelativePixel(float x, float y);

}
