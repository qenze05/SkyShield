package com.skyshield.game.gameLogic.events;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.screens.GameScreen;

public class OneTargetAttack {

    public static long attackStartTime = TimeUtils.nanoTime();
    public static long lastRocketSpawnTime;
    private final float[] target;

    //TODO: target choice; other events
    public OneTargetAttack(float[] target) {
        this.target = target;
        attack();
    }

    public static void attack() {
        if (Rockets.rockets == null) {
            attackStartTime = TimeUtils.nanoTime();
            Rockets.rockets = new Array<>();
            Rockets.spawnRocket("simple", new float[]{563, 538}, new float[]{1200, 117});
            lastRocketSpawnTime = TimeUtils.nanoTime();
        }
        if (TimeUtils.nanoTime() - lastRocketSpawnTime > 100000000f / GameScreen.gameSpeed) {
            Rockets.spawnRocket("SIMPLE",
                    new float[]{420, 420},
                    new float[]{1200, 117});
            if (MathUtils.random(0, 100) > 90) Rockets.spawnRocket("FAST",
                    new float[]{420, 420},
                    new float[]{1121, 641});
            lastRocketSpawnTime = TimeUtils.nanoTime();
        }
        Rockets.launchRockets();
    }
}
