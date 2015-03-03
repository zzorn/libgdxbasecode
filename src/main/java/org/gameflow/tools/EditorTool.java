package org.gameflow.tools;

import org.gameflow.GameBase;

/**
 *
 */
public class EditorTool  extends GameBase {

    @Override
    protected void setup() {
        setScreen(new GraphicsEditScreen());
    }
}
