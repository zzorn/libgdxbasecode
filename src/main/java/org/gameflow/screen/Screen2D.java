package org.gameflow.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.gameflow.entity.Entity;

import java.util.Comparator;

/**
 * A screen implementation with a sprite stage and other utilities.
 */
public abstract class Screen2D extends ScreenBase {

    private double screenSizeScale = 1.0;

    private BitmapFont font;
    private SpriteBatch batch;
    private ScreenViewport viewport;
    private Stage stage;
    private Skin skin;

    private final Array<Entity> entities = new Array<Entity>();

    private TextureAtlas atlas;

    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private final Comparator<Entity> entityDrawOrderCompartor = new Comparator<Entity>() {
        public int compare(Entity o1, Entity o2) {
            if (o1.getDrawOrder() < o2.getDrawOrder()) return -1;
            else if (o1.getDrawOrder() > o2.getDrawOrder()) return 1;
            else return 0;
        }
    };


    public Screen2D() {
        super(null);
        this.atlas = null;
    }

    public Screen2D(TextureAtlas atlas) {
        super(null);
        this.atlas = atlas;
    }

    public Screen2D(TextureAtlas atlas, double screenSizeScale) {
        super(null);
        this.screenSizeScale = screenSizeScale;
        this.atlas = atlas;
    }

    public Screen2D(TextureAtlas atlas, String id) {
        super(id);
        this.atlas = atlas;
    }

    public double getScreenSizeScale() {
        return screenSizeScale;
    }

    public void setScreenSizeScale(double screenSizeScale) {
        this.screenSizeScale = screenSizeScale;
        if (stage != null) {
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Stage getStage() {
        return stage;
    }

    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public final void doCreate() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        inputMultiplexer.addProcessor(stage);

        // Create actors for entities that were added earlier
        for (Entity entity : entities) {
            createEntity(atlas, entity);
        }

        onCreate();
    }

    /**
     * Adds an entity to the screen.
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        createEntity(atlas, entity);
    }

    /**
     * Removes an entity from the screen.
     */
    public void removeEntity(Entity entity) {
        if (entities.contains(entity,  true)) {
            entities.removeValue(entity, true);
            if (isSceneCreated()) {
                entity.dispose();
            }
        }
    }

    protected abstract void onCreate();

    @Override
    public void update(float deltaSeconds) {
        // Update the actors
        stage.act(deltaSeconds);

        onUpdate(deltaSeconds);

        // Update entities
        for (Entity entity : entities) {
            entity.update(deltaSeconds);
        }

        // Sort entities by y coordinate
        entities.sort(entityDrawOrderCompartor);
    }

    protected void onUpdate(float deltaSeconds) {}

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Render entities
        for (Entity entity : entities) {
            entity.render(atlas, batch);
        }

        // Render screen
        onRender();
        batch.end();

        // Draw the stage actors
        stage.draw();

        // Render anything that needs to be on top of the ui
        batch.begin();
        for (Entity entity : entities) {
            entity.topLayerRender(atlas, batch);
        }
        onTopLayerRender();

        batch.end();
    }

    protected void onTopLayerRender() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    protected void onRender() {}

    @Override
    public void resize(int width, int height) {
        // Resize the stage
        viewport.update(width, height);

        onResize(width, height);
    }

    protected void onResize(int width, int height) {}

    public final void doDispose() {
        onDispose();

        // Dispose entities
        for (Entity entity : entities) {
            entity.dispose();
        }
        entities.clear();

        font.dispose();
        batch.dispose();
        stage.dispose();
        if (skin != null) {
            skin.dispose();
        }
    }

    protected abstract void onDispose();

    public Skin getSkin() {
        if( skin == null ) {
            skin = new Skin(Gdx.files.internal( "skins/uiskin.json" ),
                            new TextureAtlas(Gdx.files.internal( "skins/uiskin.atlas" )));
        }
        return skin;
    }



    public TextButton createButton(String text, ClickListener listener) {
        TextButton button = new TextButton(text, getSkin());
        button.addListener(listener);
        return button;
    }

    public ImageButton createImageButton(String icon,ClickListener listener){
        ImageButton imageButton = new ImageButton(
                new TextureRegionDrawable(atlas.findRegion(icon + "NotPressScaled")),
                new TextureRegionDrawable(atlas.findRegion(icon+"PressScaled")),
                new TextureRegionDrawable(atlas.findRegion(icon + "PressScaled")));
        imageButton.addListener(listener);
        return imageButton;
    }


    private void createEntity(TextureAtlas atlas, Entity entity) {
        if (isSceneCreated()) {
            entity.create(atlas);
            entity.showOnScreen(this);
        }
    }

    private boolean isSceneCreated() {
        return stage != null;
    }


}
