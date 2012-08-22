package org.gameflow.levels;

import com.badlogic.gdx.utils.Array;


/**
 * Base class for level infos.
 */
public class LevelInfoBase implements LevelInfo {
    private final String levelId;
    private final String name;
    private final Array<String> nextLevels = new Array<String>();

    public LevelInfoBase(String levelId) {
        this(levelId, levelId);
    }

    public LevelInfoBase(String levelId, String name) {
        this.levelId = levelId;
        this.name = name;
    }

    public LevelInfoBase(String levelId, String name, Array<String> nextLevels) {
        this.levelId = levelId;
        this.name = name;
        this.nextLevels.addAll(nextLevels);
    }

    public LevelInfoBase(String levelId, String name, String nextLevel) {
        this.levelId = levelId;
        this.name = name;
        this.nextLevels.add(nextLevel);
    }


    public final String getLevelId() {
        return levelId;
    }

    public final String getName() {
        return name;
    }

    public final Array<String> getNextLevels() {
        return nextLevels;
    }
}
