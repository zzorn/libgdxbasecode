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

    public void blend(float[] targetChannelValues, float[] sourceChannelValues) {
        float value = sourceChannelValues[valueChan];
        targetChannelValues[redChan]   = value;
        targetChannelValues[greenChan] = value;
        targetChannelValues[blueChan]  = value;
        targetChannelValues[alphaChan] = 1;
    }
}
