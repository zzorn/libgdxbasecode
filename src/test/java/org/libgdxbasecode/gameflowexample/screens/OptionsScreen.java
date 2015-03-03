package org.libgdxbasecode.gameflowexample.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.gameflow.screen.Screen2D;
import org.libgdxbasecode.gameflowexample.ExampleGame;

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
        final CheckBox soundEffectsCheckbox = new CheckBox("", getSkin());
        soundEffectsCheckbox.setChecked(game.optionsService.get(SOUND_ENABLED, true));
        soundEffectsCheckbox.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.optionsService.set(SOUND_ENABLED, enabled);
                game.soundService.setEnabled(enabled);
                game.soundService.play(ExampleGame.Sounds.UI_CLICK);
            }
        });
        table.add(soundEffectsCheckbox).padLeft(16);
        table.row();

        table.add(createButton("Return", new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
                game.soundService.play(ExampleGame.Sounds.UI_ACCEPT);
            }
        })).colspan(2);

        getStage().addActor(table);
    }

    @Override
    protected void onDispose() {
    }
}
