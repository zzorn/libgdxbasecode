package org.gameflowexample.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 600;
        cfg.r = 8;
        cfg.g = 8;
        cfg.b = 8;
        cfg.a = 8;

        new LwjglApplication(new EditorTool(), cfg);
    }
}
