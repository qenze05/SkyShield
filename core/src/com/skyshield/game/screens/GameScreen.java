package com.skyshield.game.screens;

import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
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
import com.skyshield.game.airDefence.AirDef;
import com.skyshield.game.airDefence.AirDefRocket;
import com.skyshield.game.airDefence.F500;
import com.skyshield.game.rockets.FastRocket;
import com.skyshield.game.rockets.Rocket;
import com.skyshield.game.rockets.SimpleRocket;
import com.skyshield.game.utils.AirDefLogic;
import com.skyshield.game.utils.MapPolygon;
import com.skyshield.game.utils.RocketMovement;

public class GameScreen implements Screen {
    private final SkyShield game;
    public static final float globalScale = 700 / 693f;
    private final Texture mapImage;
    private final OrthographicCamera camera;
    private boolean moveCamera = false;
    private int lastClickX, lastClickY;
    private int inputX, inputY;
    private final Vector3 cameraPos = new Vector3((float) 1280 / 2, (float) 693 / 2, 0);
    private final Stage stage;
    private Polygon map;
    private Array<Rocket> rockets;
    private Array<AirDef> airDef;
    private Array<AirDefRocket> airDefRockets;

    private long attackStartTime = TimeUtils.nanoTime(), lastRocketSpawnTime;

