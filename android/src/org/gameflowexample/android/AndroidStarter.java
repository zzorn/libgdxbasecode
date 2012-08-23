package org.gameflowexample.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import org.gameflowexample.ExampleGame;

/**
 * Starts the game on android.
 */
public class AndroidStarter extends AndroidApplication {

    /**
     * Called when the activity is first created.  Creates the game.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = false;
        cfg.useGL20 = false;

        initialize(new ExampleGame(), cfg);
    }

}
