package org.libgdxbasecode.gameflowexample.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import org.libgdxbasecode.gameflowexample.ExampleGame;
import org.gameflow.screen.Screen2D;
import org.gameflow.services.levels.Level;
import org.libgdxbasecode.gameflowexample.entities.Ship2D;

/**
 *
 */
public class ExampleLevelScreen extends Screen2D {

    private final Level level;
    private final ExampleGame game;

    private ParticleEffect particleEffect;
    private ParticleEffect snowEffect;
    private Ship2D ship2D;

    public ExampleLevelScreen(Level level, ExampleGame game) {
        this.level = level;
        this.game = game;
    }

    @Override
    protected void onCreate() {

        Table table = new Table(getSkin());

        final String labelText = "Level " + level.getLevelId();
        Label lvl= new Label(labelText, getSkin());
        table.add(lvl).padBottom(50).colspan(2);
        table.row();

        double order = Math.random();
        if (order<0.5) {
            addFailButton(table);
            addWinButton(table);
        }
        else {
            addWinButton(table);
            addFailButton(table);

        }


        table.setFillParent(true);

        getStage().addActor(table);

        setAtlas(new TextureAtlas(Gdx.files.internal("images/images.pack")));
        /*
        TextureAtlas.AtlasRegion region = atlas.findRegion("imagename");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        */

        // Show level start effect
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/testparticles.properties"), getAtlas());
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        int x = (int)(Math.random() * w);
        int y = (int)(Math.random() * h);
        int cx = (int) (w/2);
        int cy = (int) (h/2);
        particleEffect.setPosition(cx, cy);
        particleEffect.start();

        snowEffect = new ParticleEffect();
        snowEffect.load(Gdx.files.internal("particles/snowstorm.properties"), getAtlas());
        snowEffect.setPosition(x, y);
        snowEffect.start();

        // Show ship
        ship2D = new Ship2D(getAtlas());
        ship2D.x = x;
        ship2D.y = y;
        addEntity(ship2D);


        // Add a fade-in effect to the whole stage
        getStage().getRoot().getColor().a = 0f;
        getStage().getRoot().addAction(Actions.fadeIn(0.5f));

    }

    private void addWinButton(Table table) {
        table.add(createButton("Win level! :D", new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                game.soundService.play(ExampleGame.Sounds.FOOTFALL);
                Array<String> nextLevels = game.levelService.levelCompleted(level);
                if (nextLevels.size > 0) game.levelService.startLevel(nextLevels.get(0));
                else game.setScreen(new MainScreen(game));
            }
        }));
    }

    private void addFailButton(Table table) {
        table.add(createButton("Fail level :~(", new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                game.soundService.play(ExampleGame.Sounds.WHALE);
                game.setScreen(new MainScreen(game));

            }
        }));
    }

    @Override
    protected void onRender() {
        particleEffect.draw(getBatch());
        snowEffect.draw(getBatch());
    }

    @Override
    protected void onUpdate(float deltaSeconds) {
        particleEffect.update(deltaSeconds);
        snowEffect.update(deltaSeconds);
    }

    @Override
    protected void onDispose() {
        particleEffect.dispose();
        snowEffect.dispose();
        getAtlas().dispose();
    }
}
