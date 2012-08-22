package org.gameflow.levels;

import com.badlogic.gdx.utils.Array;
import org.gameflow.ServiceBase;
import org.gameflow.Game;
import org.gameflow.options.OptionsService;

/**
 * Implementation of LevelService.
 */
public class LevelServiceImpl extends ServiceBase implements LevelService {

    private static final String UNLOCKED_LEVELS_KEY = "LevelService.unlockedLevels";
    private final LevelGenerator levelGenerator;
    private final Game game;
    private final OptionsService optionsService;

    private Array<String> unlockedLevels = new Array<String>();
    private Array<LevelInfo> unlockedLevelInfos = new Array<LevelInfo>();

    private Level currentLevel = null;
    private LevelChooserScreen levelChooserScreen = null;

    public LevelServiceImpl(LevelGenerator levelGenerator, Game game, OptionsService optionsService) {
        this.levelGenerator = levelGenerator;
        this.game = game;
        this.optionsService = optionsService;
    }

    @Override
    public void create() {
        // Startup level generator
        levelGenerator.startup();

        // Get unlocked levels.
        unlockedLevels.addAll(levelGenerator.getInitiallyUnlockedLevels());
        unlockedLevels = optionsService.get(UNLOCKED_LEVELS_KEY, unlockedLevels);

        // Populate unlocked level infos.
        for (String unlockedLevel : unlockedLevels) {
            unlockedLevelInfos.add(levelGenerator.getLevelInfo(unlockedLevel));
        }
    }

    @Override
    public Level getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void startLevel(String levelId) {
        log("Changing level to '"+levelId+"'");

        // Hide previous level screen
        hideLevelScreen();

        // Change current level
        if (levelId != null && !levelId.trim().isEmpty()) {
            currentLevel = levelGenerator.getLevel(levelId);
        }

        // Show new level screen
        if (currentLevel != null) {
            game.addAndChangeToScreen(currentLevel.getScreen());
        }
    }

    @Override
    public Array<String> levelCompleted(LevelInfo levelInfo) {
        // Unlock new levels
        Array<String> nextLevels = levelInfo.getNextLevels();
        addUnlockedLevels(nextLevels);

        return nextLevels;
    }

    @Override
    public void startLevelChooser() {
        // Hide the level, if it is visible
        hideLevelScreen();

        // Try to create level chooser screen if needed
        if (levelChooserScreen == null) {
            levelChooserScreen = levelGenerator.createLevelSelectionScreen();
        }

        // Show level chooser screen if the generator created one
        if (levelChooserScreen != null) {
            // Notify level chooser of any new unlocked levels
            levelChooserScreen.updateUnlockedLevels(getUnlockedLevelInfos());

            // Switch to level chooser screen
            game.addAndChangeToScreen(levelChooserScreen);
        }
    }

    @Override
    public Array<String> getUnlockedLevels() {
        return unlockedLevels;
    }

    @Override
    public Array<LevelInfo> getUnlockedLevelInfos() {
        return unlockedLevelInfos;
    }

    @Override
    public void addUnlockedLevel(String levelId) {
        if (!unlockedLevels.contains(levelId, false)) {
            unlockedLevels.add(levelId);
            unlockedLevelInfos.add(levelGenerator.getLevelInfo(levelId));

            // Save
            optionsService.set(UNLOCKED_LEVELS_KEY, unlockedLevels);
        }
    }

    @Override
    public void addUnlockedLevels(Array<String> unlockedLevels) {
        for (String unlockedLevel : unlockedLevels) {
            addUnlockedLevel(unlockedLevel);
        }
    }

    @Override
    public void dispose() {
        levelGenerator.shutdown();
    }

    private void hideLevelScreen() {
        if (currentLevel != null) {
            game.removeScreen(currentLevel.getScreen());
            currentLevel.dispose();
            currentLevel = null;
        }
    }
}
