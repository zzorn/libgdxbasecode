package org.gameflow.tools.picture;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import org.gameflow.tools.picture.blender.Blender;
import org.gameflow.tools.picture.sampler.PictureSampler;

/**
 *
 */
public final class PictureUtils {

    private final static Rectangle ZERO_TO_ONE_RECT = new Rectangle(0,0,1,1);

    public static void renderPicture(Picture target, PictureSampler source, Rectangle targetArea, Blender blender) {
        renderPicture(target, source, targetArea, ZERO_TO_ONE_RECT, blender);
    }

    public static void renderPicture(Picture target, PictureSampler source, Rectangle targetArea, Rectangle sourceArea, Blender blender) {
        // TODO
    }

    public static void renderTransformedPicture(Picture target, PictureSampler source, Matrix3 transformation, Blender blender) {
        // TODO

    }




    /**
     * Private constructor, this is an utility class, you should not need to create instances of it.
     */
    private PictureUtils() {
    }
}
