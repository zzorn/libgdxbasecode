package org.crawl;

import org.crawl.screens.CrawlLevelScreen;
import org.gameflow.Screen;
import org.gameflow.levels.LevelBase;
import org.gameflow.levels.LevelInfo;

/**
 *
 */
public class CrawlLevel extends LevelBase {

    private final CrawlGame crawlGame;

    public CrawlLevel(LevelInfo levelInfo, CrawlGame crawlGame) {
        super(levelInfo);
        this.crawlGame = crawlGame;
    }

    @Override
    protected Screen createLevelScreen() {
        return new CrawlLevelScreen(getInfo(), crawlGame);
    }

    @Override
    protected void onDispose() {
    }
}
