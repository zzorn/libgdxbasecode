package org.gameflow.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import org.gameflow.screen.Screen2D;

import java.util.Random;

/**
 *
 */
public class GraphicsEditScreen extends Screen2D {

    private Texture texture;
    private Pixmap pixmap;
    private Random random = new Random();

    @Override
    protected void onCreate() {
        pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);

        for (int i = 0; i < 64; i++) {
            pixmap.drawPixel(i, i, Color.PINK.toIntBits());
        }

        texture = new Texture(pixmap);

        buildUi();
    }


    private void buildUi() {
        Table table= new Table();
        table.setFillParent(true);

        Table settingsTable = new Table();
        settingsTable.add(new Label("Editor", getSkin())).left();
        settingsTable.row();
        settingsTable.add(new Label("", getSkin())).expandY();
        table.add(settingsTable).left();

        Image image = new Image(texture);
        table.add(image).right();


        getStage().addActor(table);
    }

    private void updateTexture() {
        Gdx.gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureObjectHandle());
        int width = pixmap.getWidth();
        int height = pixmap.getHeight();

        pixmap.drawPixel(random.nextInt(width), random.nextInt(height), Color.PINK.toIntBits());

        Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, width, height,
                pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
    }

    @Override
    public void update(float deltaSeconds) {
        updateTexture();
    }

    @Override
    protected void onDispose() {
    }
}
