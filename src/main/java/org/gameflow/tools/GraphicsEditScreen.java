package org.gameflow.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import org.gameflow.screen.Screen2D;
import org.gameflow.tools.picture.Picture;
import org.gameflow.tools.picture.blender.GradientBlender;
import org.gameflow.tools.picture.blender.ValueToGreyscaleBlender;
import org.gameflow.tools.picture.effect.PasteEffect;
import org.gameflow.tools.picture.exporter.PixmapExporter;
import org.gameflow.tools.picture.generator.PictureGenerator;
import org.gameflow.tools.picture.generator.SimplePictureGenerator;
import org.gameflow.tools.picture.gradient.*;
import org.gameflow.tools.picture.gradient.Gradient;
import org.gameflow.tools.picture.sampler.ConstantChannel;
import org.gameflow.tools.picture.sampler.NoiseChannel;
import org.gameflow.tools.picture.sampler.SampledPicture;

import java.util.List;

/**
 *
 */
public class GraphicsEditScreen extends Screen2D {

    private static final int SIZE = 256;
    private static final Color MID_COLOR = new Color(0.8f, 0.7f, 0.5f, 1);
    private static final Color EDGE_COLOR = new Color(0.3f, 0.2f, 0.3f, 1f);
    private Texture texture1;
    private Texture texture2;
    private Texture texture3;
    private Texture texture4;
    //private Pixmap pixmap;

    private final PixmapExporter pixmapExporter = new PixmapExporter();
    private PictureGenerator pictureGenerator;

    private List<Picture> pictures;
    private List<Pixmap> pixmaps;

    public GraphicsEditScreen() {
    }

    @Override
    protected void onCreate() {

        pictureGenerator = createPictureGenerator();
        pictures = pictureGenerator.generatePictures();
        pixmaps = pixmapExporter.exportPictures(pictures, pixmaps);
        texture1 = new Texture(pixmaps.get(0));
        texture2 = new Texture(pixmaps.get(1));
        texture3 = new Texture(pixmaps.get(2));
        texture4 = new Texture(pixmaps.get(3));

        buildUi();
    }

    private SimplePictureGenerator createPictureGenerator() {
        SimplePictureGenerator generator = new SimplePictureGenerator("TestPic", 256, 256, 4, 142);

        Gradient gradient = new Gradient("red", "green", "blue", "alpha");
        gradient.addPoint(0.0, 0, 0, 0, 1);
        gradient.addPoint(0.2, 1, 0, 0, 1);
        gradient.addPoint(0.4, 1, 1, 0, 1);
        gradient.addPoint(0.6, 1, 0, 1, 1);
        gradient.addPoint(0.8, 0, 0, 1, 1);
        gradient.addPoint(1.0, 0, 1, 1, 1);

        System.out.println(gradient);
        System.out.println("gradient.getChannelValue(\"red\", 0.5f) = " + gradient.getChannelValue("red", 0.5f));

        GradientBlender blender = new GradientBlender(gradient);
        NoiseChannel noiseChannel = new NoiseChannel(2, 2, 6f, 1f, 0.5f, 142, 242);
        generator.addEffect(new PasteEffect(new SampledPicture("value", noiseChannel), blender));

//        SampledPicture pic = new SampledPicture();
//        pic.addChannel("value", new ConstantChannel(0.5f));
//        generator.addEffect(new PasteEffect(pic));

        return generator;
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
        imagePreview.add(new Image(texture1));
        imagePreview.add(new Image(texture2));
        imagePreview.row();
        imagePreview.add(new Image(texture3));
        imagePreview.add(new Image(texture4));
        table.add(imagePreview).right();


        getStage().addActor(table);
    }

    private void updateTexture(float deltaSeconds) {

        //pixmap.drawPixel(random.nextInt(width), random.nextInt(height), Color.PINK.toIntBits());



        /*
        time += deltaSeconds;
        pic.clearToColor(Color.ORANGE);

        BlobEffect blobEffect = new BlobEffect();
        blobEffect.setSize(100, 100);
        blobEffect.getNoiseScale(0.95f);
        blobEffect.setUseBlending(false);
        blobEffect.setGradient(new SimpleGradient(MID_COLOR, EDGE_COLOR));

        random.setSeed(232);
        float scale = 1.2f;
        for (int i = 0; i < 50; i++) {
            blobEffect.setSeed(i + 100);
            pic.drawEffect(blobEffect, random.nextInt(SIZE), random.nextInt(SIZE),
                    random.nextFloat() * scale + scale * 0.5f,
                    random.nextFloat() * scale + scale * 0.5f, false, false, time * (float) random.nextGaussian() * 0.1f);
            scale *= 0.97f;
        }

        Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, width, height,
                pic.getPixmap().getGLFormat(), pic.getPixmap().getGLType(), pic.getPixmap().getPixels());
        */


        /* TODO: Upgrade code

        Gdx.gl.glBindTexture(
                GL10.GL_TEXTURE_2D,
                texture1.getTextureObjectHandle());
        Gdx.gl.glBindTexture(
                GL10.GL_TEXTURE_2D,
                texture2.getTextureObjectHandle());
        Gdx.gl.glBindTexture(
                GL10.GL_TEXTURE_2D,
                texture3.getTextureObjectHandle());
        Gdx.gl.glBindTexture(
                GL10.GL_TEXTURE_2D,
                texture4.getTextureObjectHandle());

        for (Pixmap pixmap : pixmaps) {
            Gdx.gl.glTexSubImage2D(
                    GL10.GL_TEXTURE_2D,
                    0,
                    0, 0,
                    pixmap.getWidth(),
                    pixmap.getHeight(),
                    pixmap.getGLFormat(),
                    pixmap.getGLType(),
                    pixmap.getPixels());
        }
        */
    }

    @Override
    public void update(float deltaSeconds) {
        updateTexture(deltaSeconds);
    }

    @Override
    protected void onDispose() {
    }
}
