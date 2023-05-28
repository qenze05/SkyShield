package com.skyshield.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

public class RocketMovement {
    public static float getTakeoffShiftX(int frame, int angle, float maxSpeed) {
        return (float) (Math.sin(Math.toRadians(angle))*(maxSpeed*frame/40)*GameScreen.globalScale/360);
    }

    public static float getTakeoffShiftY(int frame, int angle, float maxSpeed) {
        return (float) (Math.cos(Math.toRadians(angle))*(maxSpeed*frame/40)*GameScreen.globalScale/360);
    }

    public static int rotateRocket(float[] current, float[] target, int angle, int shift) {

        int triangleDegree = getTriangleDegree(current, target);

        if (target[0] < current[0]) {

            if (target[1] < current[1]) {
                if (Math.abs(angle - 270 - triangleDegree) <= 3) return 270 - triangleDegree;
                return (angle < (270 - triangleDegree - 180) || angle > 270 - triangleDegree) ? angle - shift : angle + shift;

            } else if (target[1] > current[1]) {
                if (Math.abs(angle - 270 + triangleDegree) <= 3) return 270 + triangleDegree;
                return (angle < (270 + triangleDegree - 180) || angle > 270 + triangleDegree) ? angle - shift : angle + shift;
            }

        } else if (target[0] > current[0]) {

            if (target[1] < current[1]) {
                if (Math.abs(angle - 90 + triangleDegree) <= 3) return 90 + triangleDegree;
                return (angle < (90 + triangleDegree + 180) && angle > 90 + triangleDegree) ? angle - shift : angle + shift;

            } else if (target[1] > current[1]) {
                if (Math.abs(angle - 90 - triangleDegree) <= 3) return 90 - triangleDegree;
                return (angle < (90 - triangleDegree + 180) && angle > 90 - triangleDegree) ? angle - shift : angle + shift;
            }
        }

        return angle-3;
    }

    private static int getTriangleDegree(float[] current, float[] target) {
        float distance = getDistance(current, target);
        float cathetus = getDistance(current, new float[]{Math.min(current[0], target[0]), current[1]});
        return (int) Math.toDegrees(Math.acos(cathetus / distance));
    }

    public static float getDistance(float[] current, float[] target) {
        return (float) (Math.sqrt(Math.pow((target[0] - current[0]), 2)
                + Math.pow((target[1] - current[1]), 2)) * GameScreen.globalScale);
    }

    public static float getMaxSpeedShiftX(float maxSpeed, int angle) {
        return (float) (Math.sin(Math.toRadians(angle))*maxSpeed*GameScreen.globalScale/360);
    }

    public static float getMaxSpeedShiftY(float maxSpeed, int angle) {
        return (float) (Math.cos(Math.toRadians(angle))*maxSpeed*GameScreen.globalScale/360);
    }

    public static boolean targetReached(Rectangle current, float[] target) {
        return (Math.abs(current.x-target[0]) <= 20 && Math.abs(current.y-target[1]) <= 20);
    }

}
