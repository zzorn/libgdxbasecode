package org.gameflow.levels;

import org.gameflow.Screen;

/**
 * The level with all data.
 */
public interface Level {

    /**
     * @return id of this level
     */
    String getLevelId();

    /**
     * @return generic information about the level.
     */
    LevelInfo getInfo();

    /**
     * @return a screen that the level can be played on.
     * Should call LevelService.levelCompleted when the player passes the level, so that the next levels get unlocked.
     * May call LevelService.startLevelChooser when player exits the level, to allow the player to pick another one.
     */
    Screen getScreen();

    /**
     * Frees any resources used by this level object.
     */
    void dispose();
}
