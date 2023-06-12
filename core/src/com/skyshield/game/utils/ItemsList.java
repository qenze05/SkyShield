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
    public static Array<AirDef> uniqueAirDefs = getUniqueAirDefs();

    public static String getRandomBuilding(String type) {
        TreeMap<String, Rectangle> map = new TreeMap<>();
        for(Map.Entry<String, Rectangle> entry : buildings.entrySet()) {
            if(entry.getKey().split("-")[0].equalsIgnoreCase(type)) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return getBuilding(map);
    }

    private static String getBuilding(TreeMap<String, Rectangle> map) {
        int rand = MathUtils.random(0, map.size()-1);
        int counter = 0;
        for(Map.Entry<String, Rectangle> entry : map.entrySet()) {
            if(counter == rand) return entry.getKey();
            counter++;
        }
        return "";
    }

    public static String getRandomBuilding() {
        return getBuilding(buildings);
    }

    public static TreeMap<String, Rectangle> getBuildings() {
        TreeMap<String, Rectangle> map = new TreeMap<>();
        for(int i = 0; i < Buildings.hub1s.size; i++) {
            if(!Buildings.hub1s.get(i).isDisabled()) {
                map.put("Hub1-" + i, Buildings.hub1s.get(i).getHitbox());
            }
        }
        for(int i = 0; i < Buildings.hub2s.size; i++) {
            if(!Buildings.hub2s.get(i).isDisabled()) {
                map.put("Hub2-" + i, Buildings.hub2s.get(i).getHitbox());
            }
        }
        for(int i = 0; i < Buildings.hub3s.size; i++) {
            if(!Buildings.hub3s.get(i).isDisabled()) {
                map.put("Hub3-" + i, Buildings.hub3s.get(i).getHitbox());
            }
        }
//        for(int i = 0; i < Buildings.superFactories.size; i++) {
//            if(!Buildings.superFactories.get(i).isDisabled()) {
//                map.put("SuperFactory-" + i, Buildings.superFactories.get(i).getHitbox());
//            }
//        }
        for(int i = 0; i < Buildings.cities.size; i++) {
            if(!Buildings.cities.get(i).isDisabled()) {
                map.put("City-" + i, Buildings.cities.get(i).getHitbox());
            }
        }
        for(int i = 0; i < Buildings.barracks.size; i++) {
            if(!Buildings.barracks.get(i).isDisabled()) {
                map.put("Barrack-" + i, Buildings.barracks.get(i).getHitbox());
            }
        }
        for(int i = 0; i < Buildings.powerStations.size; i++) {
            if(!Buildings.powerStations.get(i).isDisabled()) {
                map.put("PowerStation-" + i, Buildings.powerStations.get(i).getHitbox());
            }
        }
        for(int i = 0; i < Buildings.dams.size; i++) {
            if(!Buildings.dams.get(i).isDisabled()) {
                map.put("Dam-" + i, Buildings.dams.get(i).getHitbox());
            }
        }
        for(int i = 0; i < Buildings.factories.size; i++) {
            if(!Buildings.factories.get(i).isDisabled()) {
                map.put("Factory-" + i, Buildings.factories.get(i).getHitbox());
            }
        }
        return map;
    }
    public static Array<Rocket> getUniqueRockets() {
        Array<Rocket> arr = new Array<>();
        arr.add(new SimpleRocket("City-1", pos));
        return arr;
    }
    
    
    public static AirDef getAirDef(String name) {
        switch (name.toLowerCase()) {
            case "kronamk1" -> {
                return uniqueAirDefs.get(0);
            }
            case "kronamk2" -> {
                return uniqueAirDefs.get(1);
            }
            case "kronamk3" -> {
                return uniqueAirDefs.get(2);
            }
            case "slon" -> {
                return uniqueAirDefs.get(3);
            }
            case "skorpion" -> {
                return uniqueAirDefs.get(4);
            }
            case "mukhobiyka" -> {
                return uniqueAirDefs.get(5);
            }
            case "pulsar" -> {
                return uniqueAirDefs.get(6);
            }
            case "kronas" -> {
                return uniqueAirDefs.get(7);
            }
            case "slons" -> {
                return uniqueAirDefs.get(8);
            }
            case "skorpions" -> {
                return uniqueAirDefs.get(9);
            }
            case "pulsars" -> {
                return uniqueAirDefs.get(10);
            }
            case "armahedon" -> {
                return uniqueAirDefs.get(11);
            }
            default -> {
                return uniqueAirDefs.get(0);
            }
        }

    }
    public static Array<AirDef> getUniqueAirDefs() {
        Array<AirDef> arr = new Array<>();
        arr.add(new KronaMK1(pos));
        arr.add(new KronaMK2(pos));
        arr.add(new KronaMK3(pos));
        arr.add(new Slon(pos));
        arr.add(new Skorpion(pos));
        arr.add(new Mukhobiyka(pos));
        arr.add(new Pulsar(pos));
//        arr.add(new Mushlya(pos));
        arr.add(new KronaS(pos));
//        arr.add(new Lut(pos));
        arr.add(new SlonS(pos));
        arr.add(new SkorpionS(pos));
        arr.add(new PulsarS(pos));
        arr.add(new Armahedon(pos));
        return arr;
    }
}
