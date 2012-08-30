package org.gameflow.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.Map;

import static org.gameflow.utils.MathTools.*;
import static org.gameflow.utils.MathTools.clamp;

/**
 * A surface to paint on.
 * Supports painting pixmaps, other paintings, and some basic shapes.
 */
// TODO: Masking
public class Pic {

    private final Pixmap pixmap;

    private Map<String, Channel> channels = null;

    boolean wrapVertically = false;
    boolean wrapHorizontally = false;

    public Pic(int width, int height) {
        this(width, height, false, false);
    }

    public Pic(int width, int height, boolean wrapVertically, boolean wrapHorizontally) {
        this.wrapVertically = wrapVertically;
        this.wrapHorizontally = wrapHorizontally;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    }

    public Pic(Pixmap pixmap) {
        this.pixmap = pixmap;
    }

    public Pixmap getPixmap() {
        return pixmap;
    }

    public Channel getChannel(String name) {
        if (channels == null) return null;
        else return channels.get(name);
    }

    public Channel getOrCreateChannel(String name) {
        Channel channel = getChannel(name);
        if (channel == null) channel = addChannel(name);
        return channel;
    }

    public Channel addChannel(String name) {
        if (channels == null) channels = new HashMap<String, Channel>();

        Channel channel = new Channel(getWidth(), getHeight());

        channels.put(name, channel);

        return channel;
    }

    public boolean isWrapVertically() {
        return wrapVertically;
    }

    public void setWrapVertically(boolean wrapVertically) {
        this.wrapVertically = wrapVertically;
    }

    public boolean isWrapHorizontally() {
        return wrapHorizontally;
    }

    public void setWrapHorizontally(boolean wrapHorizontally) {
        this.wrapHorizontally = wrapHorizontally;
    }

    public int getWidth() {
        return pixmap.getWidth();
    }

    public int getHeight() {
        return pixmap.getHeight();
    }

    public void clearToColor(Color color) {
        pixmap.setColor(color);
        pixmap.fill();
    }

    /**
     * @param pixmap
     * @return true if the picture was wrapped.
     */
    public boolean draw(Pixmap pixmap,
                     int srcX, int srcY, int srcW, int srcH,
                     int dstX, int dstY, int dstW, int dstH) {

        // Wrap
        int w = getWidth();
        int h = getHeight();
        boolean wrapRight = wrapRight(dstX, dstX + dstW);
        boolean wrapLeft = wrapLeft(dstX, dstX + dstW);
        boolean wrapUp = wrapUp(dstY, dstY + dstH);
        boolean wrapDown = wrapDown(dstY, dstH);

        // TODO: Mask?

        if (wrapLeft) this.pixmap.drawPixmap(pixmap,
                srcX, srcY, srcW, srcH,
                dstX - w, dstY, dstW, dstH);
        if (wrapRight) this.pixmap.drawPixmap(pixmap,
                srcX, srcY, srcW, srcH,
                dstX + w, dstY, dstW, dstH);
        if (wrapUp) this.pixmap.drawPixmap(pixmap,
                srcX, srcY, srcW, srcH,
                dstX, dstY + h, dstW, dstH);
        if (wrapDown) this.pixmap.drawPixmap(pixmap,
                srcX, srcY, srcW, srcH,
                dstX, dstY - h, dstW, dstH);

        this.pixmap.drawPixmap(pixmap,
                srcX, srcY, srcW, srcH,
                dstX, dstY, dstW, dstH);

        return wrapRight || wrapLeft || wrapDown || wrapUp;
    }

    /**
     * Draws a sphere on the pixmap.
     */
    public void drawOval(float x, float y, float rx, float ry, float direction, Gradient gradient) {
        if (rx > 0 && ry > 0) {
            int r = (int) Math.max(rx, ry) + 1;
            int x1 = (int) x - r;
            int y1 = (int) y - r;
            int x2 = (int) x + r;
            int y2 = (int) y + r;

            int w = getWidth();
            int h = getHeight();
            boolean wrapRight = wrapRight(x1, x2);
            boolean wrapLeft  = wrapLeft(x1, x2);
            boolean wrapUp    = wrapUp(y1, y2);
            boolean wrapDown  = wrapDown(y1, y2);

            Pixmap.Blending oldBlending = Pixmap.getBlending();
            Pixmap.setBlending(Pixmap.Blending.None);


            if (wrapDown && wrapRight) drawOvalOnce(x + w, y + h, rx, ry, direction, gradient);
            if (wrapDown)              drawOvalOnce(x,     y + h, rx, ry, direction, gradient);
            if (wrapDown && wrapLeft)  drawOvalOnce(x - w, y + h, rx, ry, direction, gradient);

            if (wrapRight)             drawOvalOnce(x + w, y, rx, ry, direction, gradient);
                                       drawOvalOnce(x,     y, rx, ry, direction, gradient);
            if (wrapLeft)              drawOvalOnce(x - w, y, rx, ry, direction, gradient);

            if (wrapUp && wrapRight)   drawOvalOnce(x + w, y - h, rx, ry, direction, gradient);
            if (wrapUp)                drawOvalOnce(x,     y - h, rx, ry, direction, gradient);
            if (wrapUp && wrapLeft)    drawOvalOnce(x - w, y - h, rx, ry, direction, gradient);

            Pixmap.setBlending(oldBlending);
        }
    }

    private boolean wrapUp(int y1, int y2) {
        return wrapVertically && y1 < getHeight() && y2 >= getHeight();
    }

    private boolean wrapDown(float y1, int y2) {
        return wrapVertically && y1 < 0 && y2 >= 0;
    }

    private boolean wrapLeft(int x1, int x2) {
        return wrapHorizontally && x1 < getWidth() && x2 >= getWidth();
    }

    private boolean wrapRight(int x1, int x2) {
        return wrapHorizontally && x1 < 0 && x2 >= 0;
    }

    private void drawOvalOnce(float x, float y, float rx, float ry, float direction, Gradient gradient) {
        if (rx > 0 && ry > 0) {
            final float angle = TauFloat * direction;
            float cosA = MathUtils.cos(angle);
            float sinA = MathUtils.sin(angle);

            int r = (int) Math.max(rx, ry) + 1;

            int x1 = clamp((int) x - r, 0, getWidth());
            int y1 = clamp((int) y - r, 0, getHeight());
            int x2 = clamp((int) x + r, 0, getWidth());
            int y2 = clamp((int) y + r, 0, getHeight());

            float rxSqr = rx*rx;
            float rySqr = ry*ry;
            for (int ty = y1; ty < y2; ty++) {
                for (int tx = x1; tx < x2; tx++) {
                    float tdx = tx - x;
                    float tdy = ty - y;
                    float dx = (tdx * cosA - tdy * sinA);
                    float dy = (tdx * sinA + tdy * cosA);
                    float xDistSqr = dx*dx;
                    float yDistSqr = dy*dy;
                    float relXDist = xDistSqr / rxSqr;
                    float relYDist = yDistSqr / rySqr;
                    float d = relXDist + relYDist;
                    if (d <= 1) {
                        int color = gradient.getColor(d);
                        pixmap.drawPixel(tx, ty, color);
                    }
                }
            }

        }
    }

}
