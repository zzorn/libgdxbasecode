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

    private static final int SIZE = 256;
    private Texture texture;
    //private Pixmap pixmap;
    private Random random = new Random();
    private Pic pic;

    private float time = 0;

    @Override
    protected void onCreate() {
        //pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pic = new Pic(SIZE, SIZE, true, true);

        /*
        for (int i = 0; i < 64; i++) {
            pixmap.drawPixel(i, i, Color.PINK.toIntBits());
        }
        */


        texture = new Texture(pic.getPixmap());

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

        Table imagePreview = new Table();
        imagePreview.add(new Image(texture));
        imagePreview.add(new Image(texture));
        imagePreview.row();
        imagePreview.add(new Image(texture));
        imagePreview.add(new Image(texture));
        table.add(imagePreview).right();


        getStage().addActor(table);
    }

    private void updateTexture(float deltaSeconds) {
        Gdx.gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureObjectHandle());
        int width = pic.getWidth();
        int height = pic.getHeight();

        //pixmap.drawPixel(random.nextInt(width), random.nextInt(height), Color.PINK.toIntBits());

        time += deltaSeconds;
        pic.clearToColor(Color.BLUE);

        random.setSeed(232);
        float scale = SIZE * 0.8f;
        for (int i = 0; i < 40; i++) {
            pic.drawOval(random.nextInt(SIZE), random.nextInt(SIZE), random.nextFloat() * scale, random.nextFloat() * scale, time * (float) random.nextGaussian(), new SimpleGradient(Color.YELLOW, Color.RED));
            scale *= 0.97f;
        }


        Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, width, height,
                pic.getPixmap().getGLFormat(), pic.getPixmap().getGLType(), pic.getPixmap().getPixels());
    }

    @Override
    public void update(float deltaSeconds) {
        updateTexture(deltaSeconds);
    }

    @Override
    protected void onDispose() {
    }
}
