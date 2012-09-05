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

    private String name;
    private int number;
    private int width;
    private int height;
    private long seed;

    private Array<PictureEffect> effects = new Array<PictureEffect>();
    private Array<Wrap> wraps = new Array<Wrap>();

    public Array<Picture> generatePictures() {
        Array<Picture> pictures = new Array<Picture>();

        // Create pictures
        for (int i = 0; i < number; i++) {
            PictureImpl picture = new PictureImpl(width, height);

            // Draw effects
            for (PictureEffect effect : effects) {
                effect.draw(picture, wraps, seed);
            }

            pictures.add(picture);
        }

        return pictures;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
