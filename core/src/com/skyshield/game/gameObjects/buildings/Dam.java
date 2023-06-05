package com.skyshield.game.gameObjects.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

public class Dam {
    int HealthMax = 15000;
    private int health;
    private Texture texture;
    private float[] pos;
    private Rectangle hitbox;
    public Dam(float[] pos, int health) {
        this.texture = new Texture(Gdx.files.internal("buildings/damba.jpg"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                40 * GameScreen.textureScale,
                40 * GameScreen.textureScale);
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

    public Rectangle getHitbox() {
        return hitbox;
    }
}
