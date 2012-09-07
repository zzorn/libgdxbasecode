package org.gameflow.tools.picture.sampler;

import org.gameflow.utils.PerlinNoise;

/**
 *
 */
public class NoiseChannel extends ChannelSamplerBase {

    private float scaleX = 1;
    private float scaleY = 1;
    private float offsetX = 0;
    private float offsetY = 0;
    private float octaves = 3;
    private float amplitude = 1;
    private float resultOffset = 0;
    private int fillSeed = 42;
    private int edgeSeed = 142;
    private boolean tiled = true;

    public NoiseChannel() {
    }

    /**
     * Creates a non-tiling noise channel.
     * Allows fractional scaling.
     */
    public NoiseChannel(float scaleX, float scaleY, float octaves, float amplitude, float resultOffset, int seed) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.octaves = octaves;
        this.amplitude = amplitude;
        this.resultOffset = resultOffset;
        this.fillSeed = seed;
        this.tiled = false;
    }

    /**
     * Creates a tiling noise channel.
     * The scales need to be integers for the tiling to pan out.
     */
    public NoiseChannel(int scaleX, int scaleY, float octaves, float amplitude, float resultOffset, int fillSeed, int edgeSeed) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.octaves = octaves;
        this.amplitude = amplitude;
        this.resultOffset = resultOffset;
        this.fillSeed = fillSeed;
        this.edgeSeed = edgeSeed;
        this.tiled = true;
    }

    public float getRelativePixel(float x, float y) {
        if (tiled) {
            return resultOffset + amplitude * (float) PerlinNoise.tilingOctaveNoise(
                    offsAndScaleX(x),
                    offsAndScaleY(y),
                    octaves,
                    offsAndScaleX(0),
                    offsAndScaleY(0),
                    offsAndScaleX(1),
                    offsAndScaleY(1),
                    fillSeed + getPictureSeed(),
                    edgeSeed + getGeneratorSeed());
        } else {
            return resultOffset + amplitude * (float) PerlinNoise.octaveNoise(
                    offsAndScaleX(x),
                    offsAndScaleY(y),
                    octaves,
                    fillSeed + getPictureSeed());
        }
    }

    private float offsAndScaleY(float y) {
        return (offsetY + y) * scaleY;
    }

    private float offsAndScaleX(float x) {
        return (offsetX + x) * scaleX;
    }

    public int getFillSeed() {
        return fillSeed;
    }

    public void setFillSeed(int fillSeed) {
        this.fillSeed = fillSeed;
    }

    public int getEdgeSeed() {
        return edgeSeed;
    }

    public void setEdgeSeed(int edgeSeed) {
        this.edgeSeed = edgeSeed;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public boolean isTiled() {
        return tiled;
    }

    public void setTiled(boolean tiled) {
        this.tiled = tiled;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getOctaves() {
        return octaves;
    }

    public void setOctaves(float octaves) {
        this.octaves = octaves;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

}
