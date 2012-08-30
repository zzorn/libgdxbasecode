package org.gameflow.tools;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * A surface to paint on.
 * Supports painting pixmaps, other paintings, and some basic shapes.
 */
// TODO: Masking
public class Pic {

    private final Pixmap pixmap;

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
        boolean wrapRight = wrapHorizontally && dstX < 0 && dstX + dstW > 0;
        boolean wrapLeft  = wrapHorizontally && dstX < w && dstX + dstW > w;
        boolean wrapUp    = wrapVertically && dstY < 0 && dstY + dstH > 0;
        boolean wrapDown  = wrapVertically && dstY < h && dstY + dstH > h;

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


}
