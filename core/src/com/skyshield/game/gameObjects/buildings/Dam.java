package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Dam {
    int HealthMax = 15000;
    private int health;
    private Texture texture;
    private float[] pos;
    public Dam(float[] pos, int health) {
        this.texture = new Texture(Gdx.files.internal("buildings/damba.jpg"));
        this.pos = pos;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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
}
