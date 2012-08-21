package org.gameflow;

/**
 *
 */
public abstract class BaseService implements Service {

    @Override
    public String getServiceName() {
        return getClass().getSimpleName();
    }

    @Override
    public void create() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
