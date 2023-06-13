package com.skyshield.game.gameObjects.buildings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.gameLogic.events.Attack;
public class Hub3 {
    private final Hub2 hub2;
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

    public Hub3(float[] pos, Hub2 hub2, PowerStation powerStation, int maxhealth, int limit) {
        this.pos = pos;
        this.hub2 = hub2;
        this.powerStation = powerStation;
        this.health = maxhealth;
        this.maxhealth = maxhealth;
        this.limit = (int) (limit*Attack.coef);
        this.texture = new Texture(Gdx.files.internal("buildings/armshub.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale * 1.25f,
                30 * GameScreen.textureScale * 1.25f);
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

    public int getHealth() {
        return health;
    }
    public void setHealth(int hp) {
        this.health = Math.min ( Math.max(health+hp, 0), maxhealth );
        if(health <= 0) setTexture (new Texture(Gdx.files.internal("buildings/armshub-destroyed.png")));
        else setTexture (new Texture(Gdx.files.internal("buildings/armshub.png")));
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public int getMaxhealth() {
        return maxhealth;
    }

    public void update(float deltaTime) {
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
        if (hub2.getWeapons() >= maxCapacity) {
            isTraining = true;
            hub2.takeWeaponsFromFactory(trainingSize);
            Factory.setRocketCount(trainingSize);
        }
    }
    private void finishTraining() {
        trainedSoldiers += trainingSize * (calculateHealthPercentage()/checkDamageBefore);
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

