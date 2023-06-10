package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

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
    private Rectangle hitbox;
    private boolean disabled;

    public Hub1(float[] pos, SuperFactory factory, PowerStation powerStation, int health, int limit) {
        this.pos = pos;
        this.factory = factory;
        this.powerStation = powerStation;
        this.health = health;
        this.limit = limit;
        this.texture = new Texture(Gdx.files.internal("buildings/armshub.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale,
                30 * GameScreen.textureScale);
        this.timeSinceLastProduction = 0;
        this.trainingDuration = 180*3;
        this.isTraining = false;
        this.disabled = false;
    }

    public void setDisabled(boolean value) {
        this.disabled = value;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void update(float deltaTime) {
        if(disabled) return;
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

    public Rectangle getHitbox() {
        return hitbox;
    }
}