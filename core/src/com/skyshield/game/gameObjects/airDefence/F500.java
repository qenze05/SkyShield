package com.skyshield.game.gameObjects.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

public class F500 extends AirDef{

    private final float[] pos;
    private final int launchesPerMin;
    private final float optimalSize;
    private final float optimalSpeed;
    private final float centrality;
    private final float radius;
    private long lastLaunchTime;
    private final Texture texture;
    private final Texture circleTexture;
    private final Rectangle circleHitbox;
    private final String name;
    private final int price;

    public F500(float[] pos) {
        super(pos);
        this.pos = pos;
        this.name = "F-500";
        this.launchesPerMin = 60;
        this.optimalSize = 4.5f;
        this.optimalSpeed = 750;
        this.centrality = 0.7f;
        this.radius = 50;
        this.lastLaunchTime = 0;
        this.texture = new Texture(Gdx.files.internal("air-defence/f-500.png"));
        this.circleTexture = new Texture(Gdx.files.internal("air-defence/range.png"));
        this.circleHitbox = new Rectangle(pos[0]- radius, pos[1]- radius,
                radius*2, radius*2);
        this.price = 100;
    }


    @Override
    public float[] getPos() {
        return pos;
    }

    @Override
    public String getName()  {
        return name;
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
    public float getOptimalSpeed() {
        return optimalSpeed;
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
        return new Rectangle(circleHitbox.x+circleHitbox.width/2 - circleHitbox.width * GameScreen.globalScale/2,
                circleHitbox.y+circleHitbox.height/2 - circleHitbox.height * GameScreen.globalScale/2,
                circleHitbox.width * GameScreen.globalScale,
                circleHitbox.height * GameScreen.globalScale);
    }

    @Override
    public void setLastLaunchTime(long time) {
        lastLaunchTime = time;
    }

    @Override
    public int getPrice() {
        return this.price;
    }
}
