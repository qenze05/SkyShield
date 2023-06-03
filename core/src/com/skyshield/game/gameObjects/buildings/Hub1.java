package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Hub1 {
    private final SuperFactory factory;
    int HealthMax = 150;
    public int checkDamageBefore;
    private final int limit;
    private final Texture texture;
    private final PowerStation powerStation;
    private final int health;
    private float[] pos;
    private float timeSinceLastProduction;

    private final float trainingDuration;
    private boolean isTraining;
    private int trainingSize;
    public int weapons;

    public Hub1(float[] pos, SuperFactory factory, PowerStation powerStation, int health, int limit) {
        this.pos = pos;
        this.factory = factory;
        this.powerStation = powerStation;
        this.health = health;
        this.limit = limit;
        this.texture = new Texture(Gdx.files.internal("buildings/hab.jpg"));
        this.timeSinceLastProduction = 0;
        this.trainingDuration = 180*3;
        this.isTraining = false;
    }

    public void update(float deltaTime) {
        if (!isTraining) {
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
        checkDamageBefore = health / HealthMax;
        int healthPercentage = powerStation.calculateHealthPercentage() * checkDamageBefore;
        int maxCapacity = (limit * healthPercentage);
        trainingSize = Math.min(City.totalPopulation, maxCapacity);
        if (factory.getWeaponsProduced() >= maxCapacity) {
            isTraining = true;
            factory.takeWeaponsFromFactory(trainingSize);
        }
    }
    private void finishTraining() {
        weapons += trainingSize;
        isTraining = false;
        timeSinceLastProduction = 0;
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
    public void takeWeaponsFromFactory(int quantity) {
        if (weapons >= quantity) {
            weapons -= quantity;
            factory.takeWeaponsFromFactory(quantity);
        }
    }
    public int getWeapons() {
        return weapons;
    }
}