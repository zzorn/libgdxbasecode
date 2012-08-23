package org.gameflowexample;

import org.gameflow.services.sound.SoundResourceHandle;
import org.gameflow.services.sound.SoundService;
import org.gameflow.services.sound.SoundServiceImpl;
import org.gameflowexample.screens.MainScreen;
import org.gameflowexample.screens.OptionsScreen;
import org.gameflow.GameBase;
import org.gameflow.screen.Screen;
import org.gameflow.services.levels.LevelGenerator;
import org.gameflow.services.levels.LevelService;
import org.gameflow.services.levels.LevelServiceImpl;
import org.gameflow.services.options.InMemoryOptionsService;
import org.gameflow.services.options.OptionsService;

/**
 * Entry point for the game.
 */
public class ExampleGame extends GameBase {

    public final LevelGenerator levelGenerator = new ExampleLevelGenerator(this);
    public final OptionsService optionsService = addService(new InMemoryOptionsService());
    public final LevelService levelService = addService(new LevelServiceImpl(levelGenerator, this, optionsService));
    public final SoundService soundService = addService(new SoundServiceImpl());

//    public final Screen splashScreen = addScreen(new SplashScreen());

    public final Screen mainScreen = addScreen(new MainScreen(this));
    public final Screen optionsScreen = addScreen(new OptionsScreen(this));

    /**
     * The available sound files.
     */
    public enum Sounds implements SoundResourceHandle {
        UI_CLICK("sounds/giant_footfall.wav"),
        UI_ACCEPT("sounds/giant_footfall.wav"),
        FOOTFALL("sounds/giant_footfall.wav"),
        WHALE("sounds/whale.wav"),
        ;

        private final String fileName;

        private Sounds(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }


    // TODO: Do not store screens, instead create a new screen instance whenever it is switched to?
    // -> So only keep track of the current screen.

    @Override
    protected void setup() {
        changeScreen(mainScreen);
    }

}
