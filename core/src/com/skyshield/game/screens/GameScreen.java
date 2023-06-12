package com.skyshield.game.screens;

import java.io.IOException;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skyshield.game.SkyShield;
import com.skyshield.game.gameLogic.entities.Buildings;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.gui.TextElements;
import com.skyshield.game.gui.camera.Camera;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.clock.Clock;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.dialog.DialogText;
import com.skyshield.game.gui.dialog.DialogTimer;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.utils.CountryTerritory;
import com.skyshield.game.utils.ItemsList;

public class GameScreen implements Screen {

    public static SkyShield game;
    public static int screenWidth = SkyShield.SCREEN_WIDTH;
    public static int screenHeight = SkyShield.SCREEN_HEIGHT;
    public static final float globalScale = (float) screenWidth / 2000;
    public static final float textureScale = (float) screenWidth / 1920;
    public static final float positionScale = (float) screenWidth / 1280;
    public static float screenSizeScale = 1;
    public static int gameSpeed = 1;
    public static final float WIDTH_TO_HEIGHT_RATIO = (float) GameScreen.screenWidth / GameScreen.screenHeight;
    private final Texture mapImage;
    public static int lastClickX, lastClickY;
    public static int inputX, inputY;
    public static Stage stage;
    public static Stage particleStage;
    public static Sprite lockedMapSprite;

    public GameScreen(final SkyShield game) throws IOException {
        GameScreen.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

//        particleStage = new Stage(new ScreenViewport());

        Camera.createCamera();

        mapImage = new Texture(Gdx.files.internal("bg-720.png"));

        Buildings.addBuildings();
        Buildings.cities.get(2).texture = new Texture(Gdx.files.internal("buildings/capital.png"));

//        Particles.initAtlas();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        Camera.camera.update();

        game.batch.setProjectionMatrix(Camera.camera.combined);

        drawMap();

        Buildings.drawBuildings();

        drawAirDefence();

        drawLockedMap();

        if (DialogActions.afterDialogActionActive) DialogActions.action();

        if (GUIComponents.dialogWindow == null
                && !DialogActions.afterDialogActionActive
                && GUIComponents.goldTable == null
                && !Phase.draw) {
            Attack.attack();
//            if(MathUtils.random(1, 1000) > 970) Rockets.spawnRocket("snovyda", "City-3", Rockets.spawn[2]);
        }

        if (Rockets.rockets != null) {
            AirDefence.findTargetsInRange();
            if (AirDefence.airDefRockets != null) AirDefence.moveRockets();
            Rockets.launchRockets();
        }

        inputListener();

//        drawLockedMapParticles();
//        particleStage.act();
//        particleStage.draw();

        if (GUIComponents.popUpImage != null) GUIComponents.showPopUpMenu();
        if (GUIComponents.movingButton != null) {
            GUIComponents.moveMovingButton();
            GUIComponents.showAvailableArea(GUIComponents.movingButton.getName().equalsIgnoreCase("mushlya"));
        }

        stage.act();
        stage.draw();

        Clock.drawClock();

        TextElements.draw();

        drawDialog();

        if(Phase.draw) Phase.drawPhase();
//        drawSmoke();

    }

    //    private void drawLockedMapParticles() {
//        if(CountryTerritory.territory>6) return;
//        particleStage.getBatch().begin();
//        for(LockedMapParticle particle : LockedMapParticle.particles) {
//            particle.act(1/60f);
//            particle.draw(particleStage.getBatch(), 0);
//        }
//        particleStage.getBatch().end();
//    }
    private void drawDialog() {
        if (GUIComponents.dialogWindow != null) {

            GUIComponents.dialogWindow.act(1 / 60f);

            if (DialogTimer.start == 0 && !GUIComponents.dialogWindowIsClosing) {
                DialogTimer.start = TimeUtils.millis();
                DialogTimer.textStart = TimeUtils.millis()+1000;
            }

            if (GUIComponents.dialogText == null && !GUIComponents.dialogWindowIsClosing) {
                DialogTimer.startText(DialogText.getText());
            } else if (!GUIComponents.dialogWindowIsClosing) {
                if (GUIComponents.dialogText.isWritten() && GUIComponents.skipButton != null) {
                    GUIComponents.skipButton.remove();
                    GUIComponents.skipButton = null;
                    GUIComponents.addOkButton();
                }
//                System.out.println(MathUtils.random());
                GUIComponents.updateDialogText();
            } else {
                if (GUIComponents.dialogWindow.getY() >= screenHeight) {
                    GUIComponents.dialogWindow.remove();
                    GUIComponents.dialogWindow = null;
                }
            }

        }
    }

