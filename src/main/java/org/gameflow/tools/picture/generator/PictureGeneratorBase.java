package org.gameflow.tools.picture.generator;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;

import java.util.Random;

/**
 *
 */
public abstract class PictureGeneratorBase extends GeneratorMemberBase implements PictureGenerator {

    private String name = "test_picture";
    private int number = 1;
    private int width = 128;
    private int height = 128;
    private long randomSeed = 42;
    private final Random random = new Random();

    protected PictureGeneratorBase() {
    }

    protected PictureGeneratorBase(String name) {
        setName(name);
    }

    protected PictureGeneratorBase(String name, int width, int height) {
        setName(name);
        setSize(width, height);
        setNumber(1);
    }

    protected PictureGeneratorBase(String name, int width, int height, int numPics) {
        setName(name);
        setSize(width, height);
        setNumber(numPics);
    }

    protected PictureGeneratorBase(String name, int width, int height, int numPics, long randomSeed) {
        setName(name);
        setSize(width, height);
        setNumber(numPics);
        setRandomSeed(randomSeed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int width, int height) {
        this.width  = width;
        this.height = height;
    }

    protected Random getRandom() {
        return random;
    }
}
