package com.skyshield.game.gameLogic.events;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameLogic.entities.Buildings;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gameObjects.buildings.Barracks;
import com.skyshield.game.gameObjects.buildings.Factory;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.clock.Clock;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.sound.GameMusic;
import com.skyshield.game.utils.CountryTerritory;
import com.skyshield.game.utils.ItemsList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attack {
    public static double coef = 1;
    public static int phase = 1;
    public static int[] attackStartTime;
    public static int[] lastRocketSpawnTime;
    public static int[] cooldown;
    public static int[] eventCooldown;
    public static int[] event2Cooldown;
    public static int chessOrderValue = 1;
    public static boolean dialogAdded = false;
    public static ArrayList<Integer> phase1Rockets;
    public static ArrayList<Integer> phase2Rockets;
    public static ArrayList<Integer> phase3Rockets;
    public static ArrayList<Integer> phase4Rockets;
    public static ArrayList<Integer> phase5Rockets;
    public static ArrayList<Integer> phase6Rockets;

    public static void attack() {

        if (cooldown != null && !Clock.compareTimer(Clock.getTime(), Clock.setTimer(2, cooldown))) return;
        else cooldown = null;

        if (Rockets.rockets == null) {

            GameScreen.disposeGarbage();
            GameMusic.removeSound();
            GameMusic.addSound(String.valueOf(phase));

            Phase.addPhase(phase);

            attackStartTime = Clock.getTime();
            lastRocketSpawnTime = Clock.getTime();

            Rockets.rockets = new Array<>();

            CountryTerritory.updateMap(phase);

            Buildings.setDisabled();
            ItemsList.buildings = ItemsList.getBuildings();

            dialogAdded = false;
        }

        switch (phase) {
            case 1 -> phase1();
            case 2 -> phase2();
            case 3 -> phase3();
            case 4 -> phase4();
            case 5 -> phase5();
            case 6 -> phase6();
            case 7 -> phase7();
            case 8 -> phase8();
        }
    }

    public static void startCooldown() {
        cooldown = Clock.getTime();
    }

    public static boolean success(int min) {
        if(Math.min(Barracks.getTotalTrainedSoldiers(), Factory.getRocketCount()) < min) {
            GameScreen.addFailScreen();
        }
        return Math.min(Barracks.getTotalTrainedSoldiers(), Factory.getRocketCount()) >= min;
    }

    public static void phase1() {
        coef=1;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 4, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(3000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 4, attackStartTime))) {

            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 3, attackStartTime))) {

                if(!dialogAdded) {
                    GUIComponents.addDialogTable();
                    dialogAdded = true;
                    return;
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(3, lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    if (phase1Rockets.size() != 0) {

                        String rocket = "r" + phase1Rockets.get(0);
                        phase1Rockets.remove(0);

                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                    }

                }
            } else {

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(8, 12), lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    String rocket = MathUtils.randomBoolean() ? "r1" : "r2";
                    Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                }
            }
        }


    }

    public static void phase2() {
        coef=1.5;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 6, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(10000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 6, attackStartTime))) {
            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

                if(!dialogAdded) {
                    GUIComponents.addDialogTable();
                    dialogAdded = true;
                    return;
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(1.7f, lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    if (phase2Rockets.size() != 0) {

                        String rocket = "r" + phase2Rockets.get(0);
                        phase2Rockets.remove(0);

                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                    }

                }
            } else {

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(7, 9), lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    String rocket = MathUtils.randomBoolean() ? "r1" : "r2";

                    if (MathUtils.random(1, 5) > 1) {

                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

                    } else {

                        int amount = MathUtils.random(3, 5);
                        String target = ItemsList.getRandomBuilding();

                        for (int i = 0; i < amount; i++) {
                            rocket = MathUtils.randomBoolean() ? "r1" : "r2";
                            Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
                        }
                    }
                }
            }
        }
    }

    public static void phase3() {
        coef=1.75f;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 6, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(20000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 6, attackStartTime))) {
            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

                if(!dialogAdded) {
                    GUIComponents.addDialogTable();
                    dialogAdded = true;
                    return;
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(1.2f, lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    if (phase3Rockets.size() != 0) {

                        String rocket = "r" + phase3Rockets.get(0);
                        if (rocket.equalsIgnoreCase("r4")) rocket = "korshun";
                        phase3Rockets.remove(0);

                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                    }

                }
            } else {

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(6, 8), lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    String rocket = "r" + MathUtils.random(1, 3);

                    int random = MathUtils.random(1, 10);

                    if (random > 3) {

                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

                    } else if (random > 1) {

                        phase3ComboAttack();
                    } else {

                        Rockets.spawnRocket("korshun", ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                    }
                }
            }
        }
    }

    private static void phase3ComboAttack() {
        String rocket;
        int amount = MathUtils.random(3, 5);
        String target = ItemsList.getRandomBuilding();

        for (int i = 0; i < amount; i++) {
            rocket = "r" + MathUtils.random(1, 3);
            Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
        }
    }

    public static void phase4() {
        coef=2.25f;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 7, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(35000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            eventCooldown = null;
            chessOrderValue = 1;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 7, attackStartTime))) {

            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

                if(!dialogAdded) {
                    GUIComponents.addDialogTable();
                    dialogAdded = true;
                    return;
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(1.6f, lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    if (phase4Rockets.size() != 0) {

                        String rocket = "";
                        int type = phase4Rockets.get(0);
                        phase4Rockets.remove(0);

                        switch (type) {
                            case 1 -> rocket = "r1";
                            case 2 -> rocket = "r2";
                            case 3 -> rocket = "r3";
                            case 4 -> rocket = "korshun";
                            case 5, 6 -> rocket = "mukha";
                        }

                        int amount = (type == 5) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                        float[] spawn = Rockets.getRandomSpawn();

                        if (type < 5) {
                            Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), spawn);
                        } else {
                            for (int i = 0; i < amount; i++) {
                                Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), spawn);
                            }
                        }
                    }
                }

            } else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 4, attackStartTime))) {

                phase4NormalAttack();

                if (eventCooldown == null) eventCooldown = Clock.getTime();
                else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, eventCooldown))) {

                    eventCooldown = Clock.getTime();

                    int amount = (chessOrderValue % 2 == 1) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                    chessOrderValue++;
                    float[] spawn = Rockets.getRandomSpawn();

                    for (int i = 0; i < amount; i++) {
                        Rockets.spawnRocket("mukha", ItemsList.getRandomBuilding(), spawn);
                    }
                }


            } else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 2, attackStartTime))) {

                phase4NormalAttack();

                if (eventCooldown == null) eventCooldown = Clock.getTime();
                else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, eventCooldown))) {

                    eventCooldown = Clock.getTime();

                    int amount = MathUtils.random(4, 7);
                    float[] spawn = Rockets.getRandomSpawn();

                    for (int i = 0; i < amount; i++) {
                        Rockets.spawnRocket("mukha", ItemsList.getRandomBuilding(), spawn);
                    }
                }

            } else {
                phase4NormalAttack();
            }
        }
    }

    private static void phase4NormalAttack() {
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(4, 6), lastRocketSpawnTime))) {

            lastRocketSpawnTime = Clock.getTime();

            String rocket = "r" + MathUtils.random(1, 3);

            int random = MathUtils.random(1, 100);

            if (random > 35) {

                Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

            } else if (random > 15) {

                phase3ComboAttack();

            } else {

                Rockets.spawnRocket("korshun", ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
            }
        }
    }

    public static void phase5() {
        coef=3.5f;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 7, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(60000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            eventCooldown = null;
            event2Cooldown = null;
            chessOrderValue = 1;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 7, attackStartTime))) {

            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

                if(!dialogAdded) {
                    GUIComponents.addDialogTable();
                    dialogAdded = true;
                    return;
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(1.6f, lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    if (phase5Rockets.size() != 0) {

                        String rocket = "";
                        int type = phase5Rockets.get(0);
                        phase5Rockets.remove(0);

                        switch (type) {
                            case 1 -> rocket = "r2";
                            case 2 -> rocket = "r3";
                            case 3 -> rocket = "korshun";
                            case 4, 5 -> rocket = "mukha";
                            case 6 -> rocket = "harpun";
                        }

                        int amount = (type == 4) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                        float[] spawn = Rockets.getRandomSpawn();
                        String target = (MathUtils.randomBoolean())
                                ? ItemsList.getRandomBuilding()
                                : ItemsList.getRandomBuilding("Dam");

                        if (type < 4) {

                            Rockets.spawnRocket(rocket, target, spawn);

                        } else if (type < 6) {

                            for (int i = 0; i < amount; i++) {
                                target = (MathUtils.randomBoolean())
                                        ? ItemsList.getRandomBuilding()
                                        : ItemsList.getRandomBuilding("Dam");
                                Rockets.spawnRocket(rocket, target, spawn);
                            }

                        } else {

                            Rockets.spawnRocket(rocket, target, Rockets.getRandomSeaSpawn());
                        }
                    }
                }

            } else {

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(4, 6), lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    String rocket = "r" + MathUtils.random(1, 4);
                    if (rocket.equalsIgnoreCase("r4")) rocket = "korshun";

                    int random = MathUtils.random(1, 100);

                    if (random > 25) {

                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

                    } else {

                        int amount = MathUtils.random(3, 5);
                        String target = ItemsList.getRandomBuilding();

                        for (int i = 0; i < amount; i++) {
                            rocket = "r" + MathUtils.random(1, 4);
                            if (rocket.equalsIgnoreCase("r4")) rocket = "korshun";
                            Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
                        }

                    }
                }

                if (eventCooldown == null || event2Cooldown == null) {
                    eventCooldown = Clock.getTime();
                    event2Cooldown = Clock.setTimer(7.5f, Clock.getTime());
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, eventCooldown))) {

                    eventCooldown = Clock.getTime();

                    int amount = (MathUtils.random(1, 100) > 33) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                    float[] spawn = Rockets.getRandomSpawn();

                    for (int i = 0; i < amount; i++) {
                        Rockets.spawnRocket("mukha", ItemsList.getRandomBuilding(), spawn);
                    }
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, event2Cooldown))) {

                    event2Cooldown = Clock.getTime();

                    Rockets.spawnRocket("harpun", ItemsList.getRandomBuilding(), Rockets.getRandomSeaSpawn());
                }
            }
        }
    }

    public static void phase6() {
        coef=5;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 8, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(100000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            eventCooldown = null;
            event2Cooldown = null;
            chessOrderValue = 1;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 8, attackStartTime))) {

            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

                if(!dialogAdded) {
                    GUIComponents.addDialogTable();
                    dialogAdded = true;
                    return;
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(1.8f, lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    if (phase6Rockets.size() != 0) {

                        String rocket = "";
                        int type = phase6Rockets.get(0);
                        phase6Rockets.remove(0);

                        switch (type) {
                            case 1 -> rocket = "korshun";
                            case 2 -> rocket = "sapsan";
                            case 3, 4 -> rocket = "mukha";
                            case 5 -> rocket = "harpun";
                            case 6 -> rocket = "snovyda";
                            case 7 -> rocket = "elektra";
                        }

                        int amount = (type == 3 || type == 7) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                        float[] spawn = Rockets.getRandomSpawn();
                        String target = (MathUtils.randomBoolean())
                                ? ItemsList.getRandomBuilding()
                                : ItemsList.getRandomBuilding("PowerStation");

                        if (type < 3 || type == 6) {

                            Rockets.spawnRocket(rocket, target, spawn);

                        } else if (type < 5 || type == 7) {

                            for (int i = 0; i < amount; i++) {
                                target = (MathUtils.randomBoolean())
                                        ? ItemsList.getRandomBuilding()
                                        : ItemsList.getRandomBuilding("PowerStation");
                                Rockets.spawnRocket(rocket, target, spawn);
                            }

                        } else {
                            Rockets.spawnRocket(rocket, target, Rockets.getRandomSeaSpawn());
                        }
                    }
                }

            } else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 3, attackStartTime))) {

                phase6NormalAttack();

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, eventCooldown))) {

                    eventCooldown = Clock.getTime();

                    int amount = (MathUtils.randomBoolean()) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                    float[] spawn = Rockets.getRandomSpawn();

                    for(int i = 0; i < amount; i++) {
                        Rockets.spawnRocket("elektra", ItemsList.getRandomBuilding(), spawn);
                    }
                }

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(19, event2Cooldown))) {

                    event2Cooldown = Clock.getTime();

                    Rockets.spawnRocket("snovyda", ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                }

            } else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 2, attackStartTime))){

                phase6NormalAttack();
                mukhaEvent();

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(19, event2Cooldown))) {

                    event2Cooldown = Clock.getTime();

                    Rockets.spawnRocket("snovyda", ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
                }

            } else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60, attackStartTime))) {

                phase6NormalAttackSecond();

                if (eventCooldown == null) eventCooldown = Clock.getTime();
                else mukhaEvent();

            } else {

                if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(4, 6), lastRocketSpawnTime))) {

                    lastRocketSpawnTime = Clock.getTime();

                    int type = MathUtils.random(1, 3);
                    String rocket = "";
                    switch (type) {
                        case 1 -> rocket = "r3";
                        case 2 -> rocket = "korshun";
                        case 3 -> rocket = "harpun";
                    }

                    int random = MathUtils.random(1, 100);

                    if (random > 30) {
                        if (type == 3)
                            Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSeaSpawn());
                        else Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

                    } else {

                        int amount = MathUtils.random(3, 5);
                        String target = ItemsList.getRandomBuilding();

                        for (int i = 0; i < amount; i++) {
                            type = MathUtils.random(1, 3);
                            switch (type) {
                                case 1 -> rocket = "r3";
                                case 2 -> rocket = "korshun";
                                case 3 -> rocket = "harpun";
                            }
                            if (type == 3) Rockets.spawnRocket(rocket, target, Rockets.getRandomSeaSpawn());
                            else Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
                        }

                    }
                }

                if (eventCooldown == null) eventCooldown = Clock.getTime();
                else mukhaEvent();
            }
        }
    }

    private static void phase6NormalAttackSecond() {
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(MathUtils.random(4, 6), lastRocketSpawnTime))) {

            lastRocketSpawnTime = Clock.getTime();

            int type = MathUtils.random(1, 4);
            String rocket = "";
            switch (type) {
                case 1 -> rocket = "r3";
                case 2 -> rocket = "korshun";
                case 3 -> rocket = "harpun";
                case 4 -> rocket = "sapsan";
            }

            int random = MathUtils.random(1, 100);

            if (random > 30) {
                if (type == 3)
                    Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSeaSpawn());
                else Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

            } else {

                int amount = MathUtils.random(3, 5);
                String target = ItemsList.getRandomBuilding();

                for (int i = 0; i < amount; i++) {
                    type = MathUtils.random(1, 4);
                    switch (type) {
                        case 1 -> rocket = "r3";
                        case 2 -> rocket = "korshun";
                        case 3 -> rocket = "harpun";
                        case 4 -> rocket = "sapsan";
                    }
                    if (type == 3) Rockets.spawnRocket(rocket, target, Rockets.getRandomSeaSpawn());
                    else Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
                }

            }
        }
    }

    private static void mukhaEvent() {
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, eventCooldown))) {

            eventCooldown = Clock.getTime();

            int amount = (MathUtils.randomBoolean()) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
            float[] spawn = Rockets.getRandomSpawn();

            for (int i = 0; i < amount; i++) {
                Rockets.spawnRocket("mukha", ItemsList.getRandomBuilding(), spawn);
            }
        }
    }

    private static void phase6NormalAttack() {
        phase6NormalAttackSecond();

        if (eventCooldown == null || event2Cooldown == null) {
            eventCooldown = Clock.getTime();
            event2Cooldown = Clock.getTime();
        }
    }

    public static void phase7() {
        coef=7.5f;
        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 7, attackStartTime))
                && Rockets.rockets.size == 0) {

            if (success(150000)) phase++;
            else GameScreen.game.pause();

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            eventCooldown = null;
            event2Cooldown = null;
            chessOrderValue = 1;
            startCooldown();

        } else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 7, attackStartTime))) {

            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(5, lastRocketSpawnTime))) {

                lastRocketSpawnTime = Clock.getTime();

                int type = MathUtils.random(1, 5);
                String rocket = "";
                rocket = phase7GetRocket(type, rocket);

                int random = MathUtils.random(1, 100);

                if (random > 30) {
                    if (type == 3)
                        Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSeaSpawn());
                    else Rockets.spawnRocket(rocket, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());

                } else {

                    int amount = MathUtils.random(3, 5);
                    String target = ItemsList.getRandomBuilding();

                    for (int i = 0; i < amount; i++) {
                        type = MathUtils.random(1, 5);
                        rocket = phase7GetRocket(type, rocket);
                        if (type == 3) Rockets.spawnRocket(rocket, target, Rockets.getRandomSeaSpawn());
                        else Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
                    }

                }
            }

            phase7Event();
        }
    }

    private static void phase7Event() {
        if (eventCooldown == null || event2Cooldown == null) {
            eventCooldown = Clock.getTime();
            event2Cooldown = Clock.getTime();
        }

        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(15, eventCooldown))) {

            eventCooldown = Clock.getTime();

            String type = MathUtils.randomBoolean() ? "troyanskyykin" : "kobra";
            Rockets.spawnRocket(type, ItemsList.getRandomBuilding(), Rockets.getRandomSpawn());
        }

        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(20, event2Cooldown))) {

            event2Cooldown = Clock.getTime();

            int amount = (MathUtils.random(1, 4) > 1) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
            String type = (MathUtils.random(1, 4) > 1) ? "mukha" : "elektra";
            float[] spawn = Rockets.getRandomSpawn();

            for (int i = 0; i < amount; i++) {
                Rockets.spawnRocket(type, ItemsList.getRandomBuilding(), spawn);
            }
        }
    }

    private static String phase7GetRocket(int type, String rocket) {
        switch (type) {
            case 1 -> rocket = "r3";
            case 2 -> rocket = "korshun";
            case 3 -> rocket = "harpun";
            case 4 -> rocket = "sapsan";
            case 5 -> rocket = "snovyda";
        }
        return rocket;
    }

    public static void phase8() {

        if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(62 * 5, attackStartTime))
                && Rockets.rockets.size == 0) {

            GUIComponents.addDialogTable();
            Rockets.rockets = null;
            eventCooldown = null;
            event2Cooldown = null;

        } else if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

            if(!dialogAdded) {
                GUIComponents.addDialogTable();
                dialogAdded = true;
            }

        }else if (!Clock.compareTimer(Clock.getTime(), Clock.setTimer(60 * 5, attackStartTime))) {

            if (Clock.compareTimer(Clock.getTime(), Clock.setTimer(1.1f, lastRocketSpawnTime))) {

                lastRocketSpawnTime = Clock.getTime();

                String rocket = Rockets.getRandomRocket();
                String target = "";

                int random = MathUtils.random(1, 3);
                switch (random) {
                    case 1 -> target = ItemsList.getRandomBuilding("PowerStation");
                    case 2 -> target = ItemsList.getRandomBuilding("Dam");
                    case 3 -> target = "City-2";
                }

                if (rocket.equalsIgnoreCase("harpun")) {

                    Rockets.spawnRocket(rocket, target, Rockets.getRandomSeaSpawn());

                } else if (rocket.equalsIgnoreCase("elektra") || rocket.equalsIgnoreCase("mukha")) {

                    int amount = (MathUtils.randomBoolean()) ? MathUtils.random(4, 7) : MathUtils.random(8, 10);
                    float[] spawn = Rockets.getRandomSpawn();

                    for (int i = 0; i < amount; i++) {

                        random = MathUtils.random(1, 3);
                        switch (random) {
                            case 1 -> target = ItemsList.getRandomBuilding("PowerStation");
                            case 2 -> target = ItemsList.getRandomBuilding("Dam");
                            case 3 -> target = "City-2";
                        }
                        Rockets.spawnRocket(rocket, target, spawn);
                    }
                } else {
                    Rockets.spawnRocket(rocket, target, Rockets.getRandomSpawn());
                }
            }

            phase7Event();
        }
    }


    public static void setRandomRocketOrders() {

        // 1 - r1, 2 - r2
        phase1Rockets = new ArrayList<>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2));


        // 1 - r2, 2 - r2, 3 - r3
        phase2Rockets = new ArrayList<>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3));

        // 1 - r1, 2 - r2, 3 - r3, 4 - korshun
        phase3Rockets = new ArrayList<>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                3, 3, 3, 3, 3, 4, 4, 4, 4, 4));

        // 1 - r1, 2 - r2, 3 - r3, 4 - korshun, 5 - mukha small, 6 - mukha big
        phase4Rockets = new ArrayList<>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                3, 3, 3, 3, 3, 4, 4, 4, 4, 4,
                4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                6, 6, 6, 6, 6));

        // 1 - r2, 2 - r3, 3 - korshun, 4 - mukha small, 5 - mukha big, 6 - harpun
        phase5Rockets = new ArrayList<>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                3, 3, 3, 3, 3, 4, 4, 4, 4, 4,
                4, 4, 4, 4, 4, 5, 5, 5, 5, 5,
                5, 5, 5, 5, 5, 6, 6, 6, 6, 6,
                6, 6, 6, 6, 6));

        // 1 - korshun, 2 - sapsan, 3 - mukha small, 4 - mukha big, 5 - harpun, 6 - snovyda, 7 - elektra
        phase6Rockets = new ArrayList<>(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                3, 3, 3, 3, 3, 4, 4, 4, 4, 4,
                4, 4, 4, 4, 4, 5, 5, 5, 5, 5,
                5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
                6, 6, 6, 6, 6, 7, 7, 7, 7, 7,
                7, 7, 7, 7, 7, 7, 7, 7, 7, 7));

        Collections.shuffle(phase1Rockets);
        Collections.shuffle(phase2Rockets);
        Collections.shuffle(phase3Rockets);
        Collections.shuffle(phase4Rockets);
        Collections.shuffle(phase5Rockets);
        Collections.shuffle(phase6Rockets);
    }
}
