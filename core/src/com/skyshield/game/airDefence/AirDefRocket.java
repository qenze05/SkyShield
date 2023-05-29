package com.skyshield.game.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.rockets.Rocket;

public class AirDefRocket {
    private float[] pos;
    private final Texture texture;
    private Rectangle hitbox;
    private Rocket target;
    private int frame;
    private int angle;
    private float speed;
    private final AirDef origin;
    public long timeCreated;

    public AirDefRocket(float[] pos, Rocket target, AirDef origin) {
        this.pos = pos;
        this.texture = new Texture(Gdx.files.internal("air-defence/rocket.png"));
        this.hitbox = new Rectangle(pos[0], pos[1], texture.getWidth(), texture.getHeight());
        this.target = target;
        this.frame = 0;
        this.speed = 2000;
        this.angle = MathUtils.random(target.getAngle() - 340, target.getAngle() + 340);
        this.origin = origin;
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

    public int getAngle() {
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
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    public void autoSetAngle() {
        if(target != null) {
            this.angle = MathUtils.random(target.getAngle() - 340, target.getAngle() + 340);
            if (angle > 360) angle -= 360;
            else if (angle < 0) angle += 360;
        }

    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
