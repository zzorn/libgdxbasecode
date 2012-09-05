package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.generator.wrap.Wrap;

/**
 * Draws something on a picture, or processes it in some way.
 */
public interface PictureEffect {

    boolean getUseBlending();

    float getW();
    float getH();

    /**
     * @param pixmap surface to draw on
     * @param x position to draw the effect on, in pixmap coordinates.
     * @param y position to draw the effect on, in pixmap coordinates.
     * @param scaleX scale in x direction.
     * @param scaleY scale in y direction.
     * @param flipX whether to flip along x axis.
     * @param flipY whether to flip along y axis.
     * @param direction the direction the effect should be turned. 0 = normal, 0.25 = quarter turn, 0.5 half turn, 1 = full turn, etc.
     * @param hueDelta change to apply to effect hue
     * @param satDelta change to apply to effect saturation
     * @param lumDelta change to apply to effect luminance
     * @return true if anything was drawn to the pixmap
     */
    boolean draw(Pixmap pixmap,
                 float x, float y,
                 float scaleX, float scaleY,
                 boolean flipX, boolean flipY,
                 float direction,
                 float hueDelta, float satDelta, float lumDelta);

    /**
     * @param picture target to render to.
     * @param wrapsInPicture edges that are in the picture we draw on.
     * @param seed a random seed for this drawing
     */
    void draw(Picture picture,
              Array<Wrap> wrapsInPicture,
              long seed);

}
