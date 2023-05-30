package com.skyshield.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;

public class CountryTerritory {

    private static final String normalSrc = "map/normal/normal-";

    private static Polygon map = new Polygon();
    public static int territory = 0;

    // 0 - full territory, 7 - all territory is lost
    public static void setTerritory(int value) {
        if(value >= 0 && value <= 7) territory = value;
    }

    public static int getTerritory() {
        return territory;
    }

    public Polygon getCurrentArea() {
        return new Polygon(new MapPolygon(normalSrc+territory+"-flipped.png").vertices);
    }
}
