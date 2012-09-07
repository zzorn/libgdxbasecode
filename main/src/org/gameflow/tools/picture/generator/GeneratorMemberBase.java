package org.gameflow.tools.picture.generator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GeneratorMemberBase implements GeneratorMember {

    private int generatorSeed;
    private int pictureSeed;
    private List<GeneratorMember> delegates = null;

    public final void generatorInit(PictureGenerator pictureGenerator, int generatorSeed) {
        this.generatorSeed = generatorSeed;

        if (delegates != null) {
            for (GeneratorMember delegate : delegates) {
                delegate.generatorInit(pictureGenerator, generatorSeed);
            }
        }

        onGeneratorInit(pictureGenerator, generatorSeed);
    }

    public final void pictureInit(PictureGenerator pictureGenerator, int generatorSeed, int pictureSeed) {
        this.generatorSeed = generatorSeed;
        this.pictureSeed = pictureSeed;

        if (delegates != null) {
            for (GeneratorMember delegate : delegates) {
                delegate.pictureInit(pictureGenerator, generatorSeed, pictureSeed);
            }
        }

        onPictureInit(pictureGenerator, generatorSeed, pictureSeed);
    }


    protected final int getGeneratorSeed() {
        return generatorSeed;
    }

    protected final int getPictureSeed() {
        return pictureSeed;
    }

    protected final void addGeneratorMember(GeneratorMember member) {
        if (delegates == null) delegates = new ArrayList<GeneratorMember>();

        delegates.add(member);
    }

    protected final void removeGeneratorMember(GeneratorMember member) {
        delegates.remove(member);
    }

    protected void onGeneratorInit(PictureGenerator pictureGenerator, int generatorSeed) {}
    protected void onPictureInit(PictureGenerator pictureGenerator, int generatorSeed, int pictureSeed) {}

}
