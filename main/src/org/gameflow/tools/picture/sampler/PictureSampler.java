package org.gameflow.tools.picture.sampler;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import org.gameflow.tools.picture.generator.GeneratorMember;

import java.util.Map;

/**
 * Something that can provide image data pixel by pixel.
 */
public interface PictureSampler  extends GeneratorMember {

    Map<String, ? extends ChannelSampler> getChannelSamplers();
}
