package org.gameflow.tools.picture.effect;

import com.badlogic.gdx.graphics.Pixmap;

import java.util.Random;

/**
 *
 */
public abstract class PictureEffectBase implements PictureEffect {
    private float w = 16;
    private float h = 16;
    private boolean useBlending = false;
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

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public void setW(float w) {
        this.w = w;
    }

    public void setH(float h) {
        this.h = h;
    }

    public void setSize(float width, float height) {
        this.w = width;
        this.h = height;
    }

    public boolean getUseBlending() {
        return useBlending;
    }

    public void setUseBlending(boolean useBlending) {
        this.useBlending = useBlending;
    }

    public boolean draw(Pixmap pixmap) {
        return draw(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2, 1,  1, false, false, 0, 0, 0, 0);
    }

    public boolean draw(Pixmap pixmap, float x, float y) {
        return draw(pixmap, x, y, 1,  1, false, false, 0, 0, 0, 0);
    }

    public boolean draw(Pixmap pixmap, float x, float y, float scale) {
        return draw(pixmap, x, y, scale,  scale, false, false, 0, 0, 0, 0);
    }

    public boolean draw(Pixmap pixmap, float x, float y, float scaleX, float scaleY) {
        return draw(pixmap, x, y, scaleX,  scaleY, false, false, 0, 0, 0, 0);
    }

    public boolean draw(Pixmap pixmap, float x, float y, float scaleX, float scaleY, boolean flipX, boolean flipY) {
        return draw(pixmap, x, y, scaleX,  scaleY, flipX, flipY, 0, 0, 0, 0);
    }

    public boolean draw(Pixmap pixmap, float x, float y, float scaleX, float scaleY, boolean flipX, boolean flipY, float direction) {
        return draw(pixmap, x, y, scaleX,  scaleY, flipX, flipY, direction, 0, 0, 0);
    }
}
