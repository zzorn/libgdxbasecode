package org.gameflow.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import org.gameflow.utils.SimplexGradientNoise;

import java.util.Random;

import static org.gameflow.utils.MathTools.TauFloat;
import static org.gameflow.utils.MathTools.clamp;

/**
 *
 */
public class BlobEffect extends PicEffectBase {

    private float noiseAmount = 0.5f;
    private float noiseScaleX = 0.1f;
    private float noiseScaleY = 0.1f;
    private float noiseOffsetX = 0;
    private float noiseOffsetY = 0;
    private Gradient gradient = new SimpleGradient(Color.WHITE, Color.BLACK);


    public BlobEffect() {
    }

    public boolean getUseBlending() {
        return true;
    }

    @Override
    protected void onSeedChanged(Random random) {
        noiseOffsetX = (float) random.nextGaussian() * 1000;
        noiseOffsetY = (float) random.nextGaussian() * 1000;
    }

    public float getNoiseAmount() {
        return noiseAmount;
    }

    public void setNoiseAmount(float noiseAmount) {
        this.noiseAmount = noiseAmount;
    }

    public float getNoiseScaleX() {
        return noiseScaleX;
    }

    public void setNoiseScaleX(float noiseScaleX) {
        this.noiseScaleX = noiseScaleX;
    }

    public float getNoiseScaleY() {
        return noiseScaleY;
    }

    public void setNoiseScaleY(float noiseScaleY) {
        this.noiseScaleY = noiseScaleY;
    }

    public Gradient getGradient() {
        return gradient;
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
    }

    public boolean draw(Pixmap pixmap,
                        float x, float y,
                        float scaleX, float scaleY,
                        boolean flipX, boolean flipY,
                        float direction,
                        float hueDelta, float satDelta, float lumDelta) {
        float rx = 0.5f * getW() * scaleX;
        float ry = 0.5f * getH() * scaleY;

        boolean anythingDrawn = false;

        if (rx > 0 && ry > 0) {
            final float angle = TauFloat * direction;
            float cosA = MathUtils.cos(angle);
            float sinA = MathUtils.sin(angle);

            int r = (int) Math.max(rx, ry) + 1;

            int txDelta = flipX ? -1 : 1;
            int tyDelta = flipY ? -1 : 1;

            int x1 = clamp((int) x - r * txDelta, 0, pixmap.getWidth());
            int y1 = clamp((int) y - r * tyDelta, 0, pixmap.getHeight());
            int x2 = clamp((int) x + r * txDelta, 0, pixmap.getWidth());
            int y2 = clamp((int) y + r * tyDelta, 0, pixmap.getHeight());

            for (int ty = y1; ty != y2; ty += tyDelta) {
                for (int tx = x1; tx != x2; tx += txDelta) {
                    float xOffs = tx - x;
                    float yOffs = ty - y;
                    float ux = (xOffs * cosA - yOffs * sinA);
                    float vy = (xOffs * sinA + yOffs * cosA);
                    float u = ux / rx;
                    float v = vy / ry;
                    float uDistSqr = u * u;
                    float vDistSqr = v * v;
                    float d = uDistSqr + vDistSqr;
                    d += noiseAmount * (float) SimplexGradientNoise.sdnoise2(u * noiseScaleX + noiseOffsetX, v * noiseScaleY + noiseOffsetY);
                    if (d <= 1) {
                        int color = gradient.getColor(d);
                        pixmap.drawPixel(tx, ty, color);
                        anythingDrawn = true;
                    }
                }
            }
        }

        return anythingDrawn;
    }


    public void getNoiseScale(float scale) {
        noiseScaleX = scale;
        noiseScaleY = scale;
    }
}
