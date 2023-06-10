package com.skyshield.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class CountryTerritory {

    private static final String NORMAL_SRC = "map/normal/normal-";

    public static final Polygon map = new Polygon();
    public static int territory = 0;

    // 0 - full territory, 7 - all territory is lost
    public static void setTerritory(int value) {
        if(value >= 0 && value <= 7) territory = value;
    }

    public static void setMapPolygon() {
        map.setVertices(new MapPolygon(NORMAL_SRC+territory+"-flipped.png").vertices);
    }

    public static int getTerritory() {
        return territory;
    }

    public Polygon getCurrentArea() {
        return new Polygon(new MapPolygon(NORMAL_SRC +territory+"-flipped.png").vertices);
    }

    public static Texture getTerritoryTexture() {
        return new Texture(Gdx.files.internal(NORMAL_SRC+territory+".png"));
    }

    public static boolean isInsideTerritory(float x, float y) {
        return map.contains(x, y);
    }

    public static void updateMap(int stage) {
        setTerritory(stage);
        setMapPolygon();

    }
}
