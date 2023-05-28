package com.skyshield.game.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class F500 extends AirDef{

    private final float[] pos;
    private final int launchesPerMin;
    private final float optimalSize;
    private final float centrality;
    private final int range;
    private long lastLaunchTime;
    private Texture texture, rocketTexture;
    public F500(float[] pos) {
        this.pos = pos;
        this.launchesPerMin = 30;
        this.optimalSize = 4.5f;
        this.centrality = 0.7f;
        this.range = 150;
        this.lastLaunchTime = 0;
        this.texture = new Texture(Gdx.files.internal("air-defence/f-500.png"));
        this.rocketTexture = new Texture(Gdx.files.internal("air-defence/rocket.png"));
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
    public int getRange() {
        return range;
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
    public Texture getRocketTexture() {
        return rocketTexture;
    }

    @Override
    public void setLastLaunchTime(long time) {
        lastLaunchTime = time;
    }
}
