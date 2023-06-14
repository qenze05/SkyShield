package com.skyshield.game.gameLogic.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameObjects.airDefence.*;
import com.skyshield.game.gameObjects.rockets.Rocket;
import com.skyshield.game.gameObjects.rockets.SimpleRocket;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.sound.Sounds;

import java.util.Iterator;
import java.util.TreeMap;

public class AirDefence {

    public static Array<AirDef> airDefs = new Array<>();
    public static Array<AirDefRocket> airDefRockets = new Array<>();
    public static Array<Rocket> cornerTargets = new Array<>();
    public static boolean snovydaLost = false;
    public static Sprite airDefRocketSprite;

    public static void addAirDef(float[] pos, String type) {
        if (airDefs == null) AirDefence.airDefs = new Array<>();
        switch (type.toLowerCase()) {
            case "krona-mk1" -> airDefs.add(new KronaMK1(pos));
            case "krona-mk2" -> airDefs.add(new KronaMK2(pos));
            case "krona-mk3" -> airDefs.add(new KronaMK3(pos));
            case "slon" -> airDefs.add(new Slon(pos));
            case "skorpion" -> airDefs.add(new Skorpion(pos));
            case "mukhobiyka" -> airDefs.add(new Mukhobiyka(pos));
            case "pulsar" -> airDefs.add(new Pulsar(pos));
            case "mushlya" -> airDefs.add(new Mushlya(pos));
            case "krona-s" -> airDefs.add(new KronaS(pos));
            case "lut" -> airDefs.add(new Lut(pos));
            case "slon-s" -> airDefs.add(new SlonS(pos));
            case "skorpion-s" -> airDefs.add(new SkorpionS(pos));
            case "pulsar-s" -> airDefs.add(new PulsarS(pos));
            case "armahedon" -> airDefs.add(new Armahedon(pos));
            case "okohora1" -> airDefs.add(new OkoHora1(pos));
            case "okohora2" -> airDefs.add(new OkoHora2(pos));
            case "okohora3" -> airDefs.add(new OkoHora3(pos));
        }
    }
    private static void launchAirDef(Rocket rocket, AirDef airDefUnit) {
        if (airDefRockets == null) airDefRockets = new Array<>();
        airDefRockets.add(new AirDefRocket(airDefUnit.getPos(), rocket, airDefUnit));
        Sounds.addSound("airdef_start");
    }

    public static void moveRockets() {

        GameScreen.game.batch.begin();

        if(snovydaLost) hideSnovyda();
        Iterator<AirDefRocket> iter = airDefRockets.iterator();
        AirDefRocket rocket;


        while (iter.hasNext()) {

            rocket = iter.next();
            moveRocket(rocket);

            if(rocket.getTarget() == null || rocket.getTarget().isEliminated()) {
                findNewTarget(rocket);
            } else if (rocket.getHitbox().overlaps(rocket.getTarget().getHitbox())) {

                if(rocket.getTarget().getName().equalsIgnoreCase("SimpleRocket")) { //simple rocket ability
                    removeTarget(rocket.getTarget().getHitbox(), rocket.getOrigin());
                    GameScreen.disposableTextures.add(rocket.getTexture());
                    iter.remove();
                    continue;
                }else if(rocket.getTarget().getName().equalsIgnoreCase("ImmortalRocket")){ //immortal rocket ability
                    setCornerTarget(rocket);
                }else if (rocket.getOrigin().getName().contains("OkoHora")) {
                    continue;
                }else{
                    if(miss(rocket)) {
                        setCornerTarget(rocket);
                    }else{
                        removeTarget(rocket.getTarget().getHitbox(), rocket.getOrigin());
                        GameScreen.disposableTextures.add(rocket.getTexture());
                        iter.remove();
                        continue;
                    }
                }
            } else if (rocket.getOrigin().getName().contains("OkoHora")
                    && !rocket.getOrigin().getCircleHitbox().overlaps(rocket.getTarget().getHitbox())) {
                snovydaLost = true;
                rocket.getTarget().setTargetedState(false);
                GameScreen.disposableTextures.add(rocket.getTexture());
                iter.remove();
            }

            if (!rocket.getOrigin().getCircleHitbox().contains(rocket.getHitbox())) {
                GameScreen.disposableTextures.add(rocket.getTexture());
                iter.remove();
            }

            if (airDefRocketSprite == null) airDefRocketSprite = new Sprite(rocket.getTexture());
            airDefRocketSprite.setTexture(rocket.getTexture());
            airDefRocketSprite.setBounds(rocket.getHitbox().x, rocket.getHitbox().y, rocket.getHitbox().width, rocket.getHitbox().height);
            airDefRocketSprite.setOrigin(rocket.getHitbox().width/2, rocket.getHitbox().height/2);
            airDefRocketSprite.rotate(rocket.getAngle() * (-1)-180);
            airDefRocketSprite.draw(GameScreen.game.batch);
            GameScreen.disposableTextures.add(airDefRocketSprite.getTexture());
        }

        GameScreen.game.batch.end();
    }

