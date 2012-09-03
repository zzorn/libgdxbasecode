package org.gameflow.tools.raster;

import org.gameflow.utils.ParameterChecker;

/**
 *
 */
public final class Channel {
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

}
