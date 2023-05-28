package com.skyshield.game.airDefence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class AirDef {

    public AirDef(float[] pos) {
    }

    public abstract float[] getPos();
    public abstract int getLaunchesPerMin();
    public abstract float getOptimalSize();
    public abstract float getCentrality();
    public abstract float getRadius();
    public abstract long getLastLaunchTime();
    public abstract Texture getTexture();
    public abstract Texture getCircleTexture();
    public abstract Rectangle getCircleHitbox();

    public abstract void setLastLaunchTime(long time);
}
