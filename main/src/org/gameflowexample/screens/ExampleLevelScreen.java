package org.gameflowexample.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Array;
import org.gameflowexample.ExampleGame;
import org.gameflow.screen.Screen2D;
import org.gameflow.services.levels.Level;
import org.gameflowexample.entities.Ship2D;

/**
 *
 */
public class ExampleLevelScreen extends Screen2D {

    private final Level level;
    private final ExampleGame game;

    private ParticleEffect particleEffect;
    private TextureAtlas atlas;
    private Ship2D ship2D;

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
                game.setScreen(new MainScreen(game));

            }
        }));

        table.add(createButton("Win level! :D", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.soundService.play(ExampleGame.Sounds.FOOTFALL);
                Array<String> nextLevels = game.levelService.levelCompleted(level);
                if (nextLevels.size > 0) game.levelService.startLevel(nextLevels.get(0));
                else game.setScreen(new MainScreen(game));
            }
        }));

        table.setFillParent(true);

        getStage().addActor(table);

        atlas = new TextureAtlas(Gdx.files.internal("images/images.pack"));
        /*
        TextureAtlas.AtlasRegion region = atlas.findRegion("imagename");
        Sprite sprite = atlas.createSprite("otherimagename");
        NinePatch patch = atlas.createPatch("patchimagename");
        */

        // Show level start effect
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/testparticles.properties"), atlas);
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        int x = (int)(Math.random() * w);
        int y = (int)(Math.random() * h);
        particleEffect.setPosition(x, y);
        particleEffect.start();

        // Show ship
        ship2D = new Ship2D(atlas);
        ship2D.x = x;
        ship2D.y = y;
        addEntity(ship2D);


        // Add a fade-in effect to the whole stage
        getStage().getRoot().color.a = 0f;
        getStage().getRoot().action( FadeIn.$(0.5f) );

    }

    @Override
    protected void onRender() {
        particleEffect.draw(getBatch());
    }

    @Override
    protected void onUpdate(float deltaSeconds) {
        particleEffect.update(deltaSeconds);
    }

    @Override
    protected void onDispose() {
        particleEffect.dispose();
        atlas.dispose();
    }
}
