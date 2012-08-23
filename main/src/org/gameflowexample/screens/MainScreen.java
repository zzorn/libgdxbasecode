package org.gameflowexample.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import org.gameflow.Screen2D;
import org.gameflowexample.ExampleGame;

public class MainScreen extends Screen2D {

    private final ExampleGame exampleGame;

    public MainScreen(ExampleGame exampleGame) {
        super("MainScreen", false);
        this.exampleGame = exampleGame;
    }

    @Override
    protected void onCreate() {

        Table table = new Table(getSkin());

        table.add(createButton("Start game", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                exampleGame.levelService.startFirstLevel();
            }
        }));

        table.add(createButton("Options", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                exampleGame.changeScreen(exampleGame.optionsScreen);
            }
        }));

        table.add(createButton("Quit game", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                Gdx.app.exit();
            }
        }));

        table.setFillParent(true);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {
    }

}
