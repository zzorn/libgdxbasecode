package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.PictureRenderer;
import org.gameflow.tools.picture.blender.Blender;
import org.gameflow.tools.picture.generator.wrap.Wrap;
import org.gameflow.tools.picture.sampler.PictureSampler;

/**
 *
 */
public class PastePictureEffect extends PictureEffectBase {

    private PictureSampler pictureSampler;
    private Blender blender;
    private final PictureRenderer pictureRenderer = new PictureRenderer();

    public PictureSampler getPictureSampler() {
        return pictureSampler;
    }

    public void setPictureSampler(PictureSampler pictureSampler) {
        this.pictureSampler = pictureSampler;
    }

    public Blender getBlender() {
        return blender;
    }

    public void setBlender(Blender blender) {
        this.blender = blender;
    }

    public void draw(Picture picture, Array<Wrap> wrapsInPicture, long seed) {
        if (pictureSampler != null && blender != null) {
            pictureRenderer.renderPicture(picture, pictureSampler, blender);
        }
    }
}
