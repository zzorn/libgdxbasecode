package org.gameflow.tools;

import com.badlogic.gdx.graphics.Color;

/**
 *
 */
public interface Gradient {

    boolean isSingleColor();

    int getSingleColor();

    /**
     * @param pos
     * @return the color in RGBA8888 format.
     */
    int getColor(float pos);

    Color getColor(float pos, Color colorOut);
}
