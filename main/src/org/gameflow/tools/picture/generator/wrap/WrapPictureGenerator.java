package org.gameflow.tools.picture.generator.wrap;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.PictureImpl;
import org.gameflow.tools.picture.effect.PictureEffect;
import org.gameflow.tools.picture.generator.PictureGeneratorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class WrapPictureGenerator extends PictureGeneratorBase {

    private Array<PictureEffect> effects = new Array<PictureEffect>();
    private Array<Wrap> wraps = new Array<Wrap>();

    public List<Picture> generatePictures() {
        // Initialize random number generator
        Random random = getRandom();
        random.setSeed(getRandomSeed());

        // Notify members that we started creating a set of pictures
        int generatorSeed = random.nextInt();
        generatorInit(this, generatorSeed);

        // Create pictures
        List<Picture> pictures = new ArrayList<Picture>();
        for (int i = 0; i < getNumber(); i++) {

            // Notify members that we are now working on a new picture
            int pictureSeed = random.nextInt();
            pictureInit(this, generatorSeed, pictureSeed);

            // Create picture
            PictureImpl picture = new PictureImpl(getWidth(), getHeight());

            // Draw effects
            for (PictureEffect effect : effects) {
                effect.draw(picture, wraps, getRandomSeed());
            }

            // Add to returned pics
            pictures.add(picture);
        }

        return pictures;
    }


}
