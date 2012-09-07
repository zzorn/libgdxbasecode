package org.gameflow.tools.picture;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import org.gameflow.tools.picture.blender.Blender;
import org.gameflow.tools.picture.sampler.ChannelSampler;
import org.gameflow.tools.picture.sampler.PictureSampler;

import java.util.Map;

/**
 * Handles iterating through pictures and blending source pixels on target pixels.
 */
public final class PictureRasterizer {

    private final static Rectangle ZERO_TO_ONE_RECT = new Rectangle(0,0,1,1);

    private final Rectangle tempRect = new Rectangle(0,0,1,1);
    private final FloatArray targetData = new FloatArray();
    private final FloatArray sourceData = new FloatArray();
    private final Array<String> sourceChannelNames = new Array<String>();
    private final Array<ChannelSampler> sourceChannels = new Array<ChannelSampler>();
    private final Array<String> targetChannelNames = new Array<String>();
    private final Array<Channel> targetChannels= new Array<Channel>();

    public void renderPicture(Picture target, PictureSampler source, Blender blender) {
        tempRect.setX(0);
        tempRect.setY(0);
        tempRect.setWidth(target.getWidth());
        tempRect.setHeight(target.getHeight());
        renderPicture(target, source, tempRect, ZERO_TO_ONE_RECT, blender);
    }

    public void renderPicture(Picture target, PictureSampler source, Rectangle targetArea, Blender blender) {
        renderPicture(target, source, targetArea, ZERO_TO_ONE_RECT, blender);
    }

    public void renderPicture(Picture target, PictureSampler source, Rectangle targetArea, Rectangle sourceArea, Blender blender) {
        // Clear temporary structures
        targetData.clear();
        sourceData.clear();
        sourceChannelNames.clear();
        targetChannelNames.clear();
        sourceChannels.clear();
        targetChannels.clear();

        // Get source channels
        for (Map.Entry<String, ? extends ChannelSampler> entry : source.getChannelSamplers().entrySet()) {
            sourceChannelNames.add(entry.getKey());
            sourceChannels.add(entry.getValue());
            sourceData.add(0);
        }

        // Get target channels
        for (Map.Entry<String, Channel> entry : target.getChannels().entrySet()) {
            targetChannelNames.add(entry.getKey());
            targetChannels.add(entry.getValue());
            targetData.add(0);
        }

        // Initialize the blender
        blender.initChannels(targetChannelNames, sourceChannelNames);

        // Calculate target area to loop through
        int w = (int) targetArea.getWidth();
        int h = (int) targetArea.getHeight();
        int x1 = (int) targetArea.getX();
        int x2 = x1 + w;
        int y1 = (int) targetArea.getY();
        int y2 = y1 + h;

        // Calculate source area to loop through
        float sx1 = sourceArea.getX();
        float sy1 = sourceArea.getY();
        float sxd = (sx1 + sourceArea.getWidth()) / w;
        float syd = (sy1 + sourceArea.getHeight()) / h;

        // Iterate through the target pixels
        float sy = sy1;
        for (int y = y1; y < y2; y++, sy += syd) {
            float sx = sx1;
            for (int x = x1; x < x2; x++, sx += sxd) {

                // Get source pixel data from channels
                for (int i = 0; i < sourceChannels.size; i++) {
                    sourceData.items[i] = sourceChannels.get(i).getRelativePixel(sx, sy);
                }

                // Get target pixel data from channels
                for (int i = 0; i < targetChannels.size; i++) {
                    targetData.items[i] = targetChannels.get(i).getPixel(x, y);
                }

                // Blend source onto target
                blender.blend(targetData, sourceData);

                // Store changes
                for (int i = 0; i < targetChannels.size; i++) {
                    targetChannels.get(i).setPixel(x, y, targetData.items[i]);
                }
            }
        }
    }

    public void renderTransformedPicture(Picture target, PictureSampler source, Matrix3 transformation, Blender blender) {
        // TODO

    }


}
