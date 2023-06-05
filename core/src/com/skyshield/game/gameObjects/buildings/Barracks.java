package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameObjects.buildings.City;
import com.skyshield.game.screens.GameScreen;

import static com.badlogic.gdx.math.MathUtils.random;

public class Barracks {
    int HealthMax = 150;
    private City city;
    private static int trainedSoldiers =0;
    private int limit;
    private Texture texture;
    private PowerStation powerStation;
    private int health;
    public int checkDamageBefore;
    public int checkDamageAfter;
    private float[] pos;
    private float timeSinceLastProduction;
    private float productionInterval;
    private float timeSinceLastTraining;
    private float trainingDuration;
    private boolean isTraining;
    public int trainingSize;
    private Rectangle hitbox;


    public Barracks(float[] pos, City city, PowerStation powerStation, int health, int limit) {
        this.pos = pos;
        this.city = city;
        this.powerStation = powerStation;
        this.health = health;
        this.limit = limit;
        this.texture = new Texture(Gdx.files.internal("buildings/kazarma.jpg"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                20 * GameScreen.textureScale,
                20 * GameScreen.textureScale);
        this.timeSinceLastProduction = 0;
        this.productionInterval = 420;
        this.timeSinceLastTraining = 0;
        this.trainingDuration = 180;
        this.isTraining = false;
    }

    public void update(float deltaTime) {
        if(!isTraining){
            produceSoldiers();
        }
        timeSinceLastProduction += deltaTime;
        if (isTraining) {

            if (timeSinceLastProduction >= trainingDuration) {
                finishTraining();
            }
        }
    }

    private void produceSoldiers() {
        checkDamageBefore=health/HealthMax;
        int healthPercentage = powerStation.calculateHealthPercentage() *checkDamageBefore;
        int maxCapacity = (limit * healthPercentage);
        trainingSize = Math.min(City.totalPopulation, maxCapacity);
        if (City.totalPopulation >= maxCapacity) {
            City.totalPopulation -= trainingSize;
            isTraining = true;
        }
    }

    private void finishTraining() {
        trainedSoldiers += trainingSize*health/HealthMax/checkDamageBefore;
        isTraining = false;
        timeSinceLastProduction = 0;
    }



    public static int getTotalTrainedSoldiers() {
        return trainedSoldiers;
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

    public Rectangle getHitbox() {
        return hitbox;
    }
}
