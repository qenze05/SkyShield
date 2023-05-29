package com.skyshield.game.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class F500 extends AirDef{

    private final float[] pos;
    private final int launchesPerMin;
    private final float optimalSize;
    private final float centrality;
    private final float radius;
    private long lastLaunchTime;
    private final Texture texture;
    private final Texture circleTexture;
    private final Rectangle circleHitbox;

    public F500(float[] pos) {
        super(pos);
        this.pos = pos;
        this.launchesPerMin = 1000;
        this.optimalSize = 4.5f;
        this.centrality = 0.7f;
        this.radius = 150;
        this.lastLaunchTime = 0;
        this.texture = new Texture(Gdx.files.internal("air-defence/f-500.png"));
        this.circleTexture = new Texture(Gdx.files.internal("air-defence/range.png"));
        this.circleHitbox = new Rectangle(pos[0]- radius, pos[1]- radius,
                radius*2, radius*2);
    }


    @Override
    public float[] getPos() {
        return pos;
    }

    @Override
    public int getLaunchesPerMin() {
        return launchesPerMin;
    }

    @Override
    public float getOptimalSize() {
        return optimalSize;
    }

    @Override
    public float getCentrality() {
        return centrality;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public long getLastLaunchTime() {
        return lastLaunchTime;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Texture getCircleTexture() {
        return circleTexture;
    }

    @Override
    public Rectangle getCircleHitbox() {
        return circleHitbox;
    }

    @Override
    public void setLastLaunchTime(long time) {
        lastLaunchTime = time;
    }
}
