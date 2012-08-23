package org.crawl.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import org.crawl.CrawlGame;
import org.gameflow.Screen2D;
import org.gameflow.levels.Level;

/**
 *
 */
public class CrawlLevelScreen extends Screen2D {

    private final Level level;
    private final CrawlGame crawlGame;

    public CrawlLevelScreen(Level level, CrawlGame crawlGame) {
        this.level = level;
        this.crawlGame = crawlGame;
    }

    @Override
    protected void onCreate() {

        Table table = new Table(getSkin());

        TextButton lvl= new TextButton(getSkin());
        lvl.setText(level.getLevelId());
        table.add(lvl);

        TextButton fail = new TextButton(getSkin());
        fail.setText("Fail level :~(");
        table.add(fail);
        fail.setClickListener(new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                crawlGame.levelService.startLevel(crawlGame.levelGenerator.getStartLevel());
            }
        });

        TextButton win = new TextButton(getSkin());
        win.setText("Win level! :D");
        table.add(win);
        win.setClickListener(new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                Array<String> nextLevels = crawlGame.levelService.levelCompleted(level);
                if (nextLevels.size > 0) crawlGame.levelService.startLevel(nextLevels.get(0));
                else crawlGame.levelService.startLevel(crawlGame.levelGenerator.getStartLevel());
            }
        });

        table.setFillParent(true);
        //table.layout();
        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {

    }
}
