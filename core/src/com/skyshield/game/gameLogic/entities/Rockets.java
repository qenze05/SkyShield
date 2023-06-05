package com.skyshield.game.gameLogic.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameObjects.rockets.*;
import com.skyshield.game.screens.GameScreen;

import java.util.Iterator;

public class Rockets {

    public static Array<Rocket> rockets;

    public static void spawnRocket(String type, String target, float[] spawnPoint) {
        switch (type.toLowerCase()) {
            case "simple" -> rockets.add(new SimpleRocket(target, spawnPoint));
            case "fast" -> rockets.add(new FastRocket(target, spawnPoint));
            case "elektra" -> rockets.add(new Elektra(target, spawnPoint));
            case "harpun" -> rockets.add(new Harpun(target, spawnPoint));
            case "korshun" -> rockets.add(new Korshun(target, spawnPoint));
            case "mukha" -> rockets.add(new Mukha(target, spawnPoint));
            case "r1" -> rockets.add(new R1(target, spawnPoint));
            case "r2" -> rockets.add(new R2(target, spawnPoint));
            case "r3" -> rockets.add(new R3(target, spawnPoint));
            case "sapsan" -> rockets.add(new Sapsan(target, spawnPoint));
            case "snovyda" -> rockets.add(new Snovyda(target, spawnPoint));
            case "troyanskyykin" -> rockets.add(new TroyanskyyKin(target, spawnPoint));
            case "kobra" -> rockets.add(new Kobra(target, spawnPoint));

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

            rotateRocket(rocket, hitbox);

            if (rocket.getFrame() <= 40 / GameScreen.gameSpeed) {

                hitbox.setPosition(hitbox.x + getTakeoffShiftX(rocket.getFrame(), rocket.getAngle(), rocket.getSpeed()),
                        hitbox.y + getTakeoffShiftY(rocket.getFrame(), rocket.getAngle(), rocket.getSpeed()));

                rocket.setFrame(rocket.getFrame() + 1);

            } else {


                hitbox.setPosition(hitbox.x + getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                        hitbox.y + getMaxSpeedShiftY(rocket.getSpeed(), rocket.getAngle()));
            }

            rocketSprite = new Sprite(rocket.getTexture());
            rocketSprite.setBounds(rocket.getHitbox().x, rocket.getHitbox().y, rocket.getHitbox().width, rocket.getHitbox().height);
            rocketSprite.rotate(rocket.getAngle() * (-1));

            if (targetReached(hitbox, rocket.getTargetHitbox())) {
                rocket.setEliminated(true);
                iter.remove();
            }
            GameScreen.game.batch.begin();
            rocketSprite.draw(GameScreen.game.batch);
            GameScreen.game.batch.end();
        }
    }

    private static void rotateRocket(Rocket rocket, Rectangle hitbox) {

        float shift = rocket.getSpeed()/1200;
        if(shift<1) shift = 1;

        rocket.setAngle(rotateRocket(
                new float[]{hitbox.x + hitbox.getWidth() / 2, hitbox.y + hitbox.getHeight() / 2},
                rocket.getTargetPos(),
                rocket.getAngle(), shift));

        if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
        else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);
    }

    public static float getTakeoffShiftX(int frame, float angle, float maxSpeed) {
        return (float) (Math.sin(Math.toRadians(angle))
                * (maxSpeed * frame / (40 / GameScreen.gameSpeed))
                * GameScreen.globalScale
                * GameScreen.gameSpeed
                / 360);
    }

    public static float getTakeoffShiftY(int frame, float angle, float maxSpeed) {
        return (float) (Math.cos(Math.toRadians(angle))
                * (maxSpeed * frame / (40 / GameScreen.gameSpeed))
                * GameScreen.globalScale
                * GameScreen.gameSpeed
                / 360);
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

    public static float getMaxSpeedShiftX(float maxSpeed, float angle) {
        return (float) (Math.sin(Math.toRadians(angle))
                * maxSpeed
                * GameScreen.globalScale
                * GameScreen.gameSpeed
                / 360);
    }

    public static float getMaxSpeedShiftY(float maxSpeed, float angle) {
        return (float) (Math.cos(Math.toRadians(angle))
                * maxSpeed
                * GameScreen.globalScale
                * GameScreen.gameSpeed
                / 360);
    }

    public static float rotateRocket(float[] current, float[] target, float angle, float shift) {

        shift *= GameScreen.gameSpeed;

        int triangleDegree = getTriangleDegree(current, target);

//        System.out.println("a: "+angle+"\n" +
//                "t: "+triangleDegree);

        if (target[0] <= current[0]) {

            if (target[1] < current[1]) {
                if (Math.abs(angle - 270) - triangleDegree <= shift) return 270 - triangleDegree;
                return (angle < (270 - triangleDegree - 180) || angle > 270 - triangleDegree) ? angle - shift : angle + shift;

            } else if (target[1] > current[1]) {
                if (Math.abs(angle - 270) + triangleDegree <= shift) return 270 + triangleDegree;
                return (angle < (270 + triangleDegree - 180) || angle > 270 + triangleDegree) ? angle - shift : angle + shift;
            }

        } else {

            if (target[1] < current[1]) {
                if (Math.abs(angle - 90) + triangleDegree <= shift) return 90 + triangleDegree;
                return (int) ((angle < (90 + triangleDegree + 180) && angle > 90 + triangleDegree) ? angle - shift : angle + shift);

            } else if (target[1] > current[1]) {
                if (Math.abs(angle - 90) - triangleDegree <= shift) return 90 - triangleDegree;
                return (int) ((angle < (90 - triangleDegree + 180) && angle > 90 - triangleDegree) ? angle - shift : angle + shift);
            }
        }

        return (int) (angle - shift);
    }

    public static boolean targetReached(Rectangle current, Rectangle target) {
        return target.contains(current.x+ current.width/2, current.y+ current.height/2);
    }

    public static boolean isVisible(Rocket rocket) {
        if(rocket.isTargeted()) return true;

        rocket.setTargetedState(true);
        if(rocket.isTargeted()) {
            rocket.setTargetedState(false);
            return true;
        } else {
            return false;
        }
    }

}
