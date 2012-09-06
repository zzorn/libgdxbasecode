package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.graphics.Pixmap;

import java.util.Random;

/**
 *
 */
public abstract class PictureEffectBase implements PictureEffect {
    private long seed = 42;
    private final Random random = new Random();

    protected PictureEffectBase() {
        setSeed(random.nextInt(1000));
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        random.setSeed(this.seed);
        onSeedChanged(random);
    }

    protected void onSeedChanged(Random random) {}

    protected Random getRandom() {
        return random;
    }

}