    private void drawSmoke() {
//        game.batch.begin();
//        for(ParticleEffectPool.PooledEffect particle : Particles.pooled) {
//            particle.draw(game.batch, 1/60f);
//            if(particle.isComplete()) {
//                particle.free();
//            }
//        }
//        game.batch.end();
    }


    private void drawMap() {
        game.batch.begin();
        game.batch.draw(mapImage, 0, 0, GameScreen.screenWidth, GameScreen.screenHeight);
        game.batch.end();
    }

    private void drawLockedMap() {
        float alpha = CountryTerritory.getLockedMapAlpha();
        game.batch.begin();
        lockedMapSprite.draw(game.batch, alpha);
        game.batch.end();
    }

    private void drawAirDefence() {
        game.batch.begin();
        for (AirDef airDefUnit : AirDefence.airDefs) {
            game.batch.draw(airDefUnit.getTexture(),
                    airDefUnit.getPos()[0] - (float) airDefUnit.getTexture().getWidth() * textureScale / 4,
                    airDefUnit.getPos()[1] - (float) airDefUnit.getTexture().getHeight() * textureScale / 4,
                    airDefUnit.getTexture().getWidth() * textureScale / 2f,
                    airDefUnit.getTexture().getHeight() * textureScale / 2f);
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
        GUIComponents.addTimeTable();
        GUIComponents.addDialogTable();

        CountryTerritory.setTerritory(1);
        CountryTerritory.setMapPolygon();
        lockedMapSprite = new Sprite(CountryTerritory.getLockedTexture());
        Buildings.setDisabled();

        Clock.setFontSize((int) (20 * GameScreen.screenSizeScale));

        Attack.setRandomRocketOrders();


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
        stage.dispose();
    }

    public static void changeGameSpeed() {

        if (gameSpeed == 3) {
            gameSpeed = 1;
        } else {
            gameSpeed++;
        }
    }

    private static void inputListener() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            float[] inputCoords = Camera.getRelativeCoords(Gdx.input.getX(), screenHeight - Gdx.input.getY());
            if(GUIComponents.shopButton.isChecked() && inputCoords[1] < 200) return;

            Rectangle airDefHitbox = new Rectangle();
            Rectangle buildingHitbox;

            for (AirDef airDef : AirDefence.airDefs) {

                airDefHitbox.x = airDef.getPos()[0] - (float) airDef.getTexture().getWidth() * textureScale / 5;
                airDefHitbox.y = airDef.getPos()[1] - (float) airDef.getTexture().getHeight() * textureScale / 5;
                airDefHitbox.width = airDef.getTexture().getWidth() * textureScale / 2.5f;
                airDefHitbox.height = airDef.getTexture().getHeight() * textureScale / 2.5f;

                if (airDefHitbox.contains(inputCoords[0], inputCoords[1])) {

                    if (GUIComponents.airDefButtonJustPressed
                            || GUIComponents.sellTable != null
                            || GUIComponents.repairTable != null
                            || GUIComponents.dialogWindow != null) {
                        GUIComponents.airDefButtonJustPressed = false;
                        GUIComponents.buildingButtonJustPressed = true;
                    } else {
                        GUIComponents.addSellAirDefMenu(airDef);
                    }
                    break;
                }
            }

            for (Map.Entry<String, Rectangle> building : ItemsList.buildings.entrySet()) {

                buildingHitbox = building.getValue();

                if (buildingHitbox.contains(inputCoords[0], inputCoords[1])) {

                    if (GUIComponents.airDefButtonJustPressed
                            || GUIComponents.sellTable != null
                            || GUIComponents.repairTable != null
                            || GUIComponents.dialogWindow != null
                            || GUIComponents.buildingButtonJustPressed) {
                        GUIComponents.airDefButtonJustPressed = false;
                        GUIComponents.buildingButtonJustPressed = false;
                    } else {
                        GUIComponents.addRepairBuildingMenu(building.getKey(), "1000", 1000);
                    }
                    break;
                }
            }

            if (GUIComponents.airDefButtonJustPressed) GUIComponents.airDefButtonJustPressed = false;
            if (GUIComponents.buildingButtonJustPressed) GUIComponents.buildingButtonJustPressed = false;
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
    }


}

