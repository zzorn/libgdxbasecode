package org.crawl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.crawl.screens.MainScreen;
import org.crawl.screens.OptionsScreen;
import org.crawl.screens.SplashScreen;
import org.gameflow.BaseGame;

/**
 * Entry point for the game.
 */
public class CrawlGame extends BaseGame implements Services {

    @Override
    protected void setup() {
        addScreen(new SplashScreen());
        addScreen(new MainScreen());
        addScreen(new OptionsScreen());
    }

}
