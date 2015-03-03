package org.gameflow.tools.picture.blender;

import org.gameflow.tools.picture.gradient.Gradient;

/**
 *
 */
public final class GradientBlender extends BlenderBase {
    private Gradient gradient;
    private String sourceChannelName = "value";

    private float[] values;
    private int[] targetsForGradientChannels;
    private int sourceChannelId;

    public GradientBlender(Gradient gradient) {
        this.gradient = gradient;
        values = new float[gradient.getChannelCount()];
        targetsForGradientChannels = new int[gradient.getChannelCount()];
    }

    public Gradient getGradient() {
        return gradient;
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
    }

    public String getSourceChannelName() {
        return sourceChannelName;
    }

    public void setSourceChannelName(String sourceChannelName) {
        this.sourceChannelName = sourceChannelName;
    }

    @Override
    protected void onInit() {
        sourceChannelId = getSourceChannel(sourceChannelName);

        String[] channelNames = gradient.getChannelNames();
        for (int i = 0; i < channelNames.length; i++) {
            targetsForGradientChannels[i] = getTargetChannel(channelNames[i]);
        }
    }

    public void blend(float[] targetChannelValues, float[] sourceChannelValues) {
        float position = sourceChannelValues[sourceChannelId];

        values = gradient.getChannelValues(position, values);

        for (int i = 0; i < targetsForGradientChannels.length; i++) {
            int targetChannel = targetsForGradientChannels[i];
            if (targetChannel >= 0) targetChannelValues[targetChannel] = values[i];
        }
    }
}
