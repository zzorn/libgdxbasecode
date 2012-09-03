package org.gameflow.tools.graphics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.PicEffect;
import org.gameflow.tools.raster.Raster;
import org.gameflow.tools.raster.RasterImpl;

/**
 *
 */
public class SimplePictureGenerator extends PictureGenerator {

    private String name;
    private boolean wrapVertically;
    private boolean wrapHorizontally;
    private int number;
    private int width;
    private int height;

    private Array<PicEffect> centerEffects = new Array<PicEffect>();
    private Array<Wrap> wraps = new Array<Wrap>();

    private final Matrix3 tempTransform = new Matrix3();

    public Array<Raster> generatePictures() {
        Array<Raster> pictures = new Array<Raster>();

        tempTransform.idt();

        for (int i = 0; i < number; i++) {
            RasterImpl raster = new RasterImpl(width, height);
            pictures.add(raster);

            // TODO: Intertwine the effects? How?

            // Draw center effect
            for (PicEffect effect : centerEffects) {
                effect.draw(raster, tempTransform, wraps, null);
            }

            // Draw edge effects
            for (Wrap wrap : wraps) {
                for (PicEffect effect : wrap.getWrapEffects()) {
                    effect.draw(raster, tempTransform, null, wrap);
                }
            }
        }

        return pictures;
    }
}
