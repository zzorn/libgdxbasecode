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
    protected Level createLevel(String levelId) {
        return new CrawlLevel(levelId, getNextLevelId(levelId), crawlGame);
    }

    private String getNextLevelId(String levelId) {
        if (levelId.equals("100")) {
            // Last level, game finished
            return "" + levelId;
        }
        else {
            return "" + (Integer.parseInt(levelId) + 1);
        }
    }

}
