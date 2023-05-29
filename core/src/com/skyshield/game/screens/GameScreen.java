package com.skyshield.game.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.skyshield.game.SkyShield;
import com.skyshield.game.gameLogic.events.OneTargetAttack;
import com.skyshield.game.objects.airDefence.AirDef;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.utils.MapPolygon;

public class GameScreen implements Screen {

    public static SkyShield game;
    public static final float globalScale = 700 / 693f;
    private final Texture mapImage;
    private int lastClickX, lastClickY;
    private int inputX, inputY;
    private final OrthographicCamera camera;
    private boolean moveCamera = false;
    private final Vector3 cameraPos = new Vector3((float) 1280 / 2, (float) 693 / 2, 0);
    private final Stage stage;
    private Polygon map;

    public GameScreen(final SkyShield game) throws IOException {
        GameScreen.game = game;
        mapImage = new Texture(Gdx.files.internal("bg-720.png"));
        AirDefence.addAirDef(new float[]{660, 420}, "SD-250-M");
        AirDefence.addAirDef(new float[]{893, 486}, "F-500");
        AirDefence.addAirDef(new float[]{1000, 300}, "F-500");
        AirDefence.addAirDef(new float[]{817, 320}, "SD-250-M");
        AirDefence.airDefRockets = new Array<>();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 693);
        camera.position.lerp(cameraPos, 1);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw items
        game.batch.begin();
        game.batch.draw(mapImage, 0, 0, mapImage.getWidth(), mapImage.getHeight());
        for (AirDef airDefUnit : AirDefence.airDef) {
            game.batch.draw(airDefUnit.getTexture(),
                    airDefUnit.getPos()[0] - (float) airDefUnit.getTexture().getWidth() / 2,
                    airDefUnit.getPos()[1] - (float) airDefUnit.getTexture().getHeight() / 2);
            game.batch.draw(airDefUnit.getCircleTexture(),
                    airDefUnit.getCircleHitbox().x, airDefUnit.getCircleHitbox().y,
                    airDefUnit.getCircleHitbox().width, airDefUnit.getCircleHitbox().height);
        }
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            moveCamera = true;
            lastClickX = Gdx.input.getX();
            lastClickY = Gdx.input.getY();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if (moveCamera) {

                inputX = Gdx.input.getX();
                inputY = Gdx.input.getY();

                if (inputX != lastClickX || inputY != lastClickY) {
                    changeCameraPos();
                }
            }

        } else if (moveCamera) moveCamera = false;
        if (TimeUtils.nanoTime() - OneTargetAttack.attackStartTime < 30000000000f) {
            OneTargetAttack.attack();
        } else {
            Rockets.rockets = null;
            AirDefence.airDefRockets = null;
            OneTargetAttack.attackStartTime = TimeUtils.nanoTime();
            OneTargetAttack.attack();
        }

        AirDefence.findTargetsInRange();
        if (AirDefence.airDefRockets != null) {
            AirDefence.moveRockets();
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setBounds(1280 - (float) 1280 / 4, 0, (float) 1280 / 4, 100);

        stage.addActor(table);

        map = new Polygon();
        map.setVertices(new MapPolygon("bg-normal-720-flipped.png").vertices);

        Skin skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
        TextButton zoomInButton = new TextButton("+", skin);
        TextButton zoomOutButton = new TextButton("-", skin);

        table.add(zoomInButton).right().expandX();
        table.add(zoomOutButton).right().expandX();

        zoomInButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                camera.zoom /= 1.1f;
            }
        });

        zoomOutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (camera.zoom != 1) {
                    camera.zoom *= 1.1f;
                    if (cameraPos.x > getMaxCameraX()) cameraPos.x = getMaxCameraX();
                    if (cameraPos.y > getMaxCameraY()) cameraPos.y = getMaxCameraY();
                    if (cameraPos.x < getMinCameraX()) cameraPos.x = getMinCameraX();
                    if (cameraPos.y < getMinCameraY()) cameraPos.y = getMinCameraY();
                    camera.position.lerp(cameraPos, 1);
                } else resetCameraPos();
            }
        });

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        mapImage.dispose();
    }

    private void resetCameraPos() {
        cameraPos.x = (float) 1280 / 2;
        cameraPos.y = (float) 693 / 2;
        camera.position.lerp(cameraPos, 1);
    }

    private void changeCameraPos() {

        if (cameraPos.x + (lastClickX - inputX) > getMinCameraX()
                && cameraPos.x + (lastClickX - inputX) < getMaxCameraX()
                && cameraPos.y - (lastClickY - inputY) > getMinCameraY()
                && cameraPos.y - (lastClickY - inputY) < getMaxCameraY()) {

            cameraPos.x += lastClickX - inputX;
            cameraPos.y -= lastClickY - inputY;

            camera.position.lerp(cameraPos, 1);
        }

        lastClickX = inputX;
        lastClickY = inputY;
    }

    private float getMaxCameraX() {
        return camera.viewportWidth - camera.zoom * (camera.viewportWidth / 2);
    }

    private float getMinCameraX() {
        return camera.zoom * (camera.viewportWidth / 2);
    }

    private float getMaxCameraY() {
        return camera.viewportHeight - camera.zoom * (camera.viewportHeight / 2);
    }

    private float getMinCameraY() {
        return camera.zoom * (camera.viewportHeight / 2);
    }














}

