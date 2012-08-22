package org.gameflow.levels;

import com.badlogic.gdx.utils.Array;

/**
 * Information about a level, e.g. for level selection purposes.
 */
public interface LevelInfo {

    /**
     * @return id of the level.
     */
    String getLevelId();

    /**
     * @return name of the level.
     */
    String getName();

    /**
     * @return levels that are unlocked when completing this level.
     */
    Array<String> getNextLevels();

}
