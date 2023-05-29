package com.skyshield.game.rockets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameLogic.Rockets;

public class SimpleRocket extends Rocket{

    private final float maxDistance;
    private final float speed;
    private final float power;
    private final float rocketSize;
    private final float[] target;
    private final float[] spawnPoint;
    private final Texture texture;
    private Rectangle hitbox;
    private int frame;
    private int angle;
    private boolean targeted;
    private boolean elimitaned;

    public SimpleRocket(float[] target, float[] spawnPoint) {
        super(target, spawnPoint);
        this.target = target;
        this.spawnPoint = spawnPoint;
        this.maxDistance = 2000;
        this.speed = 1000;
        this.power = 500;
        this.rocketSize = 5;
        this.texture = new Texture(Gdx.files.internal("rockets/simpleRocket.png"));
        this.hitbox = new Rectangle(spawnPoint[0], spawnPoint[1], texture.getWidth(), texture.getHeight());
        this.frame = 0;
        if(target[0] < spawnPoint[0]) angle = MathUtils.random(160, 360);
        else angle = MathUtils.random(0, 200);
        this.targeted = false;
        this.elimitaned = false;
    }

    @Override
    public boolean canReach() {
        float distance = Rockets.getDistance(spawnPoint, target);
        return distance <= maxDistance;
    }

    @Override
    public boolean isTargeted() {
        return targeted;
    }

    @Override
    public boolean isEliminated() {
        return this.elimitaned;
    }

    @Override
    public void setTargetedState(boolean state) {
        targeted = state;
    }

    @Override
    public float getMaxDistance() {
        return maxDistance;
    }

    @Override
    public float getPower() {
        return power;
    }

    @Override
    public float getRocketSize() {
        return rocketSize;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float[] getSpawnPoint() {
        return spawnPoint;
    }

    @Override
    public float[] getTarget() {
        return target;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public int getFrame() {
        return frame;
    }

    @Override
    public int getAngle() {
        return angle;
    }

    @Override
    public void setHitbox(Rectangle newHitbox) {
        this.hitbox = newHitbox;
    }

    @Override
    public void setFrame(int newFrame) {
        this.frame = newFrame;
    }

    @Override
    public void setAngle(int newAngle) {
        this.angle = newAngle;
    }

    @Override
    public void setEliminated(boolean state) {
        this.elimitaned = state;
    }
}
