package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.gameLogic.events.Attack;


public class City {
    public Texture texture;
    private float[] pos;
    private final PowerStation powerStation;
    private int population;
    private int money;
    private float timeSinceLastProductionMoney;
    private float timeSinceLastProductionPeople;
    private final int peopleProduction;
    private final int moneyProduction;
    private final float ProductionInterval;
    private int health;
    public static int totalPopulation = 0;
    public static int totalMoney = 1300;
    private final Rectangle hitbox;
    private boolean disabled;
    private final int maxhealth;

    public City(float[] pos, PowerStation powerStation, int maxhealth) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/city.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                50 * GameScreen.textureScale * 1.25f,
                50 * GameScreen.textureScale * 1.25f);
        this.health = maxhealth;
        this.powerStation = powerStation;
        this.maxhealth = maxhealth;
        this.peopleProduction = (int) (maxhealth/5*Attack.coef);
        this.moneyProduction = (int) (maxhealth*Attack.coef);
        this.ProductionInterval = 3600f;
        this.timeSinceLastProductionMoney = 0;
        this.timeSinceLastProductionPeople = 0;
        this.disabled = false;
    }
    public void setDisabled(boolean value) {
        this.disabled = value;
    }
    public boolean isDisabled() {
        return this.disabled;
    }
    public void update(float deltaTime) {
        if(disabled || health <= 0
                || GUIComponents.dialogWindow != null
                || DialogActions.afterDialogActionActive
                || GUIComponents.goldTable != null
                || Phase.draw) return;
        timeSinceLastProductionMoney += deltaTime*GameScreen.gameSpeed;
        timeSinceLastProductionPeople += deltaTime*GameScreen.gameSpeed;
        if (timeSinceLastProductionMoney >= ProductionInterval) {
            produceMoney();
            timeSinceLastProductionMoney = 0;
        }
        if (timeSinceLastProductionPeople >= ProductionInterval) {
            producePopulation();
            timeSinceLastProductionPeople = 0;
        }
    }
    private void produceMoney() {
        int moneyProduced = (int) (Attack.coef*(moneyProduction*powerStation.calculateHealthPercentage() * calculateHealthPercentage()));
        money += moneyProduced;
        totalMoney += moneyProduced;
    }
    private void producePopulation() {
        int populationProduced = (int) (Attack.coef*(peopleProduction*powerStation.calculateHealthPercentage() * calculateHealthPercentage()));
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

    public int getMaxhealth() {
        return maxhealth;
    }

    public void setHealth(int hp) {
        this.health = Math.min ( Math.max(health+hp, 0), maxhealth );
        if(health <= 0) {
            if(maxhealth == 1000) {
                setTexture (new Texture(Gdx.files.internal("buildings/capital-destroyed.png")));
                GameScreen.addFailScreen();
            }else {
                setTexture (new Texture(Gdx.files.internal("buildings/city-destroyed.png")));
            }
        }
        else {
            if(maxhealth == 1000) {
                setTexture (new Texture(Gdx.files.internal("buildings/capital.png")));
            }else {
                setTexture (new Texture(Gdx.files.internal("buildings/city.png")));
            }
        }
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
    public double calculateHealthPercentage() {
        return (double) health /maxhealth;
    }
    public void removePopulation(int trainingSize) {
        if (population >= trainingSize) {
            population -= trainingSize;
            totalPopulation -= trainingSize;
        }
    }
    public static void setTotalMoney(int money) {
        totalMoney = money;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public int calculateRepairCost() {
        return (maxhealth-health) * 10;
    }
}