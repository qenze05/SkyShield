package com.skyshield.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameLogic.entities.Buildings;
import com.skyshield.game.gameObjects.airDefence.*;
import com.skyshield.game.gameObjects.rockets.Rocket;
import com.skyshield.game.gameObjects.rockets.SimpleRocket;

import java.util.Map;
import java.util.TreeMap;

public class ItemsList {

    private static final float[] pos = new float[]{0, 0};

    public static TreeMap<String, Rectangle> buildings = getBuildings();
    public static Array<Rocket> uniqueRockets = getUniqueRockets();
    public static Array<AirDef> uniqueAirDefs = getAirDef();

    public static String getRandomBuilding() {
        int rand = MathUtils.random(0, buildings.size()-1);
        int counter = 0;
        for(Map.Entry<String, Rectangle> entry : buildings.entrySet()) {
            if(counter == rand) return entry.getKey();
            counter++;
        }
        return "";
    }

    public static TreeMap<String, Rectangle> getBuildings() {
        TreeMap<String, Rectangle> map = new TreeMap<>();
        for(int i = 0; i < Buildings.hub1s.size; i++) {
            map.put("Hub1-"+i, Buildings.hub1s.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.hub2s.size; i++) {
            map.put("Hub2-"+i, Buildings.hub2s.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.hub3s.size; i++) {
            map.put("Hub3-"+i, Buildings.hub3s.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.superFactories.size; i++) {
            map.put("SuperFactory-"+i, Buildings.superFactories.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.cities.size; i++) {
            map.put("City-"+i, Buildings.cities.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.barracks.size; i++) {
            map.put("Barrack-"+i, Buildings.barracks.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.powerStations.size; i++) {
            map.put("PowerStation-"+i, Buildings.powerStations.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.dams.size; i++) {
            map.put("Dam-"+i, Buildings.dams.get(i).getHitbox());
        }
        for(int i = 0; i < Buildings.factories.size; i++) {
            map.put("Factory-"+i, Buildings.factories.get(i).getHitbox());
        }
        return map;
    }
    public static Array<Rocket> getUniqueRockets() {
        Array<Rocket> arr = new Array<>();
        arr.add(new SimpleRocket("City-1", pos));
        return arr;
    }

    public static Array<AirDef> getAirDef() {
        Array<AirDef> arr = new Array<>();
        arr.add(new KronaMK1(pos));
        arr.add(new KronaMK2(pos));
        arr.add(new KronaMK3(pos));
        arr.add(new Slon(pos));
        arr.add(new Skorpion(pos));
        arr.add(new Mukhobiyka(pos));
        arr.add(new Pulsar(pos));
        arr.add(new Mushlya(pos));
        arr.add(new KronaS(pos));
        arr.add(new Lut(pos));
        arr.add(new SlonS(pos));
        arr.add(new SkorpionS(pos));
        arr.add(new PulsarS(pos));
        arr.add(new Armahedon(pos));
        return arr;
    }
}
