package org.gameflow.levels;

import com.badlogic.gdx.utils.Array;
import org.gameflow.Service;

/**
 * Manages levels.
 */
public interface LevelService extends Service {

    /**
     * Starts playing the specified level.
     */
    void startLevel(String levelId);

    /**
     * Should be called when a level completes successfully.
     * Unlocks next levels.
     * May be followed with e.g. a call to startLevelChooser(), or directly startLevel().
     * @return the unlocked next levels.
     */
    Array<String> levelCompleted(LevelInfo levelInfo);

    /**
     * Opens the level chooser screen.
     */
    void startLevelChooser();

    /**
     * @return the currently active level, or null if none.
     */
    Level getCurrentLevel();


    /**
     * @return all unlocked levels.
     */
    Array<String> getUnlockedLevels();

    /**
     * Adds an unlocked level.
     */
    void addUnlockedLevel(String levelId);

    /**
     * Adds a set of unlocked levels.
     */
    void addUnlockedLevels(Array<String> unlockedLevels);

    /**
     * @return the level information objects for the unlocked levels.
     */
    Array<LevelInfo> getUnlockedLevelInfos();
}
