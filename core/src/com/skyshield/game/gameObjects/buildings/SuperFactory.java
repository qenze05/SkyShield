package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.GameScreen;

import static com.badlogic.gdx.math.MathUtils.random;

public class SuperFactory {
    private Texture texture;
    private int width;
    private int height;
    private float[] pos;
    public static int rocketCount = 0;
    private float timeSinceLastProduction;
    private final float productionInterval;
    private int health;
    private int number;
    private Rectangle hitbox;

    public int weaponsProduced;
    private boolean disabled;

    public SuperFactory(float[] pos) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/factory.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                40 * GameScreen.textureScale * 1.25f,
                40 * GameScreen.textureScale * 1.25f);
        this.timeSinceLastProduction = 0;
        this.productionInterval = 0.01f; // Виробляти ракету кожну 1 секунду
        this.weaponsProduced -= 0;
        this.disabled = false;
    }

    public void update(float deltaTime) {
        if(disabled
                || GUIComponents.dialogWindow != null
                || DialogActions.afterDialogActionActive
                || GUIComponents.goldTable != null
                || Phase.draw) return;
        timeSinceLastProduction += deltaTime*GameScreen.gameSpeed;
        if (timeSinceLastProduction >= productionInterval) {
            produceRocket();
            timeSinceLastProduction = 0;
        }
    }

    public void setDisabled(boolean value) {
        this.disabled = value;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public static int getRocketCount() {
        return rocketCount;
    }

    public int getWeaponsProduced() {
        return weaponsProduced;
    }

    public void produceRocket() {
        int rocketsProduced = (int) (120* Attack.coef);
        rocketCount += rocketsProduced;
        weaponsProduced += rocketsProduced;
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

    public void takeWeaponsFromFactory(int quantity) {
        if (weaponsProduced >= quantity) {
            weaponsProduced -= quantity;
            rocketCount -= quantity;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