    public GameScreen(final SkyShield game) throws IOException {
        this.game = game;
        mapImage = new Texture(Gdx.files.internal("bg-720.png"));
        addAirDef(new float[]{660, 420}, "F-500");
        addAirDef(new float[]{893, 486}, "F-500");
        addAirDef(new float[]{1000, 300}, "F-500");
        airDefRockets = new Array<>();

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
        for (AirDef airDefUnit : airDef) {
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
        if (TimeUtils.nanoTime() - attackStartTime < 30000000000f) {
            singleAttack();
        } else {
            rockets = null;
            airDefRockets = null;
            attackStartTime = TimeUtils.nanoTime();
            singleAttack();
        }

        findRocketsInRange();
        if(airDefRockets!=null) moveAirDefRockets();

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

    private void spawnRocket(String type, float[] target, float[] spawnPoint) {
        switch (type) {
            case "simple" -> rockets.add(new SimpleRocket(target, spawnPoint));
            case "damnFast" -> rockets.add(new FastRocket(target, spawnPoint));
        }
    }

    private void singleAttack() {
        if (rockets == null) {
            attackStartTime = TimeUtils.nanoTime();
            rockets = new Array<>();
            spawnRocket("simple", new float[]{563, 538}, new float[]{1200, 117});
            lastRocketSpawnTime = TimeUtils.nanoTime();
        }
        if (TimeUtils.nanoTime() - lastRocketSpawnTime > 300000000f) {
            spawnRocket("simple",
                    new float[]{420, 420},
                    new float[]{1200, 117});
            spawnRocket("damnFast",
                    new float[]{420, 420},
                    new float[]{1121, 641});
            lastRocketSpawnTime = TimeUtils.nanoTime();
        }
        launchRockets();
    }

    private void launchRockets() {
        Iterator<Rocket> iter = rockets.iterator();

        while (iter.hasNext()) {
            Rocket rocket;
            Rectangle hitbox;
            Sprite rocketSprite;

            rocket = iter.next();
            hitbox = rocket.getHitbox();

            if (rocket.getFrame() <= 40) {

                hitbox.setPosition(hitbox.x + RocketMovement.getTakeoffShiftX(rocket.getFrame(), rocket.getAngle(), rocket.getSpeed()),
                        hitbox.y + RocketMovement.getTakeoffShiftY(rocket.getFrame(), rocket.getAngle(), rocket.getSpeed()));

                rocket.setFrame(rocket.getFrame() + 1);

            } else {
                rotateRocket(rocket, hitbox);
                hitbox.setPosition(hitbox.x + RocketMovement.getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                        hitbox.y + RocketMovement.getMaxSpeedShiftY(rocket.getSpeed(), rocket.getAngle()));
            }

            rocketSprite = new Sprite(rocket.getTexture());
            rocketSprite.setPosition(rocket.getHitbox().x, rocket.getHitbox().y);
            rocketSprite.rotate(rocket.getAngle() * (-1));

            if (RocketMovement.targetReached(hitbox, rocket.getTarget())) {
                rocket.setEliminated(true);
                iter.remove();
            }
            game.batch.begin();
            rocketSprite.draw(game.batch);
            game.batch.end();
        }
    }

    private void rotateRocket(Rocket rocket, Rectangle hitbox) {
        rocket.setAngle(RocketMovement.rotateRocket(
                new float[]{hitbox.x + hitbox.getWidth() / 2, hitbox.y + hitbox.getHeight() / 2},
                rocket.getTarget(),
                rocket.getAngle(), 3));
        if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
        else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);
    }

    private void findRocketsInRange() {
        Iterator<AirDef> airDefIter = airDef.iterator();
        Iterator<Rocket> rocketsIter;
        while (airDefIter.hasNext()) {
            AirDef airDefUnit = airDefIter.next();
            rocketsIter = rockets.iterator();
            while (rocketsIter.hasNext()) {
                Rocket rocket = rocketsIter.next();
                if (airDefUnit.getCircleHitbox().contains(rocket.getHitbox())
                        && !rocket.isTargeted()
                        && (TimeUtils.nanoTime() - airDefUnit.getLastLaunchTime()) > 60000000000f / airDefUnit.getLaunchesPerMin()) {
                    airDefUnit.setLastLaunchTime(TimeUtils.nanoTime());
                    launchAirDef(rocket, airDefUnit);
                    rocket.setTargetedState(true);
                }else if ((TimeUtils.nanoTime() - airDefUnit.getLastLaunchTime()) > 60000000000f / airDefUnit.getLaunchesPerMin()
                && airDefUnit.getCircleHitbox().contains(rocket.getHitbox())){
                    airDefUnit.setLastLaunchTime(TimeUtils.nanoTime());
                    launchAirDef(rocket, airDefUnit);
                    rocket.setTargetedState(true);
                }
            }
        }
    }

    private void launchAirDef(Rocket rocket, AirDef airDefUnit) {
        if (airDefRockets == null) airDefRockets = new Array<>();
        airDefRockets.add(new AirDefRocket(airDefUnit.getPos(), rocket, airDefUnit));
    }

    private void moveAirDefRockets() {
        game.batch.begin();
        Iterator<AirDefRocket> iter = airDefRockets.iterator();
        AirDefRocket rocket;
        while (iter.hasNext()) {
            rocket = iter.next();
            AirDefLogic.moveRocket(rocket);
            if (rocket.getHitbox().overlaps(rocket.getTarget().getHitbox())) {
                removeRocket(rocket.getTarget().getHitbox());
                iter.remove();
            } else if (!rocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) {
                iter.remove();
            } else if(rocket.getTarget().isEliminated()
                    || rocket.getTarget() == null
                    || !rocket.getOrigin().getCircleHitbox().contains(rocket.getTarget().getHitbox())) {
                findNewTarget(rocket);
            }
            if(TimeUtils.nanoTime() - rocket.timeCreated > 2000000000f) System.out.println(rocket.getTarget().getHitbox());
            game.batch.draw(rocket.getTexture(), rocket.getHitbox().x, rocket.getHitbox().y);
        }
        game.batch.end();
    }

    private void findNewTarget(AirDefRocket airDefRocket) {
        for(Rocket rocket : rockets) {
            if(!rocket.isEliminated()
                    && !rocket.isTargeted()
                    && airDefRocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) {
                airDefRocket.setTarget(rocket);
            }else if(!rocket.isEliminated()
                    && airDefRocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) {
                airDefRocket.setTarget(rocket);
            }
        }
    }
    private void removeRocket(Rectangle hitbox) {
        Iterator<Rocket> iter = rockets.iterator();
        Rocket rocket;
        while (iter.hasNext()) {
            rocket = iter.next();
            if (rocket.getHitbox().overlaps(hitbox)) {
                rocket.setEliminated(true);
                iter.remove();
                break;
            }
        }
    }

    private void addAirDef(float[] pos, String type) {
        if (airDef == null) airDef = new Array<>();
        switch (type) {
            case "F-500" -> airDef.add(new F500(pos));
            case "SD-250-M" -> airDef.add(new F500(pos));
        }
    }

}

