package org.gameflow.tools.picture.exporter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import org.gameflow.tools.picture.Channel;
import org.gameflow.tools.picture.Picture;
import org.gameflow.utils.MathTools;
import org.gameflow.utils.ParameterChecker;

import java.util.ArrayList;
import java.util.List;

import static org.gameflow.utils.ParameterChecker.*;

/**
 * Creates a pixmap from a Picture.
 */
public class PixmapExporter {

    private Pixmap.Format pixmapFormat = Pixmap.Format.RGBA8888;
    private String redChannelName   = "red";
    private String greenChannelName = "green";
    private String blueChannelName  = "blue";
    private String alphaChannelName = "alpha";

    public Pixmap export(Picture picture) {
        return export(picture, null);
    }

    public List<Pixmap> exportPictures(List<Picture> pictures, List<Pixmap> pixmaps) {
        checkNotNull(pictures, "pictures");

        if (pixmaps == null || pixmaps.size() != pictures.size()) {
            // Initialize pixmaps to be a new list with nulls for each pixmap
            pixmaps = new ArrayList<Pixmap>(pictures.size());
            for (Picture picture : pictures) {
                pixmaps.add(null);
            }
        }

        int i = 0;
        for (Picture picture : pictures) {
            pixmaps.set(i, export(picture, pixmaps.get(i)));
            i++;
        }

        return pixmaps;
    }

    public Pixmap export(Picture picture, Pixmap pixmap) {
        // Create pixmap if needed
        if (pixmap == null) pixmap = new Pixmap(picture.getWidth(), picture.getHeight(), pixmapFormat);
        System.out.println(pixmap.getGLType());

        // Check parameters
        checkNotNull(picture, "picture");
        if (picture.getWidth() != pixmap.getWidth() ||
            picture.getHeight() != pixmap.getHeight()) throw new IllegalArgumentException("The size of the given picture and pixmap differ, can not export picture to pixmap.");

        int w = picture.getWidth();
        int h = picture.getHeight();

        // Clear existing pixmap to transparent
        pixmap.setColor(0,0,0,0);
        pixmap.fill();

        // Get channels to render
        Channel rChannel = picture.getChannelOrNull(redChannelName);
        Channel gChannel = picture.getChannelOrNull(greenChannelName);
        Channel bChannel = picture.getChannelOrNull(blueChannelName);
        Channel aChannel = picture.getChannelOrNull(alphaChannelName);

        /* Maybe not needed if pixmap is 100% opacity initially
        // We need to set the alpha directly, instead of overwriting.
        Pixmap.Blending oldBlending = Pixmap.getBlending();
        Pixmap.setBlending(Pixmap.Blending.None);
         */

        // Render picture channels to pixmap pixels
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                float r = rChannel != null ? MathTools.clampToZeroToOne(rChannel.getPixel(x, y)) : 0f;
                float g = gChannel != null ? MathTools.clampToZeroToOne(gChannel.getPixel(x, y)) : 0f;
                float b = bChannel != null ? MathTools.clampToZeroToOne(bChannel.getPixel(x, y)) : 0f;
                float a = aChannel != null ? MathTools.clampToZeroToOne(aChannel.getPixel(x, y)) : 1f;
                pixmap.drawPixel(x, y, Color.rgba8888(r, g, b, a));
            }
        }

        /*
        Pixmap.setBlending(oldBlending);
        */

        return pixmap;
    }


    public Pixmap.Format getPixmapFormat() {
        return pixmapFormat;
    }

    public void setPixmapFormat(Pixmap.Format pixmapFormat) {
        this.pixmapFormat = pixmapFormat;
    }

    public String getRedChannelName() {
        return redChannelName;
    }

    public void setRedChannelName(String redChannelName) {
        this.redChannelName = redChannelName;
    }

    public String getGreenChannelName() {
        return greenChannelName;
    }

    public void setGreenChannelName(String greenChannelName) {
        this.greenChannelName = greenChannelName;
    }

    public String getBlueChannelName() {
        return blueChannelName;
    }

    public void setBlueChannelName(String blueChannelName) {
        this.blueChannelName = blueChannelName;
    }

    public String getAlphaChannelName() {
        return alphaChannelName;
    }

    public void setAlphaChannelName(String alphaChannelName) {
        this.alphaChannelName = alphaChannelName;
    }
}
