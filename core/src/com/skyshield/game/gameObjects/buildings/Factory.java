package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.math.MathUtils.random;

public class Factory {
    private Texture texture;
    private int width;
    private int height;
    private float[] pos;
    private PowerStation powerStation;
    private static int rocketCount = 0;
    private float timeSinceLastProduction;
    private final float productionInterval;
    private int health;
    int healthmax =200;
    private int number;

    public Factory(float[] pos, PowerStation powerStation, int health, int number) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/Factory.jpg"));
        this.powerStation = powerStation;
        this.timeSinceLastProduction = 0;
        this.productionInterval = 0.01f; // Виробляти ракету кожну 1 секунду
        this.health = health;
        this.number = number;
    }
    public void update(float deltaTime) {
        float randomCoefficient = 0.00001f + random.nextFloat() * (0.0001f - 0.000001f);
        timeSinceLastProduction += deltaTime * randomCoefficient;
        if (timeSinceLastProduction >= productionInterval) {
            produceRocket();
            timeSinceLastProduction = 0;
        }
    }
    public static int getRocketCount() {
        return rocketCount;
    }

    public void produceRocket() {
        double healthPercentage = powerStation.calculateHealthPercentage();
        double rocketsProduced =  healthPercentage*calculateHealthPercentage();
        rocketCount += rocketsProduced;
    }
    public int calculateHealthPercentage() {
        return health/healthmax;
    }



    public Texture getTexture() {
        return texture;
    }


    public float[] getPos() {
        return pos;
    }

    public void setPos(float[] pos) {
        this.pos = pos;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int calculateRepairCost() {
        return 100- calculateHealthPercentage() * 200;
    }
    public static void setRocketCount(int count) {
        rocketCount += count;
    }
}
