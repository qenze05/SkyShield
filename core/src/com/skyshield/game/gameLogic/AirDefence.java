package com.skyshield.game.gameLogic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.airDefence.AirDef;
import com.skyshield.game.airDefence.AirDefRocket;
import com.skyshield.game.airDefence.F500;
import com.skyshield.game.rockets.Rocket;
import com.skyshield.game.screens.GameScreen;

import java.util.Iterator;
import java.util.TreeMap;

public class AirDefence {

    public static Array<AirDef> airDef;
    public static Array<AirDefRocket> airDefRockets;

    public static void addAirDef(float[] pos, String type) {
        if (airDef == null) AirDefence.airDef = new Array<>();
        switch (type) {
            case "F-500" -> airDef.add(new F500(pos));
            case "SD-250-M" -> airDef.add(new F500(pos));
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

            } else if (!rocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) {
                iter.remove();
            }

            GameScreen.game.batch.draw(rocket.getTexture(), rocket.getHitbox().x, rocket.getHitbox().y);
        }

        GameScreen.game.batch.end();
    }

    private static void moveRocket(AirDefRocket rocket) {

        Rectangle hitbox = rocket.getHitbox();

        if(rocket.getTarget() != null) {
            rocket.setAngle(Rockets.rotateRocket(
                    new float[]{hitbox.x + hitbox.getWidth() / 2, hitbox.y + hitbox.getHeight() / 2},
                    new float[]{rocket.getTarget().getHitbox().x, rocket.getTarget().getHitbox().y},
                    rocket.getAngle(), 10));

            if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
            else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);
        }

        hitbox.setPosition(hitbox.x + Rockets.getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                hitbox.y + Rockets.getMaxSpeedShiftY(rocket.getSpeed(), rocket.getAngle()));
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
