package com.skyshield.game.screens;
import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.skyshield.game.SkyShield;
import com.skyshield.game.gameLogic.events.OneTargetAttack;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gameObjects.buildings.*;
import com.skyshield.game.gui.GUIComponents;
public class GameScreen implements Screen {

    public static SkyShield game;
    public static final float globalScale = 700 / 693f;
    private final Texture mapImage;
    private int lastClickX, lastClickY;
    private int inputX, inputY;
    public static OrthographicCamera camera;
    private boolean moveCamera = false;
    private final Vector3 cameraPos = new Vector3((float) 1280 / 2, (float) 693 / 2, 0);
    public static Stage stage;

    private final PowerStation powerStation1;
    private final PowerStation powerStation2;
    private final PowerStation powerStation3;
    private final PowerStation powerStation4;
    private final PowerStation powerStation5;
    private final PowerStation powerStation6;
    private final PowerStation powerStation7;
    private final PowerStation powerStation8;
    private final PowerStation powerStation9;
    private final PowerStation powerStation10;
    private final PowerStation powerStation11;
    private final PowerStation powerStation12;
    private final PowerStation powerStation13;
    private final Factory factory1;
    private final Factory factory2;
    private final Factory factory3;
    private final Factory factory4;
    private final Factory factory5;
    private final Factory factory6;
    private final Factory factory7;
    private final Factory factory8;
    private final Factory factory9;
    private final Factory factory10;
    private final Factory factory11;
    private final Factory factory12;
    private final Factory factory13;
    private final City city1;
    private final City city2;
    private final City city4;
    private final City city5;
    private final City city6;
    private final City city7;
    private final City city8;
    private final City city9;
    private final City city10;
    private final City city11;
    private final City city12;
    private final City city13;
    private final Barracks barrack1;
    private final Barracks barrack2;
    private final Barracks barrack3;
    private final Barracks barrack4;
    private final Barracks barrack5;
    private final Barracks barrack6;
    private final Barracks barrack7;
    private final Barracks barrack8;
    private final Barracks barrack9;
    private final Barracks barrack10;
    private final Barracks barrack11;
    private final Barracks barrack12;
    private final Barracks barrack13;
    private final Dam dam1;
    private final Dam dam2;
    private final Dam dam3;
    private final Dam dam4;
    private final SuperFactory fuck1;
    private final SuperFactory fuck2;
    private final SuperFactory fuck3;
    private final Hub1 level11;
    private final Hub1 level12;
    private final Hub1 level13;
    private final Hub2 level21;
    private final Hub2 level22;
    private final Hub2 level23;
    private final Hub3 level31;
    private final Hub3 level32;
    private final Hub3 level33;







