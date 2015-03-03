package org.gameflow.tools.picture.generator;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.picture.Picture;

import java.util.List;

/**
 * Generates one or more pictures, possibly based on some templates.
 */
public interface PictureGenerator {

    String getName();

    List<Picture> generatePictures();

}
