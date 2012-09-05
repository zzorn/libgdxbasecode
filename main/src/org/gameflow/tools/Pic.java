package org.gameflow.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import org.gameflow.tools.picture.effect.PictureEffect;
import org.gameflow.tools.picture.Channel;

import java.util.HashMap;
import java.util.Map;

import static org.gameflow.utils.MathTools.clamp;

/**
 * A surface to paint on.
 * Supports painting pixmaps, other paintings, and some basic shapes.
 */
// TODO: Masking
public class Pic {

    private final Pixmap pixmap;

    private Map<String, Channel> channels = null;

    boolean wrapVertically = true;
    boolean wrapHorizontally = true;

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

        Channel channel = new Channel(name, getWidth(), getHeight());

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

    public boolean drawEffect(PictureEffect effect) {
        return drawEffect(effect, pixmap.getWidth()/2, pixmap.getHeight()/2, 1, 1, false, false, 0, 0,0,0);
    }

    public boolean drawEffect(PictureEffect effect,
                              float x, float y) {
        return drawEffect(effect,x, y, 1, 1, false, false, 0, 0,0,0);
    }

    public boolean drawEffect(PictureEffect effect,
                              float x, float y,
                              float scale) {
        return drawEffect(effect,x, y, scale, scale, false, false, 0, 0,0,0);
    }

    public boolean drawEffect(PictureEffect effect,
                              float x, float y,
                              float scaleX, float scaleY) {
        return drawEffect(effect,x, y, scaleX, scaleY, false, false, 0, 0,0,0);
    }

    public boolean drawEffect(PictureEffect effect,
                              float x, float y,
                              float scaleX, float scaleY,
                              boolean flipX, boolean flipY) {
        return drawEffect(effect,x, y, scaleX, scaleY, flipX, flipY, 0, 0,0,0);
    }

    public boolean drawEffect(PictureEffect effect,
                              float x, float y,
                              float scaleX, float scaleY,
                              boolean flipX, boolean flipY,
                              float direction) {
        return drawEffect(effect,x, y, scaleX, scaleY, flipX, flipY, direction, 0,0,0);
    }

    /**
    * Draws an effect on the pixmap.
    */
    public boolean drawEffect(PictureEffect effect,
                           float x, float y,
                           float scaleX, float scaleY,
                           boolean flipX, boolean flipY,
                           float direction,
                           float hueDelta, float satDelta, float lumDelta) {
        if (scaleX > 0 && scaleX > 0) {
            float effectW = effect.getW();
            float effectH = effect.getH();

            // TODO: Adjust bounding box for rotation
            float sizeX = effectW * scaleX;
            float sizeY = effectH * scaleY;
            int r = (int) Math.max(sizeX, sizeY) + 1;
            int x1 = (int) x - r;
            int y1 = (int) y - r;
            int x2 = (int) x + r;
            int y2 = (int) y + r;

            boolean wrapRight = wrapRight(x1, x2);
            boolean wrapLeft  = wrapLeft(x1, x2);
            boolean wrapUp    = wrapUp(y1, y2);
            boolean wrapDown  = wrapDown(y1, y2);

            Pixmap.Blending oldBlending = Pixmap.getBlending();
            if (effect.getUseBlending()) {
                Pixmap.setBlending(Pixmap.Blending.SourceOver);
            }
            else {
                Pixmap.setBlending(Pixmap.Blending.None);
            }

            int w = getWidth();
            int h = getHeight();

            boolean b = false;
            if (wrapDown && wrapRight) b = b || effect.draw(pixmap, x + w, y + h, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);
            if (wrapDown)              b = b || effect.draw(pixmap, x    , y + h, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);
            if (wrapDown && wrapLeft)  b = b || effect.draw(pixmap, x - w, y + h, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);

            if (wrapRight)             b = b || effect.draw(pixmap, x + w, y    , scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);
                                                effect.draw(pixmap, x, y, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);
            if (wrapLeft)              b = b || effect.draw(pixmap, x - w, y    , scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);

            if (wrapUp && wrapRight)   b = b || effect.draw(pixmap, x + w, y - h, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);
            if (wrapUp)                b = b || effect.draw(pixmap, x    , y - h, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);
            if (wrapUp && wrapLeft)    b = b || effect.draw(pixmap, x - w, y - h, scaleX, scaleY, flipX, flipY, direction, hueDelta, satDelta, lumDelta);

            Pixmap.setBlending(oldBlending);

            return b;
        }
        else {
            return false;
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


}
