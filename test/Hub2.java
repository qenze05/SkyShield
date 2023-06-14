package com.skyshield.game.gameObjects.buildings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.screens.GameScreen;

public class Hub2 {
    private final Hub1 hub1;
    private final int maxhealth;
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
    private boolean disabled;

    public Hub2(float[] pos, Hub1 hub1, PowerStation powerStation, int maxhealth, int limit) {
        this.pos = pos;
        this.hub1 = hub1;
        this.powerStation = powerStation;
        this.health = maxhealth;
        this.maxhealth = maxhealth;
        this.limit = limit;
        this.texture = new Texture(Gdx.files.internal("buildings/armshub.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale,
                30 * GameScreen.textureScale);
        this.timeSinceLastProduction = 0;
        this.trainingDuration = 1800;
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
        timeSinceLastProduction += deltaTime*GameScreen.gameSpeed;
        if (isTraining) {
            if (timeSinceLastProduction >= trainingDuration) {
                finishTraining();
            }
        }
    }

    private void produceSoldiers() {
        checkDamageBefore = health / maxhealth;
        int healthPercentage = (int) (powerStation.calculateHealthPercentage() * checkDamageBefore);
        int maxCapacity = (int) (Attack.coef*limit * healthPercentage);
        trainingSize = Math.min(City.totalPopulation, maxCapacity);
        if (hub1.getWeapons() >= maxCapacity) {
            isTraining = true;
            hub1.takeWeaponsFromFactory(trainingSize);// Забрати зброю з Hub1
        }
    }

    private void finishTraining() {
        weapons += trainingSize * (calculateHealthPercentage()/checkDamageBefore);
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
    public double calculateHealthPercentage() {
        return health/maxhealth;
    }
    public int calculateRepairCost() {
        return (maxhealth-health) * 10;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getMaxhealth() {
        return maxhealth;
    }

}
