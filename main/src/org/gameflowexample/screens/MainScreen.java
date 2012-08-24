package org.gameflowexample.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import org.gameflow.screen.Screen2D;
import org.gameflowexample.ExampleGame;

public class MainScreen extends Screen2D {

    private final ExampleGame game;

    public MainScreen(ExampleGame game) {
        this.game =game;
    }

    @Override
    protected void onCreate() {

        Table table = new Table(getSkin());

        table.add(createButton("Start game", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.levelService.startFirstLevel();
                game.soundService.play(ExampleGame.Sounds.UI_CLICK);
            }
        })).fillX().padBottom(10);


        table.row();


        table.add(createButton("Options", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.setScreen(new OptionsScreen(game));
                game.soundService.play(ExampleGame.Sounds.UI_CLICK);
            }
        })).fillX().padBottom(10);

        table.row();

        table.add(createButton("Quit game", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.soundService.play(ExampleGame.Sounds.WHALE);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing
                }
                Gdx.app.exit();
            }
        })).fillX().padBottom(10);

        table.setFillParent(true);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {
    }

}
