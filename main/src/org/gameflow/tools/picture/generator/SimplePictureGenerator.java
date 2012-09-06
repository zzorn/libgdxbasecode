package org.gameflow.tools.picture.generator;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.PictureImpl;
import org.gameflow.tools.picture.effect.PictureEffect;

/**
 *
 */
public class SimplePictureGenerator extends PictureGeneratorBase {

    private Array<PictureEffect> effects = new Array<PictureEffect>();

    public Array<PictureEffect> getEffects() {
        return effects;
    }

    public void addEffect(PictureEffect effect) {
        effects.add(effect);
    }

    public void removeEffect(PictureEffect effect) {
        effects.removeValue(effect, true);
    }

    public Array<Picture> generatePictures() {
        Array<Picture> pictures = new Array<Picture>();

        // Create pictures
        for (int i = 0; i < getNumber(); i++) {
            PictureImpl picture = new PictureImpl(getWidth(), getHeight());

            // Draw effects
            for (PictureEffect effect : effects) {
                effect.draw(picture, null, getRandomSeed());
            }

            pictures.add(picture);
        }

        return pictures;
    }
}
