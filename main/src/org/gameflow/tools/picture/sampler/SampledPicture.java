package org.gameflow.tools.picture.sampler;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import org.gameflow.tools.picture.Channel;
import org.gameflow.utils.ParameterChecker;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SampledPicture extends PictureSamplerBase {

    private Map<String, ChannelSampler> channelSamplers = new HashMap<String, ChannelSampler>();

    public SampledPicture() {
    }

    public SampledPicture(String initialChannelName, ChannelSampler initialChannel) {
        addChannel(initialChannelName, initialChannel);
    }

    public Map<String, ? extends ChannelSampler> getChannelSamplers() {
        return channelSamplers;
    }

    public void addChannel(String channelName, ChannelSampler channelSampler) {
        ParameterChecker.checkNotNull(channelSampler, "channelSampler");

        if (channelSamplers.containsKey(channelName)) throw new IllegalArgumentException("A channel with the name '"+channelName+"' already exists.");
        channelSamplers.put(channelName, channelSampler);

        addGeneratorMember(channelSampler);
    }


}
