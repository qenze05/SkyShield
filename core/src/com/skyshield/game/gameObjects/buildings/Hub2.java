package com.skyshield.game.gameObjects.buildings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

public class Hub2 {
    private final Hub1 hub1;
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
    private Rectangle hitbox;

    public Hub2(float[] pos, Hub1 hub1, PowerStation powerStation, int health, int limit) {
        this.pos = pos;
        this.hub1 = hub1;
        this.powerStation = powerStation;
        this.health = health;
        this.limit = limit;
        this.texture = new Texture(Gdx.files.internal("buildings/hab.jpg"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                20 * GameScreen.textureScale,
                20 * GameScreen.textureScale);
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
        if (hub1.getWeapons() >= maxCapacity) {
            isTraining = true;
            hub1.takeWeaponsFromFactory(trainingSize);// Забрати зброю з Hub1
        }
    }

    private void finishTraining() {
        weapons += trainingSize * health / HealthMax / checkDamageBefore;
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

    public int getWeapons() {
        return weapons;
    }

    public void takeWeaponsFromFactory(int quantity) {
        if (weapons >= quantity) {
            weapons -= quantity;
            hub1.takeWeaponsFromFactory(quantity);
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
