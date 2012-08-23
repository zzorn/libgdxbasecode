package org.gameflow;

import com.badlogic.gdx.ApplicationListener;
import org.gameflow.screen.Screen;

/**
 * Interface for applications.
 */
public interface Game extends ApplicationListener {

    /**
     * @return the name of the application, for logging etc.  Defaults to the simple name of the class.
     */
    String getApplicationName();

    /**
     * Adds a service to the application.  Should be called in setup.
     * @param service the service to add.
     * @return the added service.
     */
    <T extends Service> T addService(T service);

    /**
     * Add a screen to the application.  Can be called from setup.
     * If this is the first screen added, it is made the default start screen.
     * @param screen the screen to add.  Should have an unique id.
     * @return the added screen.
     */
    Screen addScreen(Screen screen);

    /**
     * Removes a screen from the application.
     */
    void removeScreen(Screen screen);

    /**
     * Adds the screen to the game if it is not already added, and changes to it.
     */
    void addAndChangeToScreen(Screen screen);

    /**
     * Changes the currently active screen.
     * @param screenId the id of the screen to change to.
     */
    void changeScreen(String screenId);

    /**
     * Changes the currently active screen.
     * @param screen the screen to change to.
     */
    void changeScreen(Screen screen);

    /**
     * @return true if the game is running (has been created but not yet disposed).
     */
    boolean isRunning();
}
