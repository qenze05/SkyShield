package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameObjects.buildings.PowerStation;
import com.skyshield.game.screens.GameScreen;

public class City {
    int healthmax =1000;
    private Texture texture;
    private float[] pos;
    private PowerStation powerStation;
    private int population;
    private int money;
    private float timeSinceLastProductionMoney;
    private float timeSinceLastProductionPeople;
    private final float moneyProductionInterval;
    private final float populationProductionInterval;
    private int health;

    public static int totalPopulation = 0;
    private static int totalMoney = 0;
    private Rectangle hitbox;

    public City(float[] pos, PowerStation powerStation, float moneyProductionInterval, float populationProductionInterval, int health) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/city.jpg"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                40 * GameScreen.textureScale,
                40 * GameScreen.textureScale);
        this.powerStation = powerStation;
        this.moneyProductionInterval = moneyProductionInterval;
        this.populationProductionInterval = populationProductionInterval;
        this.timeSinceLastProductionMoney = 0;
        this.timeSinceLastProductionPeople = 0;
        this.health = health;
        totalPopulation += 0;
        totalMoney += 0;
    }

    public void update(float deltaTime) {
        timeSinceLastProductionMoney += deltaTime;
        timeSinceLastProductionPeople += deltaTime;

        if (timeSinceLastProductionMoney >= moneyProductionInterval) {
            produceMoney();
            timeSinceLastProductionMoney = 0;
        }

        if (timeSinceLastProductionPeople >= populationProductionInterval) {
            producePopulation();
            timeSinceLastProductionPeople = 0;
        }
    }

    private void produceMoney() {
        int moneyProduced =  (10 * powerStation.calculateHealthPercentage() * calculateHealthPercentage());
        money += moneyProduced;
        totalMoney += moneyProduced;

    }

    private void producePopulation() {
        int populationProduced =  (100 * powerStation.calculateHealthPercentage() * calculateHealthPercentage());
        population += populationProduced;
        totalPopulation += populationProduced;

    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, pos[0], pos[1]);
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

    public int getPopulation() {
        return population;
    }

    public int getMoney() {
        return money;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public static int getTotalPopulation() {
        return totalPopulation;
    }

    public static int getTotalMoney() {
        return totalMoney;
    }

    public static boolean enoughMoney(float price) {
        return totalMoney >= price;
    }

    public static void buyItem(float price) {
        totalMoney -= price;
    }

    public static void sellItem(float price) {
        totalMoney += price;
    }
    public int calculateHealthPercentage() {
        return health/healthmax;
    }
    public void removePopulation(int trainingSize) {
        if (population >= trainingSize) {
            population -= trainingSize;
            totalPopulation -= trainingSize;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}