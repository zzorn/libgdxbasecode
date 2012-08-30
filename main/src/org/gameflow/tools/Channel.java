package org.gameflow.tools;

import org.gameflow.utils.ParameterChecker;

/**
 *
 */
public class Channel {
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


}
