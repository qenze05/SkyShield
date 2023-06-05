package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class PowerStation {
    private final Texture texture;
    private int width;
    private int height;
    private float[] pos;
    private int health;
    private int number;
    private Position position;
    private Rectangle hitbox;
    int healthmax =100;

    public PowerStation(float[] pos, int health, int number) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("buildings/powerStation.jpg"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                30 * GameScreen.textureScale,
                30 * GameScreen.textureScale);
        this.health = health;
        this.number = number;
        this.position = new Position((int)pos[0], (int)pos[1]);
    }
    public  void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, pos[0], pos[1],getWidth(),getHeight());
    }
    // Геттери та сеттери для всіх полів

    public int getHealth() {
        return health;
    }
    public Texture getTexture() {
        return texture;
    }

    public float[] getPos() {
        return pos;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public int calculateHealthPercentage() {
        return health/healthmax;
    }

    public int calculateRepairCost() {
        return 100- calculateHealthPercentage() * 100;
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

