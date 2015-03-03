package org.gameflow.tools.picture.sampler;

/**
 *
 */
public interface MultiPictureSampler {

    /**
     * @param relativeIndex range 0..1, used to pick the picture sampler to return.
     * @return returns one of many picture samplers, or null if there are is not picture sampler for that value.
     */
    PictureSampler getPictureSampler(float relativeIndex);
}
