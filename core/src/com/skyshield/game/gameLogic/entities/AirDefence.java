package com.skyshield.game.gameLogic.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameObjects.airDefence.AirDefRocket;
import com.skyshield.game.gameObjects.airDefence.F500;
import com.skyshield.game.gameObjects.airDefence.SD250M;
import com.skyshield.game.gameObjects.rockets.Rocket;
import com.skyshield.game.screens.GameScreen;

import java.util.Iterator;
import java.util.TreeMap;

public class AirDefence {

    public static Array<AirDef> airDef = new Array<>();
    public static Array<AirDefRocket> airDefRockets = new Array<>();

    public static void addAirDef(float[] pos, String type) {
        if (airDef == null) AirDefence.airDef = new Array<>();
        switch (type) {
            case "F-500" -> airDef.add(new F500(pos));
            case "SD-250-M" -> airDef.add(new SD250M(pos));
        }
    }

    private static void launchAirDef(Rocket rocket, AirDef airDefUnit) {
        if (airDefRockets == null) airDefRockets = new Array<>();
        airDefRockets.add(new AirDefRocket(airDefUnit.getPos(), rocket, airDefUnit));
    }

    public static void moveRockets() {

        GameScreen.game.batch.begin();

        Iterator<AirDefRocket> iter = airDefRockets.iterator();
        AirDefRocket rocket;

        while (iter.hasNext()) {

            rocket = iter.next();
            moveRocket(rocket);

            if(rocket.getTarget() == null
                    || rocket.getTarget().isEliminated()
                    || !rocket.getOrigin().getCircleHitbox().contains(rocket.getTarget().getHitbox())) {
                findNewTarget(rocket);

            } else if (rocket.getHitbox().overlaps(rocket.getTarget().getHitbox())) {
                removeTarget(rocket.getTarget().getHitbox());
                iter.remove();

            }

            if (!rocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) {
                iter.remove();
            }

            GameScreen.game.batch.draw(rocket.getTexture(), rocket.getHitbox().x, rocket.getHitbox().y);
        }

        GameScreen.game.batch.end();
    }

    private static void moveRocket(AirDefRocket rocket) {

        Rectangle hitbox = rocket.getHitbox();

        if(rocket.getTarget() != null) {

            rocket.setAngle(rotateRocket(rocket, hitbox));

            if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
            else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);
        }

        hitbox.setPosition(hitbox.x + Rockets.getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                hitbox.y + Rockets.getMaxSpeedShiftY(rocket.getSpeed(), rocket.getAngle()));
    }

    private static int rotateRocket(AirDefRocket rocket, Rectangle hitbox) {

        Rectangle targetHitbox = rocket.getTarget().getHitbox();
        float[] current = new float[]{hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2};
        float[] target = new float[]{targetHitbox.x+targetHitbox.width/2, targetHitbox.y+targetHitbox.height/2};

        if(rocket.getWasTargetChanged()) {
            if(rocket.getFrame()!=0) rocket.setFrame(0);
            else rocket.setFrame(rocket.getFrame()+1);
        }
        else if(rocket.getFrame()==37) {
            rocket.setWasTargetChanged(false);
            rocket.setFrame(rocket.getFrame()+1);
        }

        if(rocket.getFrame()<=36) {
            return Rockets.rotateRocket(current, target, rocket.getAngle(), 10);

        }else {
            int triangleDegree = Rockets.getTriangleDegree(current, target);
            if (target[0] <= current[0]) {
                if (target[1] < current[1]) return 270 - triangleDegree;
                else if (target[1] > current[1]) return 270 + triangleDegree;
            } else {
                if (target[1] < current[1]) return 90 + triangleDegree;
                else if (target[1] > current[1]) return 90 - triangleDegree;
            }
            return 0;
        }

    }

    public static void findTargetsInRange() {

        Iterator<AirDef> airDefIter = airDef.iterator();
        Iterator<Rocket> rocketsIter;

        while (airDefIter.hasNext()) {

            AirDef airDefUnit = airDefIter.next();
            rocketsIter = Rockets.rockets.iterator();

            while (rocketsIter.hasNext()) {

                Rocket rocket = rocketsIter.next();

                if (airDefUnit.getCircleHitbox().contains(rocket.getHitbox())
                        && !isTargetedByThisAirDef(rocket, airDefUnit)
                        && (TimeUtils.nanoTime() - airDefUnit.getLastLaunchTime())
                        > 60000000000f / airDefUnit.getLaunchesPerMin()) {

                    airDefUnit.setLastLaunchTime(TimeUtils.nanoTime());
                    launchAirDef(rocket, airDefUnit);
                    rocket.setTargetedState(true);
                }
            }
        }
    }

    private static boolean isTargetedByThisAirDef(Rocket rocket, AirDef airDef) {

        if(airDefRockets == null) return false;

        for(AirDefRocket airDefRocket : airDefRockets) {
            if(airDefRocket.getTarget() == null) continue;
            if(airDefRocket.getTarget().equals(rocket) && airDefRocket.getOrigin().equals(airDef)) return true;
        }

        return false;
    }

    private static void findNewTarget(AirDefRocket airDefRocket) {
        airDefRocket.setTarget(findClosestTarget(airDefRocket, true));
    }

    private static Rocket findClosestTarget(AirDefRocket airDefRocket, boolean skipTargeted) {

        TreeMap<Float, Rocket> rocketsMap = new TreeMap<>();

        float[] airDefPos = new float[]{airDefRocket.getHitbox().x, airDefRocket.getHitbox().y};
        float[] targetPos = new float[2];

        for (Rocket rocket : Rockets.rockets) {

            if(rocket.isEliminated()
                    || rocket.isTargeted() == skipTargeted
                    || !airDefRocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) continue;

            targetPos[0] = rocket.getHitbox().x;
            targetPos[1] = rocket.getHitbox().y;
            rocketsMap.put((Rockets.getDistance(airDefPos, targetPos)), rocket);
        }

        if(rocketsMap.size()==0 && skipTargeted) return findClosestTarget(airDefRocket, false);
        else if (rocketsMap.size()==0) return null;
        else return rocketsMap.get(rocketsMap.firstKey());
    }

    private static void removeTarget(Rectangle hitbox) {
        Iterator<Rocket> iter = Rockets.rockets.iterator();
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
}
