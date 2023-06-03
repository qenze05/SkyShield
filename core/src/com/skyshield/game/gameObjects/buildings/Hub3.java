package com.skyshield.game.gameObjects.buildings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Hub3 {
    private final Hub2 hub2;
    int HealthMax = 150;
    public int checkDamageBefore;
    private City city;
    private static int trainedSoldiers = 0;
    private int limit;
    private Texture texture;
    private PowerStation powerStation;
    private int health;
    private float[] pos;
    private float timeSinceLastProduction;
    private float trainingDuration;
    private boolean isTraining;
    private int trainingSize;
    public int weapons = 0;

    public Hub3(float[] pos, Hub2 hub2, PowerStation powerStation, int health, int limit) {
        this.pos = pos;
        this.hub2 = hub2;
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
        if (hub2.getWeapons() >= maxCapacity) {
            isTraining = true;
            hub2.takeWeaponsFromFactory(trainingSize);
            Factory.setRocketCount(trainingSize);
        }
    }
    private void finishTraining() {
        trainedSoldiers += trainingSize * health / HealthMax / checkDamageBefore;
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
    public int getWeapons() {

        return hub2.getWeapons();
    }
    public void takeWeaponsFromFactory(int quantity) {
        if (weapons >= quantity) {
            weapons -= quantity;
            hub2.takeWeaponsFromFactory(quantity);
        }
    }
}

