package com.skyshield.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.skyshield.game.screens.GameScreen;

public class CountryTerritory {

    private static final String NORMAL_SRC = "map/normal/normal-";
    private static final String LOCKED_SRC = "map/locked/";
    private static final String SEA_SRC = "map/sea/";

    public static Polygon map = new Polygon();
    public static Polygon island = getIslandPolygon();
    public static Polygon sea = getSeaPolygon();
    public static float[] mapVertices = new float[100];
    public static int territory = 0;
    public static int lockedMapFrame = 0;
    public static float alpha = 0;

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
        return alpha * 0.3f + 0.2f;
    }
    // 0 - full territory, 7 - all territory is lost
    public static void setTerritory(int value) {
        if(value >= 1 && value <= 8) territory = value;
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

    public static Texture getTerritoryTexture(boolean sea) {
        return sea
                ? new Texture(Gdx.files.internal(SEA_SRC+"sea.png"))
                : new Texture(Gdx.files.internal(NORMAL_SRC+territory+".png"));
    }

    public static Texture getLockedTexture() {
        return new Texture(Gdx.files.internal(LOCKED_SRC+territory+".png"));
    }

    public static boolean isInsideTerritory(float x, float y, String name) {
        return true;
//        return name.equalsIgnoreCase("mushlya")
//                ? sea.contains(x, y) && !island.contains(x, y)
//                : map.contains(x, y) && !sea.contains(x, y) || island.contains(x, y);
    }

    public static void updateMap(int stage) {
        setTerritory(stage);
        setMapPolygon();
        GameScreen.lockedMapSprite = new Sprite(getLockedTexture());
    }

    public static Polygon getIslandPolygon() {
        return new Polygon(new float[]{643, 187, 652, 197, 700, 197, 719, 207, 736, 207,
                747, 200, 778, 195, 776, 155, 765, 152, 764, 142, 750, 131,
                710, 131, 692, 141, 688, 151, 665, 154, 646, 168, 647, 178});
    }

    public static Polygon getSeaPolygon() {
        return new Polygon(new float[]{334, 73, 350, 103, 390, 110, 460, 108, 538, 127, 562, 170,
        560, 225, 626, 250, 652, 272, 759, 263, 781, 277, 890, 281, 951, 272, 1023, 208, 1000, 196,
        932, 185, 858, 168, 825, 122, 690, 87, 614, 64, 540, 72, 438, 61});
    }
}
