package org.gameflowexample;

import org.gameflow.levels.*;

/**
 *
 */
public class ExampleLevelGenerator extends LevelGeneratorBase {

    private final ExampleGame exampleGame;

    public ExampleLevelGenerator(ExampleGame exampleGame) {
        super("1");
        this.exampleGame = exampleGame;
    }

    @Override
    protected Level createLevel(String levelId) {
        return new ExampleLevel(levelId, getNextLevelId(levelId), exampleGame);
    }

    private String getNextLevelId(String levelId) {
        if (levelId.equals("100")) {
            // Last level, game finished
            return "" + levelId;
        }
        else {
            return "" + (Integer.parseInt(levelId) + 1);
        }
    }

}
