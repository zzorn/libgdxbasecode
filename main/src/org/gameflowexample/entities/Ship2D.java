package org.gameflowexample.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.gameflow.entity.ImageEntity;

/**
 *
 */
public class Ship2D extends ImageEntity {

    private final float MAX_HORIZONTAL_SPEED = 100;
    private final float MAX_VERTICAL_SPEED = 100;

    private ParticleEffect engineEffect;
    private final TextureAtlas atlas;


    public Ship2D(TextureAtlas atlas) {
        super(atlas.findRegion("mushroomrocket"));
        this.atlas = atlas;
        touchable = false;
    }


    @Override
    public Actor create() {

        // Create particle effect for engine
        engineEffect = new ParticleEffect();
        engineEffect.load(Gdx.files.internal("particles/ion_engine.properties"), atlas);
        engineEffect.start();

        return this;
    }

    @Override
    public void dispose() {
        engineEffect.dispose();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        engineEffect.draw(spriteBatch);
    }

    @Override
    public void update(float timeDelta) {
        moveShip(timeDelta);
        engineEffect.setPosition(x + width / 2, y);
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

            // x: 4 (back), 2 (still), 0 (forward)
            // I'll translate the above values to (-2,0,2) so that my next
            // calculations are simpler
            float adjustedX = ( Gdx.input.getAccelerometerX() - 2f );
            if( adjustedX < - 2f ) adjustedX = - 2f;
            else if( adjustedX > 2f ) adjustedX = 2f;

            // y: -2 (left), 0 (still), 2 (right)
            float adjustedY = Gdx.input.getAccelerometerY();
            if( adjustedY < - 2f ) adjustedY = - 2f;
            else if( adjustedY > 2f ) adjustedY = 2f;

            // since 2 is 100% of movement speed, let's calculate the final
            // speed percentage
            adjustedX /= 2;
            adjustedY /= 2;

            // notice the inverted axis because the game is displayed in
            // landscape mode
            x += ( adjustedY * MAX_HORIZONTAL_SPEED * delta );
            y += ( - adjustedX * MAX_VERTICAL_SPEED * delta );

        }

        if (Gdx.input.isKeyPressed( Input.Keys.UP ) )    y += ( MAX_VERTICAL_SPEED * delta );
        if (Gdx.input.isKeyPressed( Input.Keys.DOWN ) )  y -= ( MAX_VERTICAL_SPEED * delta );
        if (Gdx.input.isKeyPressed( Input.Keys.LEFT ) )  x -= ( MAX_HORIZONTAL_SPEED * delta );
        if (Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ) x += ( MAX_HORIZONTAL_SPEED * delta );


        // make sure the ship is inside the stage
        if( x < 0 ) x = 0;
        else if( x > stage.width() - width ) x = stage.width() - width;
        if( y < 0 ) y = 0;
        else if( y > stage.height() - height ) y = stage.height() - height;
    }

}
