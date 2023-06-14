package com.skyshield.game.gui.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.skyshield.game.screens.GameScreen;

public class Camera {

    public static OrthographicCamera camera;
    public static boolean moveCamera = false;
    public static Vector3 cameraPos = new Vector3((float) GameScreen.screenWidth / 2, (float) GameScreen.screenHeight / 2, 0);

    public static void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameScreen.screenWidth, GameScreen.screenHeight);
        camera.position.lerp(cameraPos, 1);
    }

    public static void resetCameraPos() {
        cameraPos.x = (float) GameScreen.screenWidth / 2;
        cameraPos.y = (float) GameScreen.screenHeight / 2;
        camera.position.lerp(cameraPos, 1);
    }

    public static void changeCameraPos() {

        if (cameraPos.x + (GameScreen.lastClickX - GameScreen.inputX) > getMinCameraX() && cameraPos.x + (GameScreen.lastClickX - GameScreen.inputX) < getMaxCameraX()) {
            cameraPos.x += GameScreen.lastClickX - GameScreen.inputX;
        }

        if (cameraPos.y - (GameScreen.lastClickY - GameScreen.inputY) > getMinCameraY() && cameraPos.y - (GameScreen.lastClickY - GameScreen.inputY) < getMaxCameraY()) {
            cameraPos.y -= GameScreen.lastClickY - GameScreen.inputY;
        }

        camera.position.lerp(cameraPos, 1);

        GameScreen.lastClickX = GameScreen.inputX;
        GameScreen.lastClickY = GameScreen.inputY;
    }

    public static float getMaxCameraX() {
        return camera.viewportWidth - camera.zoom * (camera.viewportWidth / 2);
    }

    public static float getMinCameraX() {
        return camera.zoom * (camera.viewportWidth / 2);
    }

    public static float getMaxCameraY() {
        return camera.viewportHeight - camera.zoom * (camera.viewportHeight / 2);
    }

    public static float getMinCameraY() {
        return camera.zoom * (camera.viewportHeight / 2);
    }

    public static float[] getRelativeCoords(float x, float y) {
        float width = GameScreen.screenWidth;
        float height = GameScreen.screenHeight;

        float xShift = (cameraPos.x - (width / 2));
        float yShift = (cameraPos.y - (height / 2));


        float xPos = xShift + (width / 2) + (width / 2) * (x - width / 2) * camera.zoom / (width / 2);
        float yPos = yShift + (height / 2) + (height / 2) * (y - height / 2) * camera.zoom / (height / 2);

        return new float[]{xPos, yPos};
    }


}
