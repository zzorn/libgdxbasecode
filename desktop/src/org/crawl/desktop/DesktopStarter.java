package org.crawl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.crawl.CrawlGame;

/**
 * Starts the game as a desktop application.
 */
public class DesktopStarter {
    public static void main(String[] args) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "crawl";
        cfg.useGL20 = false;
        cfg.width = 480;
        cfg.height = 800;

        new LwjglApplication(new CrawlGame(), cfg);
    }
}
