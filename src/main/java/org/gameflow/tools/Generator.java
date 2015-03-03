package org.gameflow.tools;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Matrix4;

/**
 *
 */
public interface Generator {

    void generate(Pixmap pixmap, Matrix4 transformation);

}
