package org.gameflow.tools.picture.blender;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

import java.util.List;

/**
 *
 */
public abstract class BlenderBase implements Blender {
    private List<String> targetChannelNames;
    private List<String> sourceChannelNames;

    public final void initChannels(List<String> targetChannelNames,
                                   List<String> sourceChannelNames) {
        this.targetChannelNames = targetChannelNames;
        this.sourceChannelNames = sourceChannelNames;

        onInit();
    }

    /**
     * Called when the blender has been notified of the source and target channel names that will be used next.
     */
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

    public List<String> getTargetChannelNames() {
        return targetChannelNames;
    }

    public List<String> getSourceChannelNames() {
        return sourceChannelNames;
    }
}
