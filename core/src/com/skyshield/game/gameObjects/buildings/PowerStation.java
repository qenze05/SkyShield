package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class PowerStation {
    private Texture texture;
    private int width;
    private int height;
    private float[] pos;
    private int health;
    private int number;
    private Rectangle hitbox;
    private boolean disabled;
    private final int maxhealth;
    public PowerStation(float[] pos, int maxhealth) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/aetherstation.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                40 * GameScreen.textureScale * 1.25f,
                40 * GameScreen.textureScale * 1.25f);
        this.health =maxhealth;
        this.maxhealth = maxhealth;
        this.disabled = false;
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
    public  void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, pos[0], pos[1],getWidth(),getHeight());
    }
    public int getHealth() {
        return health;
    }
    public Texture getTexture() {
        return texture;
    }
    public float[] getPos() {
        return pos;
    }
    public void setHealth(int hp) {
        this.health = Math.min ( Math.max(health+hp, 0), maxhealth );
        if(health <= 0) setTexture (new Texture(Gdx.files.internal("buildings/aetherstation-destroyed.png")));
        else setTexture (new Texture(Gdx.files.internal("buildings/aetherstation.png")));
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
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    public double calculateHealthPercentage() {
        return health/maxhealth;
    }
    public int calculateRepairCost() {
        return (maxhealth-health) * 10;
    }
    public int getWidth() {
        return width/4;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height/4;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}