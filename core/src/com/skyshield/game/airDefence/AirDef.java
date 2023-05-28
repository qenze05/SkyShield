package com.skyshield.game.airDefence;

import com.badlogic.gdx.graphics.Texture;

public abstract class AirDef {

    public AirDef() {
    }

    abstract float[] getPos();
    abstract int getLaunchesPerMin();
    abstract float getOptimalSize();
    abstract float getCentrality();
    abstract int getRange();
    abstract long getLastLaunchTime();
    abstract Texture getTexture();
    abstract Texture getRocketTexture();
    abstract void setLastLaunchTime(long time);
}
