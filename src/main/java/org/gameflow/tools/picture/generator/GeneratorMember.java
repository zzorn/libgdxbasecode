package org.gameflow.tools.picture.generator;

import org.gameflow.tools.picture.Picture;

/**
 *
 */
public interface GeneratorMember {

    void generatorInit(PictureGenerator pictureGenerator, int generatorSeed);
    void pictureInit(PictureGenerator pictureGenerator, int generatorSeed, int pictureSeed);

}
