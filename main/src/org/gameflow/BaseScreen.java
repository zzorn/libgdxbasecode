package org.gameflow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.crawl.CrawlGame;

/**
 * Base class for game screens.
 */
public abstract class BaseScreen implements Screen {

    private final boolean disposeWhenClosed;
    private final String id;

    /**
     * @param id an unique id for this screen.
     */
    protected BaseScreen(String id) {
        this(id, true);
    }

    /**
     * @param id an unique id for this screen.
     * @param disposeWhenClosed whether the screen should always be disposed when it is closed (=some other screen opened), or if it should stay in memory.
     */
    protected BaseScreen(String id, boolean disposeWhenClosed) {
        if (id == null) id = getClass().getSimpleName();

        this.id = id;
        this.disposeWhenClosed = disposeWhenClosed;
    }

    @Override
    public final String getId() {
        return id;
    }

    @Override
    public final boolean getDisposeWhenClosed() {
        return disposeWhenClosed;
    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void update(float deltaSeconds) {}

    @Override
    public void render() {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

}
