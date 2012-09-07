package org.gameflow.tools.picture.blender;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

import java.util.List;

/**
 * Used to blend a source picture on top of a target picture one pixel at a time.
 */
public interface Blender {

    /**
     * Informs the blender about the channel names of the source and target pictures.
     * The channel names are in the same order as the channel values when blend is called.
     */
    void initChannels(List<String> targetChannelNames, List<String> sourceChannelNames);

    /**
     * Updates the target channel values for this pixel based on the source channel values.
     */
    void blend(float[] targetChannelValues, float[] sourceChannelValues);

}
