package com.skyshield.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skyshield.game.SkyShield;

import java.io.IOException;

public class LoadingScreen implements Screen {

    public final SkyShield game;
    private final Stage stage;
    OrthographicCamera camera;
    public static int frame;
    public static Texture text = new Texture(Gdx.files.internal("loading-screen/text.png"));
    public static Texture border = new Texture(Gdx.files.internal("loading-screen/border.png"));
    public static Texture bar;

    public LoadingScreen(final SkyShield game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 693);
    }


    @Override
    public void show() {
        Table bg = new Table();
        bg.setFillParent(true);
        stage.addActor(bg);
        bg.setBackground(new Image(new Texture(Gdx.files.internal("loading-screen/"+ MathUtils.random(1, 3) +".png"))).getDrawable());

        Image img = new Image(new Texture(Gdx.files.internal("loading-screen/bar.png")));
        img.setPosition(bg.getWidth()/2-img.getWidth()/2, 0);
        bg.add(img).bottom().pad(50).expand();
    }

    @Override
    public void render(float delta) {
        frame++;
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();

        game.batch.begin();

        switch(frame) {
            case 50 -> bar = new Texture(Gdx.files.internal("loading-screen/start.png"));
            case 100 -> bar = new Texture(Gdx.files.internal("loading-screen/more.png"));
            case 160 -> bar = new Texture(Gdx.files.internal("loading-screen/half.png"));
            case 220 -> bar = new Texture(Gdx.files.internal("loading-screen/almost.png"));
            case 300 -> {
                try {
                    frame = 0;
                    game.setScreen(new GameScreen(game));
                    bar = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(bar != null) game.batch.draw(bar, stage.getWidth()/2-text.getWidth()/2f, 50);
        game.batch.draw(border, stage.getWidth()/2-text.getWidth()/2f, 50);
        game.batch.draw(text, stage.getWidth()/2-text.getWidth()/2f, 50);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}