    private static void hideSnovyda() {
        Iterator<AirDefRocket> iter = airDefRockets.iterator();
        AirDefRocket rocket;
        while(iter.hasNext()) {
            rocket = iter.next();
            if(rocket.getTarget() == null) continue;
            if(rocket.getTarget().getName().equalsIgnoreCase("Snovyda")) {
                findNewTarget(rocket);
            }
        }
        snovydaLost = false;
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

    private static float rotateRocket(AirDefRocket rocket, Rectangle hitbox) {

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

        Iterator<AirDef> airDefIter = airDefs.iterator();
        Iterator<Rocket> rocketsIter;

        while (airDefIter.hasNext()) {

            AirDef airDefUnit = airDefIter.next();
            rocketsIter = Rockets.rockets.iterator();

            while (rocketsIter.hasNext()) {

                Rocket rocket = rocketsIter.next();

                if (airDefUnit.getCircleHitbox().overlaps(rocket.getHitbox())
                        && !isTargetedByThisAirDef(rocket, airDefUnit)
                        && (TimeUtils.nanoTime() - airDefUnit.getLastLaunchTime()) * GameScreen.gameSpeed
                        > airDefUnit.getReload() * 1000000000f) {

                    if(rocket.getName().equalsIgnoreCase("snovyda")) {
                        if(airDefUnit.getName().contains("OkoHora")
                                || rocket.isTargeted()) {
                            launchAirDef(rocket, airDefUnit);
                            rocket.setTargetedState(true);
                        }else continue;
                    }else if(airDefUnit.getName().contains("OkoHora")) {
                        continue;
                    }

                    if(!airDefUnit.getName().equalsIgnoreCase("pulsar")  // pulsar ability
                            && !airDefUnit.getName().equalsIgnoreCase("pulsar-s")) {

                        airDefUnit.setLastLaunchTime(TimeUtils.nanoTime());
                    }

                    launchAirDef(rocket, airDefUnit);
                    rocket.setTargetedState(true);
                }
            }

            if((TimeUtils.nanoTime() - airDefUnit.getLastLaunchTime()) * GameScreen.gameSpeed
                    > airDefUnit.getReload() * 1000000000f
                    && (airDefUnit.getName().equalsIgnoreCase("pulsar")  // pulsar ability
                    || airDefUnit.getName().equalsIgnoreCase("pulsar-s"))) {

                if(airDefRockets == null || airDefRockets.size==0) continue;
                Rocket anotherTarget = findClosestTarget(airDefRockets.peek(), true);

                while(anotherTarget != null && !isTargetedByThisAirDef(anotherTarget, airDefUnit)) {
                    launchAirDef(anotherTarget, airDefUnit);
                    anotherTarget.setTargetedState(true);
                    anotherTarget = findClosestTarget(airDefRockets.peek(), true);
                }

                airDefUnit.setLastLaunchTime(TimeUtils.nanoTime());
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
            if(rocket.getName().equalsIgnoreCase("Snovyda")) {
                if(airDefRocket.getOrigin().getName().contains("OkoHora")
                        || rocket.isTargeted()) {
                    rocketsMap.put((Rockets.getDistance(airDefPos, targetPos)), rocket);
                }
            }else {
                rocketsMap.put((Rockets.getDistance(airDefPos, targetPos)), rocket);
            }
        }

        if(rocketsMap.size()==0 && skipTargeted) return findClosestTarget(airDefRocket, false);
        else if (rocketsMap.size()==0) return null;
        else return rocketsMap.get(rocketsMap.firstKey());
    }

    private static void removeTarget(Rectangle hitbox, AirDef airDef) {
        Iterator<Rocket> iter = Rockets.rockets.iterator();
        Rocket rocket;
        while (iter.hasNext()) {
            rocket = iter.next();
            if (rocket.getHitbox().overlaps(hitbox)) {
                Sounds.addSound("rocket_explode");
                if(airDef.getName().equalsIgnoreCase("armahedon")) rocket.disableAbility("spawn");
                rocket.setEliminated(true);
                if(rocket.isEliminated()) {
                    GameScreen.disposableTextures.add(rocket.getTexture());
                    iter.remove();
                }
                break;

            }
        }
    }

    public static void removeAirDef(AirDef airDefToRemove) {
        Iterator<AirDef> iter = airDefs.iterator();
        AirDef airDef;
        while(iter.hasNext()) {
            airDef = iter.next();
            if(airDef.equals(airDefToRemove)) {
                GameScreen.disposableTextures.add(airDef.getTexture());
                iter.remove();
                break;
            }
        }
    }

    private static boolean miss(AirDefRocket airDefRocket) {

        AirDef airDef = airDefRocket.getOrigin();
        Rocket rocket = airDefRocket.getTarget();
        float[] rocketPos = new float[]{rocket.getHitbox().x, rocket.getHitbox().y};

        float speedEff = (airDef.getOptimalSpeed() >= rocket.getSpeed()) ? 1 : (airDef.getOptimalSpeed()/rocket.getSpeed());
        float sizeEff = (airDef.getOptimalSize() <= rocket.getRocketSize()) ? 1 : (rocket.getRocketSize()/airDef.getOptimalSize());
        float centralEff = 1 - (1 - airDef.getCentrality())*(Rockets.getDistance(airDef.getPos(), rocketPos)/(airDef.getRadius()*GameScreen.globalScale));
        float distanceEff = 0.5f + Math.abs(0.5f * (Rockets.getDistance(rocketPos, rocket.getTargetPos()) / Rockets.getDistance(rocket.getSpawnPoint(), rocket.getTargetPos())) - 0.5f);

        float totalEff = speedEff * sizeEff * centralEff * distanceEff;
//        System.out.println("speed: "+speedEff+"\n" +
//                "size: "+sizeEff+"\n" +
//                "cental: "+centralEff+"\n" +
//                "dist: "+distanceEff+"\n"+
//                "total: "+totalEff);

        return MathUtils.random(0, 100) > totalEff * 100;
    }

    private static void setCornerTarget(AirDefRocket airDefRocket) {
        if(cornerTargets.size==0) fillCornerTargetsArray();

        float angle = airDefRocket.getAngle();
        if(angle < 90) airDefRocket.setTarget(cornerTargets.get(0));
        else if(angle < 180) airDefRocket.setTarget(cornerTargets.get(1));
        else if(angle < 270) airDefRocket.setTarget(cornerTargets.get(2));
        else airDefRocket.setTarget(cornerTargets.get(3));
    }

    private static void fillCornerTargetsArray() {
        cornerTargets.add(new SimpleRocket("City-3",
                new float[]{GameScreen.screenWidth, GameScreen.screenHeight}));
        cornerTargets.add(new SimpleRocket("City-3",
                new float[]{GameScreen.screenWidth, 0}));
        cornerTargets.add(new SimpleRocket("City-3",
                new float[]{0, 0}));
        cornerTargets.add(new SimpleRocket("City-3",
                new float[]{0, GameScreen.screenHeight}));
    }

}
