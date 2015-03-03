package org.gameflow.tools.picture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import org.gameflow.tools.picture.sampler.PictureSampler;

import java.util.Map;

/**
 * A raster with a possible RGBA picture, and zero or more float valued channels.
 */
// TODO: Add name?
public interface Picture extends PictureSampler {

    int getWidth();
    int getHeight();

    void addChannel(String channelName);
    Channel getChannel(String channelName);
    Channel getChannelOrNull(String channelName);
    boolean hasChannel(String channelName);

    /**
     *  @return the channels in this picture, as a map of channel names to channels.
     */
    Map<String, Channel> getChannels();
}
