package org.gameflow.tools;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.raster.Raster;

/**
 *
 */
public class Picture {

    private Array<PicEffect> effects = new Array<PicEffect>();

    public Array<PicEffect> getEffects() {
        return effects;
    }

    public void addEffect(PicEffect effect) {
        effects.add(effect);
    }

    public void removeEffect(PicEffect effect) {
        effects.removeValue(effect, true);
    }

    public void renderPicture(Raster raster) {
        for (PicEffect effect : effects) {
            //drawEffect(effect, getWidth() / 2, getHeight() / 2, 1, 1, false, false, 0, 0, 0, 0);
        }
    }

}
