package org.gameflow;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Base class for games.
 * Implementations should implement the setup() method, and add any needed services and screens from it.
 */
public abstract class GameBase implements Game {

    public static final String LOG_TAG = "BaseGame";
    private Array<Service> services = new Array<Service>();
    private Array<Screen> screens = new Array<Screen>();
    private Array<Screen> createdScreens = new Array<Screen>();
    private Screen currentScreen = null;
    private boolean running = false;

    @Override
    public String getApplicationName() {
        return getClass().getSimpleName();
    }

    @Override
    public final <T extends Service> T addService(T service) {
        if (running) throw new IllegalStateException("Can not add a service when the application is running.");
        if (services.contains(service, true)) throw new IllegalArgumentException("The game already contains the service '"+service.getServiceName()+"'");

        services.add(service);
        return service;
    }

    @Override
    public final Screen addScreen(Screen screen) {
        if (screen == null) throw new IllegalArgumentException("Screen should not be null");
        if (screens.contains(screen, true)) throw new IllegalArgumentException("The game already contains the screen '" + screen.getId() + "'");

        screens.add(screen);

        if (currentScreen == null) {
            currentScreen = screen;

            showScreen(screen);
        }

        return screen;
    }

    @Override
    public final void removeScreen(Screen screen) {
        if (!screens.contains(screen, true)) throw new IllegalArgumentException("The game does not contains a screen '" + screen.getId() + "'");
        Gdx.app.log(LOG_TAG, "Removing screen " + screen.getId());

        if (currentScreen == screen) {
            hideScreen(screen);
            currentScreen = null;
        }

        disposeScreen(screen);

        screens.removeValue(screen, true);
    }

    private void disposeScreen(Screen screen) {
        if (createdScreens.contains(screen, true)) {
            Gdx.app.log(LOG_TAG, "Disposing screen " + screen.getId());
            screen.pause();
            screen.dispose();
            createdScreens.removeValue(screen, true);
        }
    }

    @Override
    public final void changeScreen(String screenId) {
        for (Screen screen : screens) {
            if (screen.getId().equals(screenId)) {
                changeScreen(screen);
                return;
            }
        }

        throw new IllegalArgumentException("Unknown screen '" + screenId + "'");
    }

    @Override
    public final void changeScreen(Screen screen) {
        if (!screens.contains(screen, true)) throw new IllegalArgumentException("Unknown screen '" + screen.getId() + "'");
        if (currentScreen != screen) {
            String oldScreenName = currentScreen == null ? "None" : currentScreen.getId();
            String newScreenName = screen == null ? "None" : screen.getId();
            Gdx.app.debug(LOG_TAG, "Changing from screen " + oldScreenName + " to " + newScreenName);

            hideScreen(currentScreen);

            currentScreen = screen;

            showScreen(currentScreen);
        }
    }

    @Override
    public final void addAndChangeToScreen(Screen screen) {
        if (!screens.contains(screen, true)) {
            addScreen(screen);
        }

        changeScreen(screen);
    }

    @Override
    public final boolean isRunning() {
        return running;
    }

    /**
     * Called when the application is starting up.
     * Can be used to initialize and register the services and screens.
     */
    protected abstract void setup();

    /**
     * Called when setup is done and the services and current screen have been created.
     */
    protected void onSetupDone() {}

    /**
     * Called before shutdown of services and screens starts.
     */
    protected void onShutdownStarted() {}

    /**
     * Called after shutdown of services and screens is done.
     */
    protected void onShutdownDone() {}


    @Override
    public final void create() {
        Gdx.app.log(LOG_TAG, getApplicationName() + " starting up");

        setup();

        running = true;

        for (Service service : services) {
            Gdx.app.log(LOG_TAG, "Starting service " + service.getServiceName());
            service.create();
        }

        showScreen(currentScreen);

        onSetupDone();
    }

    @Override
    public final void resize(int width, int height) {
        Gdx.app.debug(LOG_TAG, "Resizing screen size to (" + width + ", " + height + ")");

        // Notify services
        for (Service service : services) {
            service.resize(width, height);
        }

        // Notify initialized screens
        for (Screen createdScreen : createdScreens) {
            createdScreen.resize(width, height);
        }
    }

    @Override
    public final void render() {
        // Do logic update
        doUpdate();

        // Notify services
        for (Service service : services) {
            service.render();
        }

        // Draw current screen
        if (currentScreen != null) {
            currentScreen.render();
        }
    }

    private void doUpdate() {
        // TODO: More complicated logic updates could be done here.
        // TODO: See http://gafferongames.com/game-physics/fix-your-timestep/

        float deltaSeconds = Gdx.graphics.getDeltaTime();

        // Notify services
        for (Service service : services) {
            service.update(deltaSeconds);
        }

        // Update current screen
        if (currentScreen != null) {
            currentScreen.update(deltaSeconds);
        }
    }

    private void showScreen(Screen screen) {
        if (screen != null) {
            if (running) {
                if (!createdScreens.contains(screen, true)) {
                    Gdx.app.log(LOG_TAG, "Creating screen " + screen.getId());
                    screen.create();
                }

                screen.show();

                // Call resize as well to let the screen know the dimensions
                screen.resize(Gdx.graphics.getWidth(),
                              Gdx.graphics.getHeight());

                if (!createdScreens.contains(screen, true)) {
                    createdScreens.add(screen);
                }
            }
        }
    }

    private void hideScreen(Screen screen) {
        if (screen != null) {
            if (running) {
                screen.hide();

                if (screen.getDisposeWhenClosed()) {
                    disposeScreen(screen);
                }
            }
        }
    }

    @Override
    public final void pause() {
        Gdx.app.log(LOG_TAG, "Pausing");

        // Notify services
        for (Service service : services) {
            service.pause();
        }

        // Notify initialized screens
        for (Screen createdScreen : createdScreens) {
            createdScreen.pause();
        }
    }

    @Override
    public final void resume() {
        Gdx.app.log(LOG_TAG, "Resuming");

        // Notify services
        for (Service service : services) {
            service.resume();
        }

        // Notify initialized screens
        for (Screen createdScreen : createdScreens) {
            createdScreen.resume();
        }
    }

    @Override
    public final void dispose() {
        Gdx.app.log(LOG_TAG, "Shutting down " + getApplicationName());

        onShutdownStarted();

        // Close created screens
        for (Screen createdScreen : createdScreens) {
            Gdx.app.log(LOG_TAG, "Disposing screen " + createdScreen.getId());
            createdScreen.dispose();
        }
        createdScreens.clear();
        currentScreen = null;

        // Close services
        for (Service service : services) {
            Gdx.app.log(LOG_TAG, "Shutting down service " + service.getServiceName());
            service.dispose();
        }

        running = false;

        onShutdownDone();

        Gdx.app.log(LOG_TAG, "Shutdown complete");
    }
}
