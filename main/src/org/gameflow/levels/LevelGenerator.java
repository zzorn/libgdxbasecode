package org.gameflow.levels;

import com.badlogic.gdx.utils.Array;
import org.gameflow.Screen;

import java.util.List;

/**
 * Something that can generate levels.
 */
public interface LevelGenerator {

    /**
     * Do any needed initialization.
     */
    void startup();

    /**
     * @return the id of the default first level.
     */
    String getStartLevel();

    /**
     * @return the levels that are unlocked from the start.
     */
    Array<String> getInitiallyUnlockedLevels();

    /**
     * @return information about a specific level.
     */
    LevelInfo getLevelInfo(String levelId);

    /**
     * @return a level instance with the specified id.
     */
    Level getLevel(String levelId);

    /**
     * @return screen used to select the next level.
     */
    LevelChooserScreen createLevelSelectionScreen();

    /**
     * Clear out any allocated resources.
     * (Created screens will be disposed by the caller).
     */
    void shutdown();
}
