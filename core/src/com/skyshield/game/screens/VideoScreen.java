package com.skyshield.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.skyshield.game.SkyShield;
import com.skyshield.game.videos.Videos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;

public class VideoScreen implements Screen {

    public final SkyShield game;
    private final Stage stage;
    OrthographicCamera camera;

    public VideoScreen(final SkyShield game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 693);
    }


    @Override
    public void show() {
        if(!GameScreen.gameContinued && !GameScreen.gameLost && !GameScreen.win) {
            try {
                Videos.playVideo("intro");
                Videos.player.setOnCompletionListener(file -> {
                    try {
                        game.setScreen(new GameScreen(game));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if (GameScreen.win) {

        }else if (GameScreen.gameLost) {
            switch (GameScreen.gameLostState) {
                case "dam" -> {

                }
                case "weapons" -> {

                }
                case "powerstations" -> {

                }
                case "capital" -> {

                }
            }
        }
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Videos.player.update();

        game.batch.begin();
        Texture frame = Videos.player.getTexture();
        if (frame != null) game.batch.draw(frame, 0, 0);
        game.batch.end();
        if(frame == null) {

        }
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