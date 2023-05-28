package com.skyshield.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skyshield.game.SkyShield;

import java.io.IOException;

public class MainMenu implements Screen {

    final SkyShield game;
    private Stage stage;
    OrthographicCamera camera;

    public MainMenu(final SkyShield game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 693);
    }


    @Override
    public void show() {
        Table table = new Table();
//        table.setFillParent(true);
        table.setDebug(true);
        table.setBounds(200, 380, 600, 100);
        Table table2 = new Table();
//        table2.setDebug(true);
        table2.setFillParent(true);
        stage.addActor(table);
        stage.addActor(table2);

        Skin skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
        TextButton newGameButton = new TextButton("New Game", skin);
        TextButton preferencesButton = new TextButton("Preferences", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(newGameButton).right().expandX();
        table2.add(newGameButton);
//        table.row().pad(10, 0, 10, 0);
        table.add(preferencesButton).right().expandX();
//        table.row();
        table.add(exitButton).right().expandX();
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    game.setScreen(new GameScreen(game));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dispose();
            }
        });

        preferencesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("pref");
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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