    public GameScreen(final SkyShield game) throws IOException {
        GameScreen.game = game;
        mapImage = new Texture(Gdx.files.internal("photo_.jpg"));
        powerStation1 = new PowerStation(new float[]{200, 270}, 100, 1);
        powerStation2 = new PowerStation(new float[]{230, 470}, 100, 2);
        powerStation3 = new PowerStation(new float[]{400, 360}, 100, 3);
        powerStation4 = new PowerStation(new float[]{500, 270}, 100, 4);
        powerStation5 = new PowerStation(new float[]{460, 150}, 100, 5);
        powerStation6 = new PowerStation(new float[]{400, 490}, 100, 6);
        powerStation7 = new PowerStation(new float[]{630, 500}, 100, 7);
        powerStation8 = new PowerStation(new float[]{800, 330}, 100, 8);
        powerStation9 = new PowerStation(new float[]{650, 620}, 100, 9);
        powerStation10 = new PowerStation(new float[]{870, 530}, 100, 10);
        powerStation11 = new PowerStation(new float[]{930, 340}, 100, 11);
        powerStation12 = new PowerStation(new float[]{1050, 260}, 100, 12);
        powerStation13 = new PowerStation(new float[]{700, 160}, 100, 13);
        factory1 = new Factory(new float[]{150,250}, powerStation1,200,1);
        factory2 = new Factory(new float[]{260, 430}, powerStation2,200,2);
        factory3 = new Factory(new float[]{410, 400}, powerStation3,200,3);
        factory4 = new Factory(new float[]{400, 300}, powerStation4,200,4);
        factory5 = new Factory(new float[]{510, 190}, powerStation5,200,5);
        factory6 = new Factory(new float[]{450, 530}, powerStation6,200,6);
        factory7 = new Factory(new float[]{480, 420}, powerStation7,200,7);
        factory8 = new Factory(new float[]{855, 305}, powerStation8,200,8);
        factory9 = new Factory(new float[]{610, 620}, powerStation9,200,9);
        factory10 = new Factory(new float[]{860, 450}, powerStation10,200,10);
        factory11 = new Factory(new float[]{1070, 360}, powerStation11,200,11);
        factory12 = new Factory(new float[]{1080, 310}, powerStation12,200,12);
        factory13 = new Factory(new float[]{660, 160}, powerStation13,200,13);
        city1 = new City(new float[]{240,290},powerStation1,10,10,1000);
        city2 = new City(new float[]{280,550},powerStation1,10,10,1000);
        city4 = new City(new float[]{450,250},powerStation1,10,10,1000);
        city5 = new City(new float[]{420,120},powerStation1,10,10,1000);
        city6 = new City(new float[]{510,540},powerStation1,10,10,1000);
        city7 = new City(new float[]{570,450},powerStation1,10,10,1000);
        city8 = new City(new float[]{740,300},powerStation1,10,10,1000);
        city9 = new City(new float[]{720,620},powerStation1,10,10,1000);
        city10 = new City(new float[]{830,500},powerStation1,10,10,1000);
        city11 = new City(new float[]{1000,350},powerStation1,10,10,1000);
        city12 = new City(new float[]{1010, 220},powerStation1,10,10,1000);
        city13 = new City(new float[]{740,160},powerStation1,10,10,1000);
        barrack1 = new Barracks(new float[]{200, 360}, city1, powerStation1,150,1000);
        barrack2 = new Barracks(new float[]{330, 490}, city1, powerStation1,150,1000);
        barrack3 = new Barracks(new float[]{310, 360}, city1, powerStation1,150,1000);
        barrack4 = new Barracks(new float[]{570, 280}, city1, powerStation1,150,1000);
        barrack5 = new Barracks(new float[]{395, 230}, city1, powerStation1,150,1000);
        barrack6 = new Barracks(new float[]{570,540}, city1, powerStation1,150,1000);
        barrack7 = new Barracks(new float[]{520,460}, city1, powerStation1,150,1000);
        barrack8 = new Barracks(new float[]{730,440}, city1, powerStation1,150,1000);
        barrack9 = new Barracks(new float[]{760,590}, city1, powerStation1,150,1000);
        barrack10 = new Barracks(new float[]{770,500}, city1, powerStation1,150,1000);
        barrack11 = new Barracks(new float[]{930,400}, city1, powerStation1,150,1000);
        barrack12= new Barracks(new float[]{1085, 200}, city1, powerStation1,150,1000);
        barrack13 = new Barracks(new float[]{655, 185}, city1, powerStation1,150,1000);
        dam1 = new Dam(new float[]{455, 185},15000);
        dam2 = new Dam(new float[]{610, 450},15000);
        dam3 = new Dam(new float[]{500, 310},15000);
        dam4 = new Dam(new float[]{290, 400},15000);
        fuck1 = new SuperFactory(new float[]{50,580});
        fuck2 = new SuperFactory(new float[]{60,370});
        fuck3 = new SuperFactory(new float[]{200,120});
        level11 = new Hub1(new float[]{50,580},fuck1,powerStation2,150,10);
        level12 = new Hub1(new float[]{150,480},fuck2,powerStation1,150,10);
        level13 = new Hub1(new float[]{250,380},fuck3,powerStation5,150,10);
        level21 = new Hub2(new float[]{350,280},level11,powerStation6,150,50);
        level22 = new Hub2(new float[]{450,180},level12,powerStation3,150,50);
        level23 = new Hub2(new float[]{550,80},level13,powerStation4,150,50);
        level31 = new Hub3(new float[]{550,80},level21,powerStation9,150,150);
        level32 = new Hub3(new float[]{550,80},level22,powerStation7,150,150);
        level33 = new Hub3(new float[]{550,80},level23,powerStation8,150,150);
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
//        stage.draw();

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

        if(GUIComponents.movingButton != null) GUIComponents.moveButton();

        game.batch.begin();
        game.batch.draw(powerStation1.getTexture(), powerStation1.getPos()[0], powerStation1.getPos()[1],20,20);
        game.batch.draw(powerStation2.getTexture(), powerStation2.getPos()[0], powerStation2.getPos()[1],20,20);
        game.batch.draw(powerStation3.getTexture(), powerStation3.getPos()[0], powerStation3.getPos()[1],20,20);
        game.batch.draw(powerStation4.getTexture(), powerStation4.getPos()[0], powerStation4.getPos()[1],20,20);
        game.batch.draw(powerStation5.getTexture(), powerStation5.getPos()[0], powerStation5.getPos()[1],20,20);
        game.batch.draw(powerStation6.getTexture(), powerStation6.getPos()[0], powerStation6.getPos()[1],20,20);
        game.batch.draw(powerStation7.getTexture(), powerStation7.getPos()[0], powerStation7.getPos()[1],20,20);
        game.batch.draw(powerStation8.getTexture(), powerStation8.getPos()[0], powerStation8.getPos()[1],20,20);
        game.batch.draw(powerStation9.getTexture(), powerStation9.getPos()[0], powerStation9.getPos()[1],20,20);
        game.batch.draw(powerStation10.getTexture(), powerStation10.getPos()[0], powerStation10.getPos()[1],20,20);
        game.batch.draw(powerStation11.getTexture(), powerStation11.getPos()[0], powerStation11.getPos()[1],20,20);
        game.batch.draw(powerStation12.getTexture(), powerStation12.getPos()[0], powerStation12.getPos()[1],20,20);
        game.batch.draw(powerStation13.getTexture(), powerStation13.getPos()[0], powerStation13.getPos()[1],20,20);
        game.batch.draw(factory1.getTexture(),factory1.getPos()[0], factory1.getPos()[1],20,20);
        game.batch.draw(factory2.getTexture(),factory2.getPos()[0], factory2.getPos()[1],20,20);
        game.batch.draw(factory3.getTexture(),factory3.getPos()[0], factory3.getPos()[1],20,20);
        game.batch.draw(factory4.getTexture(),factory4.getPos()[0], factory4.getPos()[1],20,20);
        game.batch.draw(factory5.getTexture(),factory5.getPos()[0], factory5.getPos()[1],20,20);
        game.batch.draw(factory6.getTexture(),factory6.getPos()[0], factory6.getPos()[1],20,20);
        game.batch.draw(factory7.getTexture(),factory7.getPos()[0], factory7.getPos()[1],20,20);
        game.batch.draw(factory8.getTexture(),factory8.getPos()[0], factory8.getPos()[1],20,20);
        game.batch.draw(factory9.getTexture(),factory9.getPos()[0], factory9.getPos()[1],20,20);
        game.batch.draw(factory10.getTexture(),factory10.getPos()[0], factory10.getPos()[1],20,20);
        game.batch.draw(factory11.getTexture(),factory11.getPos()[0], factory11.getPos()[1],20,20);
        game.batch.draw(factory12.getTexture(),factory12.getPos()[0], factory12.getPos()[1],20,20);
        game.batch.draw(factory13.getTexture(),factory13.getPos()[0], factory13.getPos()[1],20,20);
        factory1.update(1);
        factory2.update(1);
        factory3.update(1);
        factory4.update(1);
        factory5.update(1);
        factory6.update(1);
        factory7.update(1);
        factory8.update(1);
        factory9.update(1);
        factory10.update(1);
        factory11.update(1);
        factory12.update(1);
        factory13.update(1);


        game.batch.draw(city1.getTexture(),city1.getPos()[0],city1.getPos()[1],20,20);
        game.batch.draw(city2.getTexture(),city2.getPos()[0],city2.getPos()[1],20,20);
        game.batch.draw(city4.getTexture(),city4.getPos()[0],city4.getPos()[1],20,20);
        game.batch.draw(city5.getTexture(),city5.getPos()[0],city5.getPos()[1],20,20);
        game.batch.draw(city6.getTexture(),city6.getPos()[0],city6.getPos()[1],20,20);
        game.batch.draw(city7.getTexture(),city7.getPos()[0],city7.getPos()[1],20,20);
        game.batch.draw(city8.getTexture(),city8.getPos()[0],city8.getPos()[1],20,20);
        game.batch.draw(city9.getTexture(),city9.getPos()[0],city9.getPos()[1],20,20);
        game.batch.draw(city10.getTexture(),city10.getPos()[0],city10.getPos()[1],20,20);
        game.batch.draw(city11.getTexture(),city11.getPos()[0],city11.getPos()[1],20,20);
        game.batch.draw(city12.getTexture(),city12.getPos()[0],city12.getPos()[1],20,20);
        game.batch.draw(city13.getTexture(),city13.getPos()[0],city13.getPos()[1],20,20);
        city1.update(1);
        city2.update(1);
        city4.update(1);
        city5.update(1);
        city6.update(1);
        city7.update(1);
        city8.update(1);
        city9.update(1);
        city10.update(1);
        city11.update(1);
        city12.update(1);
        city13.update(1);

        game.batch.draw(barrack1.getTexture(),barrack1.getPos()[0],barrack1.getPos()[1],20,20);
        game.batch.draw(barrack2.getTexture(),barrack2.getPos()[0],barrack2.getPos()[1],20,20);
        game.batch.draw(barrack3.getTexture(),barrack3.getPos()[0],barrack3.getPos()[1],20,20);
        game.batch.draw(barrack4.getTexture(),barrack4.getPos()[0],barrack4.getPos()[1],20,20);
        game.batch.draw(barrack5.getTexture(),barrack5.getPos()[0],barrack5.getPos()[1],20,20);
        game.batch.draw(barrack6.getTexture(),barrack6.getPos()[0],barrack6.getPos()[1],20,20);
        game.batch.draw(barrack7.getTexture(),barrack7.getPos()[0],barrack7.getPos()[1],20,20);
        game.batch.draw(barrack8.getTexture(),barrack8.getPos()[0],barrack8.getPos()[1],20,20);
        game.batch.draw(barrack9.getTexture(),barrack9.getPos()[0],barrack9.getPos()[1],20,20);
        game.batch.draw(barrack10.getTexture(),barrack10.getPos()[0],barrack10.getPos()[1],20,20);
        game.batch.draw(barrack11.getTexture(),barrack11.getPos()[0],barrack11.getPos()[1],20,20);
        game.batch.draw(barrack12.getTexture(),barrack12.getPos()[0],barrack12.getPos()[1],20,20);
        game.batch.draw(barrack13.getTexture(),barrack13.getPos()[0],barrack13.getPos()[1],20,20);

        barrack1.update(1);
        barrack2.update(1);
        barrack3.update(1);
        barrack4.update(1);
        barrack5.update(1);
        barrack6.update(1);
        barrack7.update(1);
        barrack8.update(1);
        barrack9.update(1);
        barrack10.update(1);
        barrack11.update(1);
        barrack12.update(1);
        barrack13.update(1);
        game.batch.draw(dam1.getTexture(),dam1.getPos()[0],dam1.getPos()[1],20,20);
        game.batch.draw(dam2.getTexture(),dam2.getPos()[0],dam2.getPos()[1],20,20);
        game.batch.draw(dam3.getTexture(),dam3.getPos()[0],dam3.getPos()[1],20,20);
        game.batch.draw(dam4.getTexture(),dam4.getPos()[0],dam4.getPos()[1],20,20);
        game.font.draw(game.batch, "Total money count: " + City.getTotalMoney(), 1000, 100);
        game.font.draw(game.batch, "civil: " + City.totalPopulation, 1000, 50);
        game.font.draw(game.batch, "Total people count: " + Barracks.getTotalTrainedSoldiers(), 1000, 200);
        game.font.draw(game.batch, "Total tanks count: " + Factory.getRocketCount(), 1000, 150);
        game.batch.draw(fuck1.getTexture(),fuck1.getPos()[0],fuck1.getPos()[1],40,40);
        game.batch.draw(fuck2.getTexture(),fuck2.getPos()[0],fuck2.getPos()[1],40,40);
        game.batch.draw(fuck3.getTexture(),fuck3.getPos()[0],fuck3.getPos()[1],40,40);
        fuck1.update(1);
        fuck3.update(1);
        fuck2.update(1);
        game.batch.draw(level11.getTexture(),level11.getPos()[0],level11.getPos()[1],20,20);
        game.batch.draw(level12.getTexture(),level12.getPos()[0],level12.getPos()[1],20,20);
        game.batch.draw(level13.getTexture(),level13.getPos()[0],level13.getPos()[1],20,20);
        game.batch.draw(level21.getTexture(),level21.getPos()[0],level21.getPos()[1],20,20);
        game.batch.draw(level22.getTexture(),level22.getPos()[0],level22.getPos()[1],20,20);
        game.batch.draw(level23.getTexture(),level23.getPos()[0],level23.getPos()[1],20,20);
        game.batch.draw(level31.getTexture(),level31.getPos()[0],level31.getPos()[1],20,20);
        game.batch.draw(level32.getTexture(),level32.getPos()[0],level32.getPos()[1],20,20);
        game.batch.draw(level33.getTexture(),level33.getPos()[0],level33.getPos()[1],20,20);
        level11.update(1);
        level12.update(1);
        level13.update(1);
        level21.update(1);
        level22.update(1);
        level23.update(1);
        level31.update(1);
        level32.update(1);
        level33.update(1);
        game.font.draw(game.batch, "factory1: " + fuck1.weaponsProduced, 100, 200);
        game.font.draw(game.batch, "factory2: " + fuck2.weaponsProduced, 100, 150);
        game.font.draw(game.batch, "factory3: " + fuck3.weaponsProduced, 100, 100);
        game.font.draw(game.batch, "level11: " + level11.weapons, 300, 200);
        game.font.draw(game.batch, "level12: " + level12.weapons, 300, 150);
        game.font.draw(game.batch, "level13: " + level13.weapons, 300, 100);
        game.font.draw(game.batch, "level21: " + level21.weapons, 500, 200);
        game.font.draw(game.batch, "level22: " + level22.weapons, 500, 150);
        game.font.draw(game.batch, "level23: " + level23.weapons, 500, 100);
        game.font.draw(game.batch, "level31: " + level31.weapons, 700, 200);
        game.font.draw(game.batch, "level32: " + level32.weapons, 700, 150);
        game.font.draw(game.batch, "level33: " + level33.weapons, 700, 100);
        game.batch.end();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
        Table guiTable = new Table();
        guiTable.setBounds(0, camera.viewportHeight-camera.viewportHeight/8, camera.viewportWidth/2, camera.viewportHeight/8);
        guiTable.setDebug(true);
        stage.addActor(guiTable);
        Skin skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
        TextButton zoomInButton = new TextButton("+", skin);
        TextButton zoomOutButton = new TextButton("-", skin);
        TextButton shopButton = new TextButton("Shop", skin);
        guiTable.add(shopButton).left().top().expand();
        guiTable.add(zoomInButton).left().top().expand();
        guiTable.add(zoomOutButton).left().top().expand();
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
        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if(shopButton.isChecked()) {
                    GUIComponents.addShop();
                }
                else {
                    GUIComponents.removeShop();
                }
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

        if (cameraPos.x + (lastClickX - inputX) > getMinCameraX() && cameraPos.x + (lastClickX - inputX) < getMaxCameraX()) {
            cameraPos.x += lastClickX - inputX;
        }

        if (cameraPos.y - (lastClickY - inputY) > getMinCameraY() && cameraPos.y - (lastClickY - inputY) < getMaxCameraY()) {
            cameraPos.y -= lastClickY - inputY;
        }

        camera.position.lerp(cameraPos, 1);

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

