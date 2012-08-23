package org.gameflowexample;

import org.gameflowexample.screens.MainScreen;
import org.gameflowexample.screens.OptionsScreen;
import org.gameflowexample.screens.SplashScreen;
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
public class ExampleGame extends GameBase {

    public final LevelGenerator levelGenerator = new ExampleLevelGenerator(this);
    public final OptionsService optionsService = addService(new InMemoryOptionsService());
    public final LevelService levelService = addService(new LevelServiceImpl(levelGenerator, this, optionsService));

//    public final Screen splashScreen = addScreen(new SplashScreen());

    public final Screen mainScreen = addScreen(new MainScreen(this));
    public final Screen optionsScreen = addScreen(new OptionsScreen(this));

    // TODO: Do not store screens, instead create a new screen instance whenever it is switched to?
    // -> So only keep track of the current screen.

    @Override
    protected void setup() {
        changeScreen(mainScreen);
    }

}
