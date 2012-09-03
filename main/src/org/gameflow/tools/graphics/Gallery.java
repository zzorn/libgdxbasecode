package org.gameflow.tools.graphics;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.Picture;

/**
 * Generates one or more pictures, possibly based on some templates.
 */
public interface Gallery {

    Array<Picture> generatePictures();

}
