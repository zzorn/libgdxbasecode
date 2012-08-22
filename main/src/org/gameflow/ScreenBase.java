package org.gameflow;

/**
 * Base class for game screens.
 */
public abstract class ScreenBase implements Screen {

    private final boolean disposeWhenClosed;
    private final String id;

    /**
     * @param id an unique id for this screen.
     */
    protected ScreenBase(String id) {
        this(id, true);
    }

    /**
     * @param id an unique id for this screen.
     * @param disposeWhenClosed whether the screen should always be disposed when it is closed (=some other screen opened), or if it should stay in memory.
     */
    protected ScreenBase(String id, boolean disposeWhenClosed) {
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
