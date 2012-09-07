package org.gameflow.tools.picture.blender;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

/**
 *
 */
public abstract class BlenderBase implements Blender {
    private Array<String> targetChannelNames;
    private Array<String> sourceChannelNames;

    public final void initChannels(Array<String> targetChannelNames,
                             Array<String> sourceChannelNames) {
        this.targetChannelNames = targetChannelNames;
        this.sourceChannelNames = sourceChannelNames;

        onInit();
    }

    protected void onInit() {}

    protected final int getSourceChannel(String channelName) {
        int i = 0;
        for (String sourceChannelName : sourceChannelNames) {
            if (sourceChannelName.equals(channelName)) return i;
            i++;
        }
        return -1;
    }

    protected final int getTargetChannel(String channelName) {
        int i = 0;
        for (String targetChannelName : targetChannelNames) {
            if (targetChannelName.equals(channelName)) return i;
            i++;
        }
        return -1;
    }

}
