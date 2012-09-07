package org.gameflow.tools.picture.blender;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

/**
 *
 */
public final class ValueToGreyscaleBlender extends BlenderBase {

    private int valueChan;
    private int redChan;
    private int greenChan;
    private int blueChan;
    private int alphaChan;

    @Override
    protected void onInit() {
        valueChan = getSourceChannel("value");
        redChan   = getTargetChannel("red");
        greenChan = getTargetChannel("green");
        blueChan  = getTargetChannel("blue");
        alphaChan = getTargetChannel("alpha");
    }

    public void blend(FloatArray targetChannelValues, FloatArray sourceChannelValues) {
        float value = sourceChannelValues.get(valueChan);
        targetChannelValues.set(redChan, value);
        targetChannelValues.set(greenChan, value);
        targetChannelValues.set(blueChan, value);
        targetChannelValues.set(alphaChan, 1);
    }
}
