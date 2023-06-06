package com.skyshield.game.gameLogic.events;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gameObjects.buildings.Barracks;
import com.skyshield.game.gameObjects.buildings.Factory;
import com.skyshield.game.gui.clock.Clock;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.ItemsList;

import java.util.Arrays;

public class Attack {
    public static int phase = 1;

    public static int[] attackStartTime;
    public static int[] lastRocketSpawnTime;
    public static int[] cooldown;

    public static void attack() {

//        if((TimeUtils.millis() - cooldown) * GameScreen.gameSpeed < 30 * 1000f) return;
//        else cooldown = 0;

        if (Rockets.rockets == null) {
            attackStartTime = Clock.getTime();
            lastRocketSpawnTime = Clock.getTime();
            Rockets.rockets = new Array<>();
        }

        switch(phase) {
            case 1: phase1();
        }

//        if (TimeUtils.millis() - lastRocketSpawnTime > 100f / GameScreen.gameSpeed) {
//            Rockets.spawnRocket("korshun",
//                    "City-3",
//                    new float[]{1200, 117});
//            lastRocketSpawnTime = TimeUtils.millis();
//        }
//        Rockets.launchRockets();
    }

    public static void startCooldown() {
        cooldown = Clock.getTime();
    }

    public static boolean success(int min) {
        return Math.min(Barracks.getTotalTrainedSoldiers(), Factory.getRocketCount()) >= min;
    }

    public static void phase1() {

        if(Clock.compareTimer(Clock.getTime(), Clock.setTimer(60, attackStartTime))
                && Rockets.rockets.size == 0) {
            System.out.println("ended in " + Arrays.toString(Clock.getTime()));
            if(success(3000)) phase++;
            else GameScreen.game.pause();
            Rockets.rockets = null;
            startCooldown();
        }else if(Clock.compareTimer(Clock.getTime(), Clock.setTimer(30, attackStartTime))) {
            if(Clock.compareTimer(Clock.getTime(), Clock.setTimer(3, lastRocketSpawnTime))) {
                lastRocketSpawnTime = Clock.getTime();
                String rocket = MathUtils.randomBoolean() ? "r1" : "r2";
                Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), new float[]{1200, 120});
            }
        }else{
            if(Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(8, 12), lastRocketSpawnTime))) {
                lastRocketSpawnTime = Clock.getTime();
                String rocket = MathUtils.randomBoolean() ? "r1" : "r2";
                Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), new float[]{1200, 120});
            }
        }

        if(Rockets.rockets != null) Rockets.launchRockets();


    }
}
