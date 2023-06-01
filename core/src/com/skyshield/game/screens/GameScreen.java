package com.skyshield.game.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skyshield.game.SkyShield;
import com.skyshield.game.gameLogic.events.OneTargetAttack;
import com.skyshield.game.gui.Camera;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.utils.CountryTerritory;

public class GameScreen implements Screen {

    public static SkyShield game;
    public static int screenWidth = SkyShield.SCREEN_WIDTH;
    public static int screenHeight = SkyShield.SCREEN_HEIGHT;
    public static final float globalScale = (float) 700 / screenHeight;
    public static float screenSizeScale = 1;
    public static final float WIDTH_TO_HEIGHT_RATIO = (float) GameScreen.screenWidth / GameScreen.screenHeight;
    private final Texture mapImage;
    public static int lastClickX, lastClickY;
    public static int inputX, inputY;
    public static Stage stage;


    public GameScreen(final SkyShield game) throws IOException {
        GameScreen.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Camera.createCamera();

        mapImage = new Texture(Gdx.files.internal("bg-720.png"));

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        Camera.camera.update();

        game.batch.setProjectionMatrix(Camera.camera.combined);

        drawMap();

        drawAirDefence();


        if (TimeUtils.nanoTime() - OneTargetAttack.attackStartTime < 30000000000f) {
            OneTargetAttack.attack();
        } else {
            Rockets.rockets = null;
            AirDefence.airDefRockets = null;
            OneTargetAttack.attackStartTime = TimeUtils.nanoTime();
            OneTargetAttack.attack();
        }

        AirDefence.findTargetsInRange();
        if (AirDefence.airDefRockets != null) AirDefence.moveRockets();

        if (GUIComponents.movingButton != null) {
            GUIComponents.moveMovingButton();
            GUIComponents.showAvailableArea();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Camera.moveCamera = true;
            lastClickX = Gdx.input.getX();
            lastClickY = Gdx.input.getY();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if (Camera.moveCamera) {

                inputX = Gdx.input.getX();
                inputY = Gdx.input.getY();

                if (inputX != lastClickX || inputY != lastClickY) {
                    Camera.changeCameraPos();
                }
            }

        } else if (Camera.moveCamera) Camera.moveCamera = false;

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();

    }

    private void drawMap() {
        game.batch.begin();
        game.batch.draw(mapImage, 0, 0, GameScreen.screenWidth, GameScreen.screenHeight);
        game.batch.end();
    }

    private void drawAirDefence() {
        game.batch.begin();
        for (AirDef airDefUnit : AirDefence.airDefs) {
            game.batch.draw(airDefUnit.getTexture(),
                    airDefUnit.getPos()[0] - (float) airDefUnit.getTexture().getWidth() / 2,
                    airDefUnit.getPos()[1] - (float) airDefUnit.getTexture().getHeight() / 2);
            game.batch.draw(airDefUnit.getCircleTexture(),
                    airDefUnit.getCircleHitbox().x, airDefUnit.getCircleHitbox().y,
                    airDefUnit.getCircleHitbox().width, airDefUnit.getCircleHitbox().height);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

        height = (int) (width / WIDTH_TO_HEIGHT_RATIO);

        Gdx.graphics.setWindowedMode(width, height);

        GameScreen.screenWidth = width;
        GameScreen.screenHeight = height;

        Camera.createCamera();
        Camera.resetCameraPos();

        screenSizeScale = (float) width / SkyShield.SCREEN_WIDTH;

        stage.clear();
        show();
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void show() {

        GUIComponents.setSkin("freezing/skin/freezing-ui.json");
        GUIComponents.addButtonsTable();
        GUIComponents.addStageInputListener();

        CountryTerritory.setTerritory(0);
        CountryTerritory.setMapPolygon();


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


}

