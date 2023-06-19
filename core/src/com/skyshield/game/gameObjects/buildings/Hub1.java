package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.dialog.DialogText;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.GameScreen;

public class Hub1 {
    private final SuperFactory factory;
    private final int maxhealth;
    public int checkDamageBefore;
    private final int limit;
    private Texture texture;
    private final PowerStation powerStation;
    private int health;
    private float[] pos;
    public float timeSinceLastProduction;

    private final float trainingDuration;
    private boolean isTraining;
    private int trainingSize;
    public int weapons;
    private Rectangle hitbox;
    private boolean disabled;

    public Hub1(float[] pos, SuperFactory factory, PowerStation powerStation, int maxhealth, int limit) {
        this.pos = pos;
        this.factory = factory;
        this.powerStation = powerStation;
        this.health = maxhealth;
        this.maxhealth = maxhealth;
        this.limit = (int) (limit*Attack.coef);
        this.texture = new Texture(Gdx.files.internal("buildings/armshub.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale * 1.25f,
                30 * GameScreen.textureScale * 1.25f);
        this.timeSinceLastProduction = 0;
        this.trainingDuration = 1800;
        this.isTraining = false;
        this.disabled = false;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public void setDisabled(boolean value) {
        this.disabled = value;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void update(float deltaTime) {
        if(disabled || health <= 0
                || (GUIComponents.dialogWindow != null && DialogText.textCounter != 18)
                || DialogActions.afterDialogActionActive
                || GUIComponents.goldTable != null
                || Phase.draw) return;
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
        if (factory.getWeaponsProduced() >= maxCapacity) {
            isTraining = true;
            factory.takeWeaponsFromFactory(trainingSize);
        }
    }
    private void finishTraining() {
        weapons += (checkDamageBefore == 0) ? 0 : trainingSize*(calculateHealthPercentage()/checkDamageBefore);
        isTraining = false;
        timeSinceLastProduction -= trainingDuration ;
    }
    public Texture getTexture() {
        return texture;
    }
    public void setHealth(int hp) {
        this.health = Math.min ( Math.max(health+hp, 0), maxhealth );
        if(health <= 0) setTexture (new Texture(Gdx.files.internal("buildings/armshub-destroyed.png")));
        else setTexture (new Texture(Gdx.files.internal("buildings/armshub.png")));
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
    public double calculateHealthPercentage() {
        return (double) health /maxhealth;
    }
    public int calculateRepairCost() {
        return (maxhealth-health) * 10;
    }
}