package org.gameflowexample.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.gameflow.tools.EditorTool;
import org.gameflowexample.ExampleGame;

/**
 *
 */
public class EditorToolStarter {
    public static void main(String[] args) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Editor Tool";
        cfg.useGL20 = false;
        cfg.width = 800;
        cfg.height = 600;

        new LwjglApplication(new EditorTool(), cfg);
    }
}
