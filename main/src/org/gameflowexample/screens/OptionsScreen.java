package org.gameflowexample.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import org.gameflow.Screen2D;
import org.gameflowexample.ExampleGame;

/**
 * Screen for tuning options.
 */
public class OptionsScreen extends Screen2D {

    private final ExampleGame exampleGame;

    public OptionsScreen(ExampleGame exampleGame) {
        this.exampleGame = exampleGame;
    }

    @Override
    protected void onCreate() {
        Table table = new Table(getSkin());

        table.add(createButton("Return", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                exampleGame.changeScreen(exampleGame.mainScreen);
            }
        }));

        table.setFillParent(true);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {
    }
}
