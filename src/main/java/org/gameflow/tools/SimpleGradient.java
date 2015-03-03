package org.gameflow.tools;

import com.badlogic.gdx.graphics.Color;
import org.gameflow.utils.MathTools;

/**
 *
 */
public class SimpleGradient implements Gradient {

    private final Color startColor;
    private final Color endColor;
    private final Color tempColor = new Color();

    public SimpleGradient(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public boolean isSingleColor() {
        return false;
    }

    public int getSingleColor() {
        return 0;
    }

    public int getColor(float pos) {
        getColor(pos, tempColor);
        return Color.rgba8888(tempColor);
    }

    public Color getColor(float pos, Color colorOut) {
        colorOut.r = MathTools.mix(pos, startColor.r, endColor.r);
        colorOut.g = MathTools.mix(pos, startColor.g, endColor.g);
        colorOut.b = MathTools.mix(pos, startColor.b, endColor.b);
        colorOut.a = MathTools.mix(pos, startColor.a, endColor.a);
        colorOut.clamp();
        return colorOut;
    }
}
