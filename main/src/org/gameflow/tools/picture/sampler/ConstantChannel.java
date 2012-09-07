package org.gameflow.tools.picture.sampler;

/**
 *
 */
public class ConstantChannel extends ChannelSamplerBase {

    private float value = 0;

    public ConstantChannel() {
    }

    public ConstantChannel(float value) {
        this.value = value;
    }

    public float getRelativePixel(float x, float y) {
        return value;
    }
}
