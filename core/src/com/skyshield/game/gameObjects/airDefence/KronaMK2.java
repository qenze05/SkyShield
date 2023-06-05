package com.skyshield.game.gameObjects.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

public class KronaMK2 extends AirDef{

    private final float[] pos;
    private final float reload;
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

    public KronaMK2(float[] pos) {
        super(pos);
        this.pos = pos;
        this.name = "Krona-MK2";
        this.optimalSpeed = 1250;
        this.optimalSize = 6;
        this.reload = 1.5f;
        this.radius = 75;
        this.centrality = 0.6f;
        this.price = 2000;
        this.lastLaunchTime = 0;
        this.texture = new Texture(Gdx.files.internal("air-defence/Krona-MK2.png"));
        this.circleTexture = new Texture(Gdx.files.internal("air-defence/range.png"));
        this.circleHitbox = new Rectangle(pos[0] - radius * GameScreen.globalScale,
                pos[1] -  radius * GameScreen.globalScale,
                radius * GameScreen.globalScale * 2,
                radius * GameScreen.globalScale * 2);
    }

    @Override
    public void specialAbility() {

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
    public float getReload() {
        return reload;
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
        return this.circleHitbox;
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
