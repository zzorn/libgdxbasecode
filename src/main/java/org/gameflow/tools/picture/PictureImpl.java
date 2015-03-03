package org.gameflow.tools.picture;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import org.gameflow.tools.picture.generator.GeneratorMemberBase;
import org.gameflow.tools.picture.sampler.ChannelSampler;
import org.gameflow.tools.picture.sampler.PictureSampler;

import java.util.HashMap;
import java.util.Map;

import static org.gameflow.utils.ParameterChecker.checkPositiveNonZeroInteger;

/**
 *
 */
public class PictureImpl extends GeneratorMemberBase implements Picture {

    private final int width;
    private final int height;

    private Map<String, Channel> channels = new HashMap<String, Channel>();

    public PictureImpl(int width, int height) {
        this(width, height, true);
    }

    public PictureImpl(int width, int height, boolean addColorAndAlphaChannels) {
        checkPositiveNonZeroInteger(width, "width");
        checkPositiveNonZeroInteger(height, "height");

        this.width = width;
        this.height = height;

        if (addColorAndAlphaChannels) {
            addChannel("red");
            addChannel("green");
            addChannel("blue");
            addChannel("alpha");
        }
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addChannel(String channelName) {
        if (channels.containsKey(channelName)) throw new IllegalArgumentException("A channel with the name '"+channelName+"' already exists.");
        channels.put(channelName, new Channel(width, height));
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    public Channel getChannel(String channelName) {
        return channels.get(channelName);
    }

    public Channel getChannelOrNull(String channelName) {
        if (hasChannel(channelName)) return getChannel(channelName);
        else return null;
    }

    public boolean hasChannel(String channelName) {
        return channels.containsKey(channelName);
    }

    public Map<String, ? extends ChannelSampler> getChannelSamplers() {
        return channels;
    }

    public PictureSampler getPictureSampler(float relativeIndex) {
        return this;
    }
}
