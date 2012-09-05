package org.gameflow.tools.picture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import org.gameflow.tools.picture.sampler.ChannelSampler;

/**
 *
 */
public class PictureImpl implements Picture {

    private final int width;
    private final int height;

    private ObjectIntMap<String> channelIds = new ObjectIntMap<String>();
    private Array<Channel> channels = new Array<Channel>();

    public PictureImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int addChannel(String channelName) {
        if (channelIds.containsKey(channelName)) throw new IllegalArgumentException("A channel with the name '"+channelName+"' already exists.");

        int id = channels.size;
        channels.add(new Channel(channelName, width, height));
        channelIds.put(channelName, id);
        return id;
    }

    public int getChannelId(String channelName) {
        return channelIds.get(channelName, -1);
    }

    public ObjectIntMap.Entries<String> getChannelIds() {
        return channelIds.entries();
    }

    public Channel getChannel(int channelId) {
        return channels.get(channelId);
    }

    public Channel getChannel(String channelName) {
        return getChannel(getChannelId(channelName));
    }

    public Array<Channel> getChannels() {
        return channels;
    }

    public Array<? extends ChannelSampler> getChannelSamplers() {
        return channels;
    }

}
