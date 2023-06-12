package com.skyshield.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private final Stage stage;
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
        Table table2 = new Table();
//        table2.setDebug(true);
        table2.setFillParent(true);
        stage.addActor(table2);

        table2.setBackground(new Image(new Texture(Gdx.files.internal("main-menu-bg.png"))).getDrawable());

        Skin skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
        TextButton newGameButton = new TextButton("New Game", skin);
        TextButton preferencesButton = new TextButton("Preferences", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table2.add(newGameButton);
        table2.row().pad(10, 0, 10, 0);
//        table2.add(preferencesButton).right().expandX();
        table2.add(exitButton);
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