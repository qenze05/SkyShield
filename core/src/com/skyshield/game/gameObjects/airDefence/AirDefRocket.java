package com.skyshield.game.gameObjects.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameObjects.rockets.Rocket;
import com.skyshield.game.screens.GameScreen;

public class AirDefRocket {
    private float[] pos;
    private final Texture texture;
    private Rectangle hitbox;
    private Rocket target;
    private int frame;
    private float angle;
    private float speed;
    private final AirDef origin;
    public long timeCreated;
    public boolean wasTargetChanged;

    public AirDefRocket(float[] pos, Rocket target, AirDef origin) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("air-defence/rocket.png"));
        this.hitbox = new Rectangle(pos[0], pos[1],
                texture.getWidth()* GameScreen.textureScale,
                texture.getHeight()*GameScreen.textureScale);
        this.target = target;
        this.frame = 38;
        this.speed = 1500;
        setSpeed();
        this.angle = MathUtils.random(target.getAngle() - 340, target.getAngle() + 340);
        this.origin = origin;
        this.wasTargetChanged = false;
        if (angle > 360) angle -= 360;
        else if (angle < 0) angle += 360;
        timeCreated = TimeUtils.nanoTime();
    }

    public float[] getPos() {
        return pos;
    }

    public float getSpeed() {
        return speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Rocket getTarget() {
        return target;
    }


    public int getFrame() {
        return frame;
    }

    public float getAngle() {
        return angle;
    }

    public AirDef getOrigin() {
        return origin;
    }

    public void setPos(float[] pos) {
        this.pos = pos;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setTarget(Rocket target) {
        this.target = target;
        this.wasTargetChanged = true;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setSpeed() {
        if(target.getSpeed() < speed) return;
        if(target.getSpeed() < 3000) speed = target.getSpeed()*1.5f;
        else speed = 3000;
    }
    public boolean getWasTargetChanged() {
        return wasTargetChanged;
    }

    public void setWasTargetChanged(boolean value) {
        wasTargetChanged = value;
    }
}
