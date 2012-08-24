package org.gameflow.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * A screen implementation with a sprite stage and other utilities.
 */
public abstract class Screen2D extends ScreenBase {

    private BitmapFont font;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;

    public Screen2D() {
        super(null);
    }

    public Screen2D(String id) {
        super(id);
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public final void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        stage = new Stage(0, 0, true);

        onCreate();
    }

    protected abstract void onCreate();

    @Override
    public void update(float deltaSeconds) {
        // update the actors
        stage.act(deltaSeconds);

        onUpdate(deltaSeconds);
    }

    protected void onUpdate(float deltaSeconds) {}

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage actors
        stage.draw();

        batch.begin();
        onRender();
        batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    protected void onRender() {}

    @Override
    public void resize(int width, int height) {
        // Resize the stage
        stage.setViewport(width, height, true);

        onResize(width, height);
    }

    protected void onResize(int width, int height) {}

    @Override
    public final void dispose() {
        onDispose();

        font.dispose();
        batch.dispose();
        stage.dispose();
        if (skin != null) {
            skin.dispose();
        }
    }

    protected abstract void onDispose();

    protected Skin getSkin() {
        if( skin == null ) {
            skin = new Skin( Gdx.files.internal( "uiskin.json" ), Gdx.files.internal( "uiskin.png" ) );
        }
        return skin;
    }



    protected TextButton createButton(String text, ClickListener listener) {
        TextButton button = new TextButton(getSkin());
        button.setText(text);
        button.setClickListener(listener);
        return button;
    }


}
