package org.gameflow.tools.raster;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;

/**
 *
 */
public class RasterImpl implements Raster {

    private final int width;
    private final int height;

    private ObjectIntMap<String> channelIds = new ObjectIntMap<String>();
    private Array<Channel> channels = new Array<Channel>();

    private final Pixmap pixmap;

    public RasterImpl(int width, int height) {
        this.width = width;
        this.height = height;

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
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
        channels.add(new Channel(width, height));
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

    public Pixmap getPixmap() {
        return pixmap;
    }

    public int getPixelColor(int x, int y) {
        return pixmap.getPixel(x, y);
    }

    public void setPixelColor(int x, int y, int rgba) {
        pixmap.drawPixel(x, y, rgba);
    }

    public float getPixelChannel(int channel, int x, int y) {
        return channels.get(channel).getPixel(x, y);
    }

    public void setPixelChannel(int channel, int x, int y, float value) {
        channels.get(channel).setPixel(x, y, value);
    }
}
