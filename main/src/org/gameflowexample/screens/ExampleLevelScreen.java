package org.gameflowexample.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import org.gameflowexample.ExampleGame;
import org.gameflow.screen.Screen2D;
import org.gameflow.services.levels.Level;

/**
 *
 */
public class ExampleLevelScreen extends Screen2D {

    private final Level level;
    private final ExampleGame game;

    public ExampleLevelScreen(Level level, ExampleGame game) {
        this.level = level;
        this.game = game;
    }

    @Override
    protected void onCreate() {

        Table table = new Table(getSkin());

        TextButton lvl= new TextButton(getSkin());
        lvl.setText(level.getLevelId());
        table.add(lvl);

        table.add(createButton("Fail level :~(", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.soundService.play(ExampleGame.Sounds.WHALE);
                game.changeScreen(game.mainScreen);
            }
        }));

        table.add(createButton("Win level! :D", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.soundService.play(ExampleGame.Sounds.FOOTFALL);
                Array<String> nextLevels = game.levelService.levelCompleted(level);
                if (nextLevels.size > 0) game.levelService.startLevel(nextLevels.get(0));
                else game.changeScreen(game.mainScreen);
            }
        }));

        table.setFillParent(true);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {

    }
}
