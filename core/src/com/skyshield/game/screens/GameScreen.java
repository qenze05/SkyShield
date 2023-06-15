package com.skyshield.game.screens;

import java.io.IOException;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skyshield.game.SkyShield;
import com.skyshield.game.gameLogic.entities.Buildings;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.gameObjects.buildings.*;
import com.skyshield.game.gui.pause.PauseBg;
import com.skyshield.game.gui.pause.PauseMenu;
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
import com.skyshield.game.gui.shop.ShopBackground;
import com.skyshield.game.gui.shop.ShopScrollBar;
import com.skyshield.game.sound.GameDialog;
import com.skyshield.game.sound.GameMusic;
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
    private final Texture mapImage = new Texture(Gdx.files.internal("bg-720.png"));
    public static int lastClickX, lastClickY;
    public static int inputX, inputY;
    public static Stage stage, pauseStage;
    public static Stage particleStage;
    public static Sprite lockedMapSprite;
    public static boolean isPaused = false;
    public static boolean gameRestarted = false;
    public static boolean gameContinued = false;
    public static Array<Texture> disposableTextures = new Array<>();
    public static Array<BitmapFont> disposableFonts = new Array<>();
    public static Array<Sound> disposableSounds = new Array<>();
    public static boolean win = false;

    public GameScreen(final SkyShield game) throws IOException {
        GameScreen.game = game;

        if(gameRestarted) {
            screenWidth = SkyShield.SCREEN_WIDTH;
            screenHeight = SkyShield.SCREEN_HEIGHT;
            screenSizeScale = 1;
            gameSpeed = 1;
            lastClickX = 0;
            lastClickY = 0;
            inputX = 0;
            inputY = 0;
            stage = null;
            pauseStage = null;
            particleStage = null;
            lockedMapSprite = null;
            isPaused = false;
        }


        if(!gameContinued) {
            stage = new Stage(new ScreenViewport());
            pauseStage = new Stage(new ScreenViewport());
            GameScreen.game.resume();
        }
        Gdx.input.setInputProcessor(stage);
        if(gameContinued) return;

//        particleStage = new Stage(new ScreenViewport());

        if(gameRestarted) {
            Camera.camera = null;
            Camera.moveCamera = false;
            Camera.cameraPos = new Vector3((float) GameScreen.screenWidth / 2, (float) GameScreen.screenHeight / 2, 0);
        }

        Camera.createCamera();

        if(gameRestarted) {
            resetGame();
        }

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

        if (isPaused) {

            drawPauseMenu();
            return;
        }

        drawHpBars();

        if (DialogActions.afterDialogActionActive) {
            DialogActions.action();
        }

        if (GUIComponents.dialogWindow == null
                && !DialogActions.afterDialogActionActive
                && GUIComponents.goldTable == null
                && !Phase.draw) {
            Attack.attack();
            if(MathUtils.random(1, 100) > 90) Rockets.spawnRocket("snovyda", "Dam-2", Rockets.spawn[2]);
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

        if (Phase.draw) Phase.drawPhase();
//        drawSmoke();

    }

    public static void disposeGarbage() {
        for(Texture texture : disposableTextures) {
            texture.dispose();
        }
//        for(BitmapFont font : disposableFonts) {
//            font.dispose();
//        }
        for(Sound sound : disposableSounds) {
            sound.dispose();
        }
        disposableTextures.clear();
        disposableFonts.clear();
        disposableSounds.clear();
    }
    public static void drawPauseMenu() {
        if (pauseStage.getActors().size == 0) {
            pauseStage.addActor(new PauseBg());
            pauseStage.addActor(new PauseMenu());
        }
        pauseStage.act(1 / 60f);
        pauseStage.getActors().get(0).act(1 / 60f);
        pauseStage.draw();
    }

    public static void drawHpBars() {
        if (Buildings.hpBars.size == 0) return;
        game.batch.begin();
        for (Table bar : Buildings.hpBars) {
            bar.draw(game.batch, 1);
        }
        game.batch.end();
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
                DialogTimer.textStart = TimeUtils.millis() + 1000;
                GameDialog.removeSound();
                GameDialog.addSound();
            }

            if (GUIComponents.dialogText == null && !GUIComponents.dialogWindowIsClosing) {
                DialogTimer.startText(DialogText.getFormattedText());
            } else if (!GUIComponents.dialogWindowIsClosing) {
                if (GUIComponents.dialogText.isWritten() && GUIComponents.skipButton != null) {
                    GUIComponents.skipButton.remove();
                    GUIComponents.skipButton = null;
                    GUIComponents.addOkButton();
                }

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

//        height = (int) (width / WIDTH_TO_HEIGHT_RATIO);
//
//        Gdx.graphics.setWindowedMode(width, height);
//
//        GameScreen.screenWidth = width;
//        GameScreen.screenHeight = height;
//
//        Camera.createCamera();
//        Camera.resetCameraPos();
//
//        screenSizeScale = (float) width / SkyShield.SCREEN_WIDTH;
//
//        stage.clear();
//        show();
//        stage.getViewport().update(width, height, true);

    }

    @Override
    public void show() {

        if(gameContinued) {
            gameContinued = false;
            return;
        }

        if(gameRestarted) {
            CountryTerritory.map = new Polygon();
            CountryTerritory.island = CountryTerritory.getIslandPolygon();
            CountryTerritory.sea = CountryTerritory.getSeaPolygon();
            CountryTerritory.mapVertices = new float[100];
            CountryTerritory.territory = 0;
            CountryTerritory.lockedMapFrame = 0;
            CountryTerritory.alpha = 0;
        }


        GUIComponents.setSkin("freezing/skin/freezing-ui.json");
        GUIComponents.addButtonsTable();
        GUIComponents.addStageInputListener();
        GUIComponents.addTimeTable();
        GUIComponents.addDialogTable();
        GUIComponents.addNoMoneyTable();

        CountryTerritory.setTerritory(1);
        CountryTerritory.setMapPolygon();
        lockedMapSprite = new Sprite(CountryTerritory.getLockedTexture());
        Buildings.setDisabled();

        if(gameRestarted) {
            ItemsList.buildings = ItemsList.getBuildings();
            ItemsList.uniqueRockets = ItemsList.getUniqueRockets();
            ItemsList.uniqueAirDefs = ItemsList.getUniqueAirDefs();
            gameRestarted = false;
        }


        Clock.setFontSize((int) (20 * GameScreen.screenSizeScale));

        Attack.setRandomRocketOrders();

        GUIComponents.shopBackground = new ShopBackground();
        GUIComponents.shopScrollBar = new ShopScrollBar();


    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        isPaused = true;
        Gdx.input.setInputProcessor(pauseStage);
        drawPauseMenu();
        GameDialog.pauseSound();
        GameMusic.pauseSound();
    }

    @Override
    public void resume() {
        isPaused = false;
        pauseStage.clear();
        Gdx.input.setInputProcessor(stage);
        GameDialog.resumeSound();
        GameMusic.resumeSound();
    }

    @Override
    public void dispose() {
        mapImage.dispose();
        stage.dispose();
        particleStage.dispose();
        game.batch.dispose();
        Buildings.hpBarTexture.dispose();
        Clock.font.dispose();
        DialogText.font.dispose();
        Phase.labelTexture.dispose();
        Phase.bgTexture.dispose();

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

            if(GUIComponents.movingButton != null) return;

            float[] inputCoords = Camera.getRelativeCoords(Gdx.input.getX(), screenHeight - Gdx.input.getY());
            if (GUIComponents.shopButton.isChecked() && inputCoords[1] < 200) return;

            Rectangle airDefHitbox = new Rectangle();
            Rectangle buildingHitbox;

            for (AirDef airDef : AirDefence.airDefs) {

                if (airDef.getName().contains("OkoHora")) continue;

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

                        String name = building.getKey().toLowerCase().split("-")[0];
                        int number = Integer.parseInt(building.getKey().toLowerCase().split("-")[1]);

                        String[] values = GUIComponents.getRepairCost(name, number).split("-");
                        if (!values[1].equals("0"))
                            GUIComponents.addRepairBuildingMenu(building.getValue(), values[0], Integer.parseInt(values[1]));
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

    public static void addFailScreen() {
        game.pause();
        DialogText.textCounter = -1;
        GameDialog.removeSound();
        GameMusic.removeSound();
        game.setScreen(new FailScreen(GameScreen.game));
    }
    public static void resetGame() {
        AirDefence.airDefs = new Array<>();
        AirDefence.airDefRockets = new Array<>();
        AirDefence.cornerTargets = new Array<>();
        AirDefence.snovydaLost = false;
        AirDefence.airDefRocketSprite = null;
        Buildings.barracks = new Array<>();
        Buildings.cities = new Array<>();
        Buildings.dams = new Array<>();
        Buildings.factories = new Array<>();
        Buildings.hub1s = new Array<>();
        Buildings.hub2s = new Array<>();
        Buildings.hub3s = new Array<>();
        Buildings.superFactories = new Array<>();
        Buildings.powerStations = new Array<>();
        Buildings.hpBars = new Array<>();
        Rockets.rockets = null;
        Rockets.rocketSprite = null;
        Attack.coef = 1;
        Attack.phase = 1;
        Attack.attackStartTime = null;
        Attack.lastRocketSpawnTime = null;
        Attack.cooldown = null;
        Attack.eventCooldown = null;
        Attack.event2Cooldown = null;
        Attack.chessOrderValue = 1;
        Attack.dialogAdded = false;
        Attack.phase1Rockets = null;
        Attack.phase2Rockets = null;
        Attack.phase3Rockets = null;
        Attack.phase4Rockets = null;
        Attack.phase5Rockets = null;
        Attack.phase6Rockets = null;
        Barracks.trainedSoldiers = 0;
        City.totalPopulation = 0;
        City.totalMoney = 1300;
        Factory.rocketCount = 0;
        Hub3.trainedSoldiers = 0;
        SuperFactory.rocketCount = 0;

        Clock.time = new int[]{0, 0};
        Clock.day = 0;
        Clock.font = null;
        Clock.timeMillis = TimeUtils.millis();
        DialogActions.afterDialogActionActive = false;
        DialogActions.counter = -1;
        DialogText.textCounter = 1;
        DialogText.font = null;
        DialogTimer.start = 0;
        DialogTimer.textStart = 0;
        Phase.draw = false;
        Phase.label = null;
        Phase.bg = null;
        Phase.labelTexture = null;
        GUIComponents.shopBackground = null;
        GUIComponents.shopScrollBar = null;
        GUIComponents.skin = null;
        GUIComponents.movingButton = null;
        GUIComponents.okButton = null;
        GUIComponents.skipButton = null;
        GUIComponents.movingButtonCircle = null;
        GUIComponents.zoomInButton = null;
        GUIComponents.zoomOutButton = null;
        GUIComponents.shopButton = null;
        GUIComponents.gameSpeedButton = null;
        GUIComponents.animationFrame = 0;
        GUIComponents.popUpTimer = 0;
        GUIComponents.popUpTexture = null;
        GUIComponents.popUpImage = null;
        GUIComponents.airDefButtonJustPressed = false;
        GUIComponents.buildingButtonJustPressed = false;
        GUIComponents.sellTable = null;
        GUIComponents.repairTable = null;
        GUIComponents.dialogWindow = null;
        GUIComponents.dialogText = null;
        GUIComponents.dialogWindowIsClosing = false;
        GUIComponents.goldTable = null;
        GUIComponents.noMoneyTable = null;
        TextElements.moneyFont = null;
        TextElements.hpFont = null;
        TextElements.sellValue = -1;
        TextElements.repairValue = -1;
        TextElements.hpValue = "";
    }


}

