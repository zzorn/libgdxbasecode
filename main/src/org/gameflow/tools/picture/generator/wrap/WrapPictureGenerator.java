package org.gameflow.tools.picture.generator.wrap;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.PictureImpl;
import org.gameflow.tools.picture.effect.PictureEffect;
import org.gameflow.tools.picture.generator.PictureGeneratorBase;

/**
 *
 */
public class WrapPictureGenerator extends PictureGeneratorBase {

    private Array<PictureEffect> effects = new Array<PictureEffect>();
    private Array<Wrap> wraps = new Array<Wrap>();

    public Array<Picture> generatePictures() {
        Array<Picture> pictures = new Array<Picture>();

        // Create pictures
        for (int i = 0; i < getNumber(); i++) {
            PictureImpl picture = new PictureImpl(getWidth(), getHeight());

            // Draw effects
            for (PictureEffect effect : effects) {
                effect.draw(picture, wraps, getRandomSeed());
            }

            pictures.add(picture);
        }

        return pictures;
    }


}
