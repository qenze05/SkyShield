package com.skyshield.game.gameLogic.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameObjects.rockets.FastRocket;
import com.skyshield.game.gameObjects.rockets.Rocket;
import com.skyshield.game.gameObjects.rockets.SimpleRocket;
import com.skyshield.game.screens.GameScreen;

import java.util.Iterator;

public class Rockets {

    public static Array<Rocket> rockets;

    public static void spawnRocket(String type, float[] target, float[] spawnPoint) {
        switch (type) {
            case "SIMPLE" -> rockets.add(new SimpleRocket(target, spawnPoint));
            case "FAST" -> rockets.add(new FastRocket(target, spawnPoint));
        }
    }

    public static void launchRockets() {

        Iterator<Rocket> iter = rockets.iterator();

        while (iter.hasNext()) {

            Rocket rocket;
            Rectangle hitbox;
            Sprite rocketSprite;

            rocket = iter.next();
            hitbox = rocket.getHitbox();

            if (rocket.getFrame() <= 40) {

                hitbox.setPosition(hitbox.x + getTakeoffShiftX(rocket.getFrame(), rocket.getAngle(), rocket.getSpeed()),
                        hitbox.y + getTakeoffShiftY(rocket.getFrame(), rocket.getAngle(), rocket.getSpeed()));

                rocket.setFrame(rocket.getFrame() + 1);

            } else {

                rotateRocket(rocket, hitbox);
                hitbox.setPosition(hitbox.x + getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                        hitbox.y + getMaxSpeedShiftY(rocket.getSpeed(), rocket.getAngle()));
            }

            rocketSprite = new Sprite(rocket.getTexture());
            rocketSprite.setPosition(rocket.getHitbox().x, rocket.getHitbox().y);
            rocketSprite.rotate(rocket.getAngle() * (-1));

            if (targetReached(hitbox, rocket.getTarget())) {
                rocket.setEliminated(true);
                iter.remove();
            }
            GameScreen.game.batch.begin();
            rocketSprite.draw(GameScreen.game.batch);
            GameScreen.game.batch.end();
        }
    }

    private static void rotateRocket(Rocket rocket, Rectangle hitbox) {

        rocket.setAngle(rotateRocket(
                new float[]{hitbox.x + hitbox.getWidth() / 2, hitbox.y + hitbox.getHeight() / 2},
                rocket.getTarget(),
                rocket.getAngle(), 3));

        if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
        else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);
    }

    public static float getTakeoffShiftX(int frame, int angle, float maxSpeed) {
        return (float) (Math.sin(Math.toRadians(angle))*(maxSpeed*frame/40)*GameScreen.globalScale/360);
    }

    public static float getTakeoffShiftY(int frame, int angle, float maxSpeed) {
        return (float) (Math.cos(Math.toRadians(angle))*(maxSpeed*frame/40)*GameScreen.globalScale/360);
    }

    public static int getTriangleDegree(float[] current, float[] target) {
        float distance = getDistance(current, target);
        float cathetus = getDistance(current, new float[]{target[0], current[1]});

        return (int) Math.toDegrees(Math.acos(cathetus / distance));
    }

    public static float getDistance(float[] current, float[] target) {
        return (float) (Math.sqrt(Math.pow((target[0] - current[0]), 2)
                + Math.pow((target[1] - current[1]), 2)) * GameScreen.globalScale);
    }

    public static float getMaxSpeedShiftX(float maxSpeed, int angle) {
        return (float) (Math.sin(Math.toRadians(angle))*maxSpeed*GameScreen.globalScale/360);
    }

    public static float getMaxSpeedShiftY(float maxSpeed, int angle) {
        return (float) (Math.cos(Math.toRadians(angle))*maxSpeed*GameScreen.globalScale/360);
    }

    public static int rotateRocket(float[] current, float[] target, int angle, int shift) {

        int triangleDegree = getTriangleDegree(current, target);

        if (target[0] <= current[0]) {

            if (target[1] < current[1]) {
                if (Math.abs(angle - 270 - triangleDegree) <= 3) return 270 - triangleDegree;
                return (angle < (270 - triangleDegree - 180) || angle > 270 - triangleDegree) ? angle - shift : angle + shift;

            } else if (target[1] > current[1]) {
                if (Math.abs(angle - 270 + triangleDegree) <= 3) return 270 + triangleDegree;
                return (angle < (270 + triangleDegree - 180) || angle > 270 + triangleDegree) ? angle - shift : angle + shift;
            }

        } else {

            if (target[1] < current[1]) {
                if (Math.abs(angle - 90 + triangleDegree) <= 3) return 90 + triangleDegree;
                return (angle < (90 + triangleDegree + 180) && angle > 90 + triangleDegree) ? angle - shift : angle + shift;

            } else if (target[1] > current[1]) {
                if (Math.abs(angle - 90 - triangleDegree) <= 3) return 90 - triangleDegree;
                return (angle < (90 - triangleDegree + 180) && angle > 90 - triangleDegree) ? angle - shift : angle + shift;
            }
        }

        return angle-3;
    }

    public static boolean targetReached(Rectangle current, float[] target) {
        return (Math.abs(current.x-target[0]) <= 20 && Math.abs(current.y-target[1]) <= 20);
    }

}
