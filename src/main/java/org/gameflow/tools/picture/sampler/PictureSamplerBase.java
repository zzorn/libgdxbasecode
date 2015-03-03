package org.gameflow.tools.picture.sampler;

import org.gameflow.tools.picture.generator.GeneratorMemberBase;

/**
 *
 */
public abstract class PictureSamplerBase extends GeneratorMemberBase implements PictureSampler {

    public PictureSampler getPictureSampler(float relativeIndex) {
        return this;
    }
}
