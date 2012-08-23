package org.gameflowexample.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import org.gameflow.screen.Screen2D;
import org.gameflowexample.ExampleGame;

/**
 * Screen for tuning options.
 */
public class OptionsScreen extends Screen2D {

    private static final String SOUND_ENABLED = "soundEnabled";

    private final ExampleGame game;

    public OptionsScreen(ExampleGame game) {
        this.game = game;
    }

    @Override
    protected void onCreate() {
        Table table = new Table(getSkin());
        table.setFillParent(true);

        // Toggle sounds enabled checkbox
        table.add(new Label("Sound Effects", getSkin())).left();
        final CheckBox soundEffectsCheckbox = new CheckBox(getSkin());
        soundEffectsCheckbox.setChecked(game.optionsService.get(SOUND_ENABLED, true));
        soundEffectsCheckbox.setClickListener(new ClickListener() {
            public void click(Actor actor, float x, float y) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.optionsService.set(SOUND_ENABLED, enabled);
                game.soundService.setEnabled(enabled);
                game.soundService.play(ExampleGame.Sounds.UI_CLICK);
            }
        });
        table.add(soundEffectsCheckbox).padLeft(16);
        table.row();

        table.add(createButton("Return", new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                game.changeScreen(game.mainScreen);
                game.soundService.play(ExampleGame.Sounds.UI_ACCEPT);
            }
        })).colspan(2);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {
    }
}
