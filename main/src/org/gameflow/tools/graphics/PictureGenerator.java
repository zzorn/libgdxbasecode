package org.gameflow.tools.graphics;

import com.badlogic.gdx.utils.Array;
import org.gameflow.tools.Picture;
import org.gameflow.tools.raster.Raster;

/**
 * Generates one or more pictures, possibly based on some templates.
 */
public interface PictureGenerator {

    Array<Raster> generatePictures();

}
