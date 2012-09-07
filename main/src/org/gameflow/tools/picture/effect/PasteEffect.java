package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.PictureRasterizer;
import org.gameflow.tools.picture.blender.Blender;
import org.gameflow.tools.picture.blender.ValueToGreyscaleBlender;
import org.gameflow.tools.picture.generator.PictureGenerator;
import org.gameflow.tools.picture.generator.wrap.Wrap;
import org.gameflow.tools.picture.sampler.PictureSampler;

/**
 *
 */
public class PasteEffect extends PictureEffectBase {

    private PictureSampler pictureSampler;
    private Blender blender;
    private final PictureRasterizer pictureRasterizer = new PictureRasterizer();

    public PasteEffect(PictureSampler pictureSampler) {
        setPictureSampler(pictureSampler);
        this.blender = new ValueToGreyscaleBlender();
    }

    public PasteEffect(PictureSampler pictureSampler, Blender blender) {
        setPictureSampler(pictureSampler);
        this.blender = blender;
    }

    public PictureSampler getPictureSampler() {
        return pictureSampler;
    }

    public void setPictureSampler(PictureSampler pictureSampler) {
        if (this.pictureSampler != null) removeGeneratorMember(this.pictureSampler);

        this.pictureSampler = pictureSampler;

        if (this.pictureSampler != null) addGeneratorMember(this.pictureSampler);
    }

    public Blender getBlender() {
        return blender;
    }

    public void setBlender(Blender blender) {
        this.blender = blender;
    }

    public void draw(Picture picture, Array<Wrap> wrapsInPicture, long seed) {
        if (pictureSampler != null && blender != null) {
            pictureRasterizer.renderPicture(picture, pictureSampler, blender);
        }
    }
}
