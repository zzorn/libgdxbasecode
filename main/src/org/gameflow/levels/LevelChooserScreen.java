package org.gameflow.levels;

import com.badlogic.gdx.utils.Array;
import org.gameflow.Screen;

/**
 * Screen used to pick a level to play.
 * Should call LevelService.startLevel with the id of the selected level.
 */
public interface LevelChooserScreen extends Screen {

    /**
     * Called to inform the screen about the levels that are available, when they change.
     */
    public void updateUnlockedLevels(Array<LevelInfo> unlockedLevelInfos);

}
