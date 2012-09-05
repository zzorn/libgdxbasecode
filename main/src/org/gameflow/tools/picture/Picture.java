package org.gameflow.tools.picture;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ObjectIntMap;
import org.gameflow.tools.picture.sampler.PictureSampler;

/**
 * A raster with a possible RGBA picture, and zero or more float valued channels.
 */
public interface Picture extends PictureSampler {

    int addChannel(String channelName);
    Channel getChannel(String channelName);

    int getChannelId(String channelName);
    ObjectIntMap.Entries<String> getChannelIds();
    Channel getChannel(int channelId);

}
