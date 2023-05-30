package com.skyshield.game.gameObjects.rockets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Rocket {
    public Rocket(float[] target, float[] spawnPoint) {
    }

    public abstract boolean isTargeted();
    public abstract boolean isEliminated();
    public abstract void setTargetedState(boolean state);
    public abstract void setEliminated(boolean state);
    public abstract boolean canReach();

    public abstract float getMaxDistance();

    public abstract float getPower();

    public abstract float getRocketSize();

    public abstract float getSpeed();

    public abstract float[] getSpawnPoint();

    public abstract float[] getTarget();

    public abstract Texture getTexture();

    public abstract Rectangle getHitbox();

    public abstract int getFrame();

    public abstract int getAngle();

    public abstract void setHitbox(Rectangle newHitbox);

    public abstract void setFrame(int newFrame);
    public abstract void setAngle(int newAngle);

}
