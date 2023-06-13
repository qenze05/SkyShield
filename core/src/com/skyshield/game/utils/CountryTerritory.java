package com.skyshield.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class CountryTerritory {

    private static final String NORMAL_SRC = "map/normal/normal-";
    private static final String LOCKED_SRC = "map/locked/";

    public static Polygon map = new Polygon();
    public static float[] mapVertices = new float[100];
    public static int territory = 0;
    private static int lockedMapFrame = 0;
    private static float alpha = 0;

    public static float getLockedMapAlpha() {
        lockedMapFrame++;
        if(lockedMapFrame < 60*3) {
            alpha += 1/180f;
            if(alpha > 1) alpha = 1;
        }else if(lockedMapFrame > 60*8) {
            lockedMapFrame = 0;
            alpha = 0;
        }else{
            alpha -= 1/300f;
            if(alpha < 0) alpha = 0;
        }
        return alpha * 0.5f + 0.2f;
    }
    // 0 - full territory, 7 - all territory is lost
    public static void setTerritory(int value) {
        if(value >= 0 && value <= 7) territory = value;
    }

    public static void setMapPolygon() {
        mapVertices = new MapPolygon(NORMAL_SRC+territory+"-flipped.png").vertices;
        map.setVertices(mapVertices);
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

    public static Texture getLockedTexture() {
        return new Texture(Gdx.files.internal(LOCKED_SRC+territory+".png"));
    }

    public static boolean isInsideTerritory(float x, float y) {
        return map.contains(x, y);
    }

    public static void updateMap(int stage) {
        setTerritory(stage);
        setMapPolygon();

    }
}
