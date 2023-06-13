package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.gameObjects.buildings.City;
import com.skyshield.game.screens.GameScreen;

import static com.badlogic.gdx.math.MathUtils.random;
import com.skyshield.game.gameLogic.events.Attack;

public class Barracks {

    private final int maxhealth;
    private City city;
    private static int trainedSoldiers =0;
    private int limit;
    private Texture texture;
    private PowerStation powerStation;
    private int health;
    public double checkDamageBefore;
    private float[] pos;
    private float timeSinceLastProduction;
    private float productionInterval;
    private float timeSinceLastTraining;
    private float trainingDuration;
    private boolean isTraining;
    public int trainingSize;
    private Rectangle hitbox;
    private boolean disabled;
    public Barracks(float[] pos, City city, PowerStation powerStation, int maxhealth, int limit) {
        this.pos = pos;
        this.city = city;
        this.powerStation = powerStation;
        this.health = maxhealth;
        this.maxhealth = maxhealth;
        this.limit = (int) (limit*Attack.coef);
        this.texture = new Texture(Gdx.files.internal("buildings/military.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale,
                30 * GameScreen.textureScale);
        this.timeSinceLastProduction = 0;
        this.productionInterval = 3600f/GameScreen.gameSpeed;;
        this.timeSinceLastTraining = 0;
        this.trainingDuration = 180/GameScreen.gameSpeed;
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
        if(!isTraining){
            produceSoldiers();
        }
        timeSinceLastProduction += deltaTime*GameScreen.gameSpeed;
        if (isTraining) {

            if (timeSinceLastProduction >= trainingDuration) {
                finishTraining();
            }
        }
    }
    private void produceSoldiers() {
        checkDamageBefore=health/maxhealth;
        int healthPercentage = (int) (powerStation.calculateHealthPercentage() *checkDamageBefore*calculateHealthPercentage());
        int maxCapacity = (int) (Attack.coef*(limit * healthPercentage));
        trainingSize = Math.min(City.totalPopulation, maxCapacity);
        if (City.totalPopulation >= maxCapacity) {
            City.totalPopulation -= trainingSize;
            isTraining = true;
        }
    }
    private void finishTraining() {
        trainedSoldiers += trainingSize*(calculateHealthPercentage()/checkDamageBefore);
        isTraining = false;
        timeSinceLastProduction -= trainingDuration;
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
    public double calculateHealthPercentage() {
        return health/maxhealth;
    }
    public int calculateRepairCost() {
        return (maxhealth-health) * 10;
    }

}
