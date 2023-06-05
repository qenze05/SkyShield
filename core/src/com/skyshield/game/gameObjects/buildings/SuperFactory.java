package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

import static com.badlogic.gdx.math.MathUtils.random;

public class SuperFactory {
    private Texture texture;
    private int width;
    private int height;
    private float[] pos;
    private static int rocketCount = 0;
    private float timeSinceLastProduction;
    private final float productionInterval;
    private int health;
    int healthmax = 200;
    private int number;
    private Rectangle hitbox;

    public int weaponsProduced; // Лічильник виробленої зброї

    public SuperFactory(float[] pos) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/Factory.jpg"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale,
                30 * GameScreen.textureScale);
        this.timeSinceLastProduction = 0;
        this.productionInterval = 0.01f; // Виробляти ракету кожну 1 секунду
        this.weaponsProduced -= 0;
    }

    public void update(float deltaTime) {
        float randomCoefficient = 0.00001f + random.nextFloat() * (0.0001f - 0.000001f);
        timeSinceLastProduction += deltaTime * randomCoefficient;
        if (timeSinceLastProduction >= productionInterval) {
            produceRocket();
            timeSinceLastProduction = 0;
        }
    }

    public static int getRocketCount() {
        return rocketCount;
    }

    public int getWeaponsProduced() {
        return weaponsProduced;
    }

    public void produceRocket() {
        int rocketsProduced = 5;
        rocketCount += rocketsProduced;
        weaponsProduced += rocketsProduced; // Збільшити лічильник виробленої зброї
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
