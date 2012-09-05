package org.gameflow.tools.picture.generator;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
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

    public void renderPicture(Picture picture) {
        for (PictureEffect effect : effects) {
            //drawEffect(effect, getWidth() / 2, getHeight() / 2, 1, 1, false, false, 0, 0, 0, 0);
        }
    }

    public Array<Picture> generatePictures() {
        // TODO

    }
}
