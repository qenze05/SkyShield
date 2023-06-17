package com.skyshield.game.gameObjects.airDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;

public class Pulsar extends AirDef{

    private final float[] pos;
    private final float reload;
    private final float optimalSize;
    private final float optimalSpeed;
    private final float centrality;
    private final float radius;
    private long lastLaunchTime;
    private Texture texture;
    private final Texture circleTexture;
    private final Rectangle circleHitbox;
    private final String name;
    private final int price;
    private boolean locked;
    private float health;
    private final float maxHealth;

    public Pulsar(float[] pos) {
        super(pos);
        this.pos = pos;
        this.name = "Pulsar";
        this.optimalSpeed = 300;
        this.optimalSize = 1;
        this.reload = 1;
        this.radius = 250;
        this.centrality = 0.5f;
        this.price = 20000;
        this.lastLaunchTime = 0;
        this.texture = new Texture(Gdx.files.internal("air-defence/Pulsar.png"));
        this.circleTexture = new Texture(Gdx.files.internal("air-defence/range.png"));
        this.circleHitbox = new Rectangle(pos[0] - radius * GameScreen.globalScale,
                pos[1] -  radius * GameScreen.globalScale,
                radius * GameScreen.globalScale * 2,
                radius * GameScreen.globalScale * 2);
        this.locked = false;
        this.maxHealth = 100;
        this.health = 100;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public float getHealthPercentage() {
        return health/maxHealth;
    }

    @Override
    public void setHealth(float value) {
        this.health += value;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public boolean isLocked() {
        return this.locked;
    }

    @Override
    public void setLocked(boolean value) {
        this.locked = value;
    }

    @Override
    public void setTexture() {
        this.texture = locked
                ? new Texture(Gdx.files.internal("air-defence/Pulsar-locked.png"))
                : new Texture(Gdx.files.internal("air-defence/Pulsar.png"));
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
