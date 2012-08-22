package org.crawl;

import org.crawl.screens.MainScreen;
import org.crawl.screens.OptionsScreen;
import org.crawl.screens.SplashScreen;
import org.gameflow.GameBase;
import org.gameflow.Screen;
import org.gameflow.levels.LevelGenerator;
import org.gameflow.levels.LevelService;
import org.gameflow.levels.LevelServiceImpl;
import org.gameflow.options.InMemoryOptionsService;
import org.gameflow.options.OptionsService;

/**
 * Entry point for the game.
 */
public class CrawlGame extends GameBase implements Services {

    public final LevelGenerator levelGenerator = new CrawlLevelGenerator(this);
    public final OptionsService optionsService = addService(new InMemoryOptionsService());
    public final LevelService levelService = addService(new LevelServiceImpl(levelGenerator, this, optionsService));

    public final Screen splashScreen = addScreen(new SplashScreen());
    public final Screen mainScreen = addScreen(new MainScreen());
    public final Screen optionsScreen = addScreen(new OptionsScreen());

    @Override
    protected void setup() {
        levelService.startLevel(levelGenerator.getStartLevel());
    }

}
