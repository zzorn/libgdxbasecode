package org.gameflow.tools.picture;

import org.gameflow.tools.picture.generator.GeneratorMemberBase;
import org.gameflow.tools.picture.sampler.ChannelSampler;
import org.gameflow.utils.ParameterChecker;

/**
 *
 */
public final class Channel extends GeneratorMemberBase implements ChannelSampler {
    private final int width;
    private final int height;
    private final float data[];

    public Channel(int width, int height) {
        ParameterChecker.checkPositiveNonZeroInteger(width, "width");
        ParameterChecker.checkPositiveNonZeroInteger(height, "height");

        this.width = width;
        this.height = height;

        data = new float[width * height];
    }

    public float getPixel(int x, int y) {
        return data[x + y * width];
    }

    public void setPixel(int x, int y, float value) {
        data[x + y * width] = value;
    }

    public float getRelativePixel(float x, float y) {
        return data[(int) (width * x) + (int) (height * y) * width];
    }
}
