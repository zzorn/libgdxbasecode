package org.libgdxbasecode.gameflowexample;

import org.libgdxbasecode.gameflowexample.screens.ExampleLevelScreen;
import org.gameflow.screen.Screen;
import org.gameflow.services.levels.LevelBase;

/**
 *
 */
public class ExampleLevel extends LevelBase {

    private final ExampleGame exampleGame;

    public ExampleLevel(String levelId, String nextLevelId, ExampleGame exampleGame) {
        super(levelId, nextLevelId);
        this.exampleGame = exampleGame;
    }

    @Override
    protected Screen createLevelScreen() {
        return new ExampleLevelScreen(this, exampleGame);
    }

}
