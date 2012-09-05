package org.gameflow.tools.picture.sampler;

import com.badlogic.gdx.utils.Array;

/**
 * Something that can provide image data pixel by pixel.
 */
public interface PictureSampler {

    Array<? extends ChannelSampler> getChannelSamplers();
}
