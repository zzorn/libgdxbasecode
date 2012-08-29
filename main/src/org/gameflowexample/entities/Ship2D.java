package org.gameflowexample.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.gameflow.entity.Entity;
import org.gameflow.screen.Screen2D;

/**
 *
 * Based on some code from https://code.google.com/p/steigert-libgdx/
 */
public class Ship2D extends Entity {

    private final float MAX_HORIZONTAL_SPEED = 200;
    private final float MAX_VERTICAL_SPEED = 200;
    private final float SCREEN_SIZE_SCALE = 1;

    private ParticleEffect engineEffect;
    private final Image image;

    public float x;
    public float y;
    public float width = 32;
    public float height = 64;

    public Ship2D(TextureAtlas atlas) {
        image = new Image(atlas.findRegion("mushroomrocket"));
    }


    @Override
    protected void onCreate(TextureAtlas atlas) {

        // Create particle effect for engine
        engineEffect = new ParticleEffect();
        engineEffect.load(Gdx.files.internal("particles/ion_engine.properties"), atlas);
        engineEffect.start();

    }

    @Override
    public void onDispose() {
        engineEffect.dispose();
    }

    @Override
    public void render(TextureAtlas atlas, SpriteBatch spriteBatch) {
        engineEffect.draw(spriteBatch);
        image.x = x;
        image.y = y;
        image.draw(spriteBatch, 1);
    }

    @Override
    public void update(float timeDelta) {
        moveShip(timeDelta);
        engineEffect.setPosition(x * SCREEN_SIZE_SCALE + width * SCREEN_SIZE_SCALE / 2, y * SCREEN_SIZE_SCALE);
        engineEffect.update(timeDelta);
    }

    /**
     * Moves the ship around the screen.
     * <p>
     * Note that the "move speed" is multiplied by the delta time so that the
     * ship moves the configured amount of pixels independently of the current
     * FPS output.
     */
    public void moveShip(float delta )
    {
        // check the input and move the ship
        if( Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer) ) {

            float adjustedX = ( Gdx.input.getAccelerometerX());
            if( adjustedX < - 2f ) adjustedX = - 2f;
            else if( adjustedX > 2f ) adjustedX = 2f;

            float adjustedY = Gdx.input.getAccelerometerY() - 3.5f; // When tilted in normal viewing angle, keep rocket still.
            if( adjustedY < - 2f ) adjustedY = - 2f;
            else if( adjustedY > 2f ) adjustedY = 2f;

            // since 2 is 100% of movement speed, let's calculate the final
            // speed percentage
            adjustedX /= 2;
            adjustedY /= 2;

            x += -adjustedX * MAX_HORIZONTAL_SPEED * delta;
            y += -adjustedY * MAX_VERTICAL_SPEED * delta;

        }

        if (Gdx.input.isKeyPressed( Input.Keys.UP ) )    y += ( MAX_VERTICAL_SPEED * delta );
        if (Gdx.input.isKeyPressed( Input.Keys.DOWN ) )  y -= ( MAX_VERTICAL_SPEED * delta );
        if (Gdx.input.isKeyPressed( Input.Keys.LEFT ) )  x -= ( MAX_HORIZONTAL_SPEED * delta );
        if (Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ) x += ( MAX_HORIZONTAL_SPEED * delta );


        // make sure the ship is inside the stage
        if( x < 0 ) x = 0;
        else if( x > Gdx.graphics.getWidth() - width ) x = Gdx.graphics.getWidth() - width;
        if( y < 0 ) y = 0;
        else if( y > Gdx.graphics.getHeight() - height ) y = Gdx.graphics.getHeight() - height;
    }

}
