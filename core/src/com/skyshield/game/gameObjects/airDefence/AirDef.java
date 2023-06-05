package com.skyshield.game.gameObjects.airDefence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class AirDef {

    public AirDef(float[] pos) {
    }

    public abstract float[] getPos();
    public abstract String getName();
    public abstract float getReload();
    public abstract float getOptimalSize();
    public abstract float getOptimalSpeed();
    public abstract float getCentrality();
    public abstract float getRadius();
    public abstract long getLastLaunchTime();
    public abstract Texture getTexture();
    public abstract Texture getCircleTexture();
    public abstract Rectangle getCircleHitbox();
    public abstract void specialAbility();
    public abstract int getPrice();

    public abstract void setLastLaunchTime(long time);
}
