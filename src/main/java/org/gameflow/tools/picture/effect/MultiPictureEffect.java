package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Channel;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.blender.Blender;
import org.gameflow.tools.picture.generator.wrap.Wrap;
import org.gameflow.tools.picture.sampler.ChannelSampler;
import org.gameflow.tools.picture.sampler.PictureSamplerSet;

/**
 *
 */
public class MultiPictureEffect extends PictureEffectBase {

    private float gridX = 10;
    private float gridY = 10;
    private float offsetX = 0;
    private float offsetY = 0;
    private float maxPicturesPerGrid = 3;
    private ChannelSampler densityChannel;
    private PictureSamplerSet pictures;
    private Blender blender;

    public void draw(Picture picture, Array<Wrap> wrapsInPicture, long seed) {



    }
}
