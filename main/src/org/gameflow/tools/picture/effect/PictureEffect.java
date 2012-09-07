package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.generator.GeneratorMember;
import org.gameflow.tools.picture.generator.wrap.Wrap;

/**
 * Draws something on a picture, or processes it in some way.
 */
public interface PictureEffect extends GeneratorMember {

    /**
     * @param picture target to render to.
     * @param wrapsInPicture edges that are in the picture we draw on.
     * @param seed a random seed for this drawing
     */
    void draw(Picture picture,
              Array<Wrap> wrapsInPicture,
              long seed);

}
