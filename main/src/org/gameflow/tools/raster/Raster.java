package org.gameflow.tools.raster;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectIntMap;

/**
 * A raster with a possible RGBA picture, and zero or more float valued channels.
 */
public interface Raster {

    int addChannel(String channelName);
    int getChannelId(String channelName);
    ObjectIntMap.Entries<String> getChannelIds();
    Channel getChannel(int channelId);
    Channel getChannel(String channelName);

    Pixmap getPixmap();

    int getPixelColor(int x, int y);
    void setPixelColor(int x, int y, int rgba);
    float getPixelChannel(int channel, int x, int y);
    void setPixelChannel(int channel, int x, int y, float value);

}
