package org.gameflow.tools.picture.generator;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.PictureImpl;
import org.gameflow.tools.picture.effect.PictureEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class SimplePictureGenerator extends PictureGeneratorBase {

    private Array<PictureEffect> effects = new Array<PictureEffect>();

    public SimplePictureGenerator() {
    }

    public SimplePictureGenerator(String name) {
        super(name);
    }

    public SimplePictureGenerator(String name, int width, int height) {
        super(name, width, height);
    }

    public SimplePictureGenerator(String name, int width, int height, int numPics) {
        super(name, width, height, numPics);
    }

    public SimplePictureGenerator(String name, int width, int height, int numPics, long randomSeed) {
        super(name, width, height, numPics, randomSeed);
    }

    public Array<PictureEffect> getEffects() {
        return effects;
    }

    public void addEffect(PictureEffect effect) {
        effects.add(effect);
        addGeneratorMember(effect);
    }

    public void removeEffect(PictureEffect effect) {
        effects.removeValue(effect, true);
        removeGeneratorMember(effect);
    }

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
                effect.draw(picture, null, getRandomSeed());
            }

            // Add to returned pics
            pictures.add(picture);
        }

        return pictures;
    }
}
