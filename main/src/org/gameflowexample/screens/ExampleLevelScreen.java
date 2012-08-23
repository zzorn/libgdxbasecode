package org.gameflowexample.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import org.gameflowexample.ExampleGame;
import org.gameflow.Screen2D;
import org.gameflow.levels.Level;

/**
 *
 */
public class ExampleLevelScreen extends Screen2D {

    private final Level level;
    private final ExampleGame exampleGame;

    public ExampleLevelScreen(Level level, ExampleGame exampleGame) {
        this.level = level;
        this.exampleGame = exampleGame;
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
                exampleGame.changeScreen(exampleGame.mainScreen);
            }
        }));

        table.add(createButton("Win level! :D", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                Array<String> nextLevels = exampleGame.levelService.levelCompleted(level);
                if (nextLevels.size > 0) exampleGame.levelService.startLevel(nextLevels.get(0));
                else exampleGame.changeScreen(exampleGame.mainScreen);
            }
        }));

        table.setFillParent(true);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {

    }
}
