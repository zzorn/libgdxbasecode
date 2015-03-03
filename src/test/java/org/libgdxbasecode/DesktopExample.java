package org.libgdxbasecode;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.libgdxbasecode.gameflowexample.ExampleGame;

/**
 * Starts the example game as a desktop application.
 */
public class DesktopExample {
    public static void main(String[] args) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "GameFlow Example";
        cfg.width = 800;
        cfg.height = 600;

        new LwjglApplication(new ExampleGame(), cfg);
    }
}
