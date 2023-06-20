package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.dialog.DialogText;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.GameScreen;
import static com.badlogic.gdx.math.MathUtils.random;
import com.skyshield.game.gameLogic.events.Attack;

public class Factory {
    private Texture texture;
    private int width;
    private int height;
    private float[] pos;
    private PowerStation powerStation;
    public static int rocketCount = 0;
    public float timeSinceLastProduction;
    private final float productionInterval;
    private int health;
    int maxhealth;
    private int number;
    private Rectangle hitbox;
    private boolean disabled;
    private int rocketProduction;
    private double remCoef =1;

    public Factory(float[] pos, PowerStation powerStation, int maxhealth) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/factory.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale * 1.25f,
                30 * GameScreen.textureScale * 1.25f);
        this.powerStation = powerStation;
        this.timeSinceLastProduction = 0;
        this.productionInterval = 1800f;
        this.health = maxhealth;
        this.maxhealth = maxhealth;
        this.disabled = false;
        this.rocketProduction = (int) (maxhealth/7*Attack.coef);
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
        timeSinceLastProduction += deltaTime*GameScreen.gameSpeed;
        if (timeSinceLastProduction >= productionInterval) {
            produceRocket();
            timeSinceLastProduction = 0;
        }
    }
    public static int getRocketCount() {
        return rocketCount;
    }

    public int getMaxhealth() {
        return maxhealth;
    }

    public void produceRocket() {
        double healthPercentage = powerStation.calculateHealthPercentage();
        double rocketsProduced =  rocketProduction*healthPercentage*calculateHealthPercentage();
        rocketCount += rocketsProduced*Attack.coef;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int hp) {
        this.health = Math.min ( Math.max(health+hp, 0), maxhealth );
        if(health <= 0) setTexture (new Texture(Gdx.files.internal("buildings/factory-destroyed.png")));
        else setTexture (new Texture(Gdx.files.internal("buildings/factory.png")));
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static void setRocketCount(int count) {
        rocketCount += count;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
    public double calculateHealthPercentage() {
        return (double) health /maxhealth;
    }
    public int calculateRepairCost() {
        remCoef = remCoef*1.1;
        int repair = (int) (((maxhealth-health) * 10)*remCoef);
        return repair;
    }
}
