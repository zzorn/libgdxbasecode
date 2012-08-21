package org.gameflow;

import com.badlogic.gdx.ApplicationListener;

/**
 * Interface for some screen in the application, e.g. main menu, game screen, options, etc.
 */
public interface Screen extends ApplicationListener  {

    /**
     * @return name of this screen.  Used e.g. when switching screens.
     */
    String getId();

    /**
     *
     * @return true if this screen should be disposed when it is navigated away from (and created when navigated back to),
     * false if it should only be disposed when the application closes.
     */
    boolean getDisposeWhenClosed();

    /**
     * Called when the screen has been activated.
     */
    void show();

    /**
     * Called when the screen has been closed.
     */
    void hide();

    /**
     * Logic update.
     * @param deltaSeconds number of seconds since last call.
     */
    void update(float deltaSeconds);
}
