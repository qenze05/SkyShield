package com.skyshield.game.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameObjects.airDefence.F500;
import com.skyshield.game.gameObjects.airDefence.SD250M;
import com.skyshield.game.gameObjects.rockets.FastRocket;
import com.skyshield.game.gameObjects.rockets.Rocket;
import com.skyshield.game.gameObjects.rockets.SimpleRocket;

public class ItemsList {

    private static float[] pos = new float[]{0, 0};

    public static Array<Rocket> rockets = getRockets();
    public static Array<AirDef> airDefs = getAirDef();

    private static Array<Rocket> getRockets() {
        Array<Rocket> arr = new Array<>();
        arr.add(new SimpleRocket(pos, pos));
        arr.add(new FastRocket(pos, pos));
        return arr;
    }

    private static Array<AirDef> getAirDef() {
        Array<AirDef> arr = new Array<>();
        arr.add(new F500(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        arr.add(new SD250M(pos));
        return arr;
    }
}
