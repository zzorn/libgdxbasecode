package org.gameflow.levels;

import org.gameflow.Screen;

/**
 * Base class for levels.
 */
public abstract class LevelBase implements Level {

    private final LevelInfo levelInfo;
    private Screen levelScreen = null;

    protected LevelBase(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    @Override
    public final String getLevelId() {
        return levelInfo.getLevelId();
    }

    @Override
    public final LevelInfo getInfo() {
        return levelInfo;
    }

    @Override
    public Screen getScreen() {
        if (levelScreen == null) {
            levelScreen = createLevelScreen();
        }

        return levelScreen;
    }

    protected abstract Screen createLevelScreen();

    @Override
    public final void dispose() {
        if (levelScreen != null) {
            levelScreen.pause();
            levelScreen.dispose();
            levelScreen = null;
            onDispose();
        }
    }

    protected abstract void onDispose();
}
