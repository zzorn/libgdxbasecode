package org.crawl;

import org.crawl.screens.CrawlLevelScreen;
import org.gameflow.Screen;
import org.gameflow.levels.LevelBase;

/**
 *
 */
public class CrawlLevel extends LevelBase {

    private final CrawlGame crawlGame;

    public CrawlLevel(String levelId, String nextLevelId, CrawlGame crawlGame) {
        super(levelId, nextLevelId);
        this.crawlGame = crawlGame;
    }

    @Override
    protected Screen createLevelScreen() {
        return new CrawlLevelScreen(this, crawlGame);
    }

}
