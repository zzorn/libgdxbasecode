package org.gameflow;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Base class for games.
 * Implementations should implement the setup() method, and add any needed services and screens from it.
 */
public abstract class BaseGame implements ApplicationListener {

    public static final String LOG_TAG = "BaseGame";
    private Array<Service> services = new Array<Service>();
    private ObjectMap<String, Screen> screens = new ObjectMap<String, Screen>();
    private Array<Screen> createdScreens = new Array<Screen>();
    private Screen currentScreen = null;
    private boolean running = false;

    /**
     * @return the name of the application, for logging etc.  Defaults to the simple name of the class.
     */
    public String getApplicationName() {
        return getClass().getSimpleName();
    }

    /**
     * Adds a service to the application.  Should be called in setup.
     * @param service the service to add.
     * @return the added service.
     */
    public final Service addService(Service service) {
        if (running) throw new IllegalStateException("Can not add a service when the application is running.");
        if (services.contains(service, true)) throw new IllegalArgumentException("The game already contains the service '"+service.getServiceName()+"'");

        services.add(service);
        return service;
    }

    /**
     * Add a screen to the application.  Can be called from setup.
     * If this is the first screen added, it is made the default start screen.
     * @param screen the screen to add.  Should have an unique id.
     * @return the added screen.
     */
    public final Screen addScreen(Screen screen) {
        String id = screen.getId();
        if (screens.containsKey(id)) throw new IllegalArgumentException("The game already contains a screen with the id '" + id + "'");

        screens.put(id, screen);

        if (currentScreen == null) {
            currentScreen = screen;
        }

        return screen;
    }

    /**
     * Changes the currently active screen.
     * @param screenName the name of the screen to change to.
     */
    public final void changeScreen(String screenName) {
        if (!screens.containsKey(screenName)) throw new Error("Unknown screen '" + screenName + "'");
        changeScreen(screens.get(screenName));
    }

    /**
     * Changes the currently active screen.
     * @param screen the screen to change to.
     */
    public final void changeScreen(Screen screen) {
        if (!screens.containsValue(screen, true)) throw new Error("Unknown screen '" + screen.getId() + "'");
        if (currentScreen != screen) {
            String oldScreenName = currentScreen == null ? "None" : currentScreen.getId();
            String newScreenName = screen == null ? "None" : screen.getId();
            Gdx.app.debug(LOG_TAG, "Changing from screen " + oldScreenName + " to " + newScreenName);

            hideScreen(currentScreen);

            currentScreen = screen;

            showScreen(currentScreen);
        }
    }

    /**
     * @return true if the game is running (has been created but not yet disposed).
     */
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
                    Gdx.app.log(LOG_TAG, "Disposing screen " + screen.getId());
                    screen.pause();
                    screen.dispose();
                    createdScreens.removeValue(screen, true);
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
