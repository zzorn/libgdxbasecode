package org.crawl;

import org.gameflow.levels.*;

/**
 *
 */
public class CrawlLevelGenerator extends LevelGeneratorBase {

    private final CrawlGame crawlGame;

    public CrawlLevelGenerator(CrawlGame crawlGame) {
        super("1");
        this.crawlGame = crawlGame;
    }

    @Override
    protected Level createLevel(LevelInfo levelInfo) {
        return new CrawlLevel(levelInfo, crawlGame);
    }

    @Override
    public LevelInfo getLevelInfo(String levelId) {
        if (levelId.equals("100")) {
            // Last level, game finished
            return new LevelInfoBase(levelId, "Level " + levelId);
        }
        else {
            return new LevelInfoBase(levelId, "Level " + levelId, "" + (Integer.parseInt(levelId) + 1));
        }
    }

}
