package com.skyshield.game.gameObjects.rockets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.ItemsList;

public class Kobra extends Rocket {

    private final float maxDistance;
    private float speed;
    private float power;
    private float rocketSize;
    private final float[] spawnPoint;
    private Texture texture;
    private Rectangle hitbox;
    private int frame;
    private float angle;
    private boolean targeted;
    private boolean eliminated;
    private String targetName;
    private Rectangle targetHitbox;
    private float[] targetPos;
    private boolean wasEliminatedOnce;
    private boolean abilityDisabled;
    private String name;

    public Kobra(String target, float[] spawnPoint) {
        super(target, spawnPoint);

        this.targetName = target;
        setTargetHitbox();
        setTargetPos();

        this.spawnPoint = spawnPoint;
        this.maxDistance = 2000;
        this.speed = 1500;
        this.power = 75;
        this.rocketSize = 5;

        this.texture = new Texture(Gdx.files.internal("rockets/Kobra1.png"));
        this.hitbox = new Rectangle(spawnPoint[0] - 30 * GameScreen.textureScale,
                spawnPoint[1] - 30 * GameScreen.textureScale,
                60 * GameScreen.textureScale,
                60 * GameScreen.textureScale);

        this.frame = 0;

        if (targetHitbox.x < spawnPoint[0]) angle = MathUtils.random(160, 360);
        else angle = MathUtils.random(0, 200);

        this.targeted = false;
        this.eliminated = false;

        this.wasEliminatedOnce = false;
        this.abilityDisabled = false;
        this.name = "Kobra";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void specialAbility() {
        if(abilityDisabled || wasEliminatedOnce) return;
        eliminated = false;
        this.texture = new Texture(Gdx.files.internal("rockets/Kobra2.png"));
        this.speed = 2500;
        this.rocketSize = 3;
        this.power = 65;
        wasEliminatedOnce = true;
    }

    @Override
    public void disableAbility(String ability) {
        if(ability.equalsIgnoreCase("spawn")) {
            this.abilityDisabled = true;
        }
    }

    @Override
    public boolean isTargeted() {
        return targeted;
    }

    @Override
    public boolean isEliminated() {
        return this.eliminated;
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
    public String getTargetName() {
        return targetName;
    }

    @Override
    public Rectangle getTargetHitbox() {
        return targetHitbox;
    }

    @Override
    public float[] getTargetPos() {
        return targetPos;
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
    public float getAngle() {
        return angle;
    }

    @Override
    public void setTargetHitbox() {
        this.targetHitbox = ItemsList.buildings.get(targetName);
    }

    @Override
    public void setTargetPos() {
        this.targetPos = new float[]{targetHitbox.x+targetHitbox.width/2,
                targetHitbox.y+targetHitbox.height/2};
    }

    @Override
    public void setTargetName(String name) {
        this.targetName = name;
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
    public void setAngle(float newAngle) {
        this.angle = newAngle;
    }

    @Override
    public void setEliminated(boolean state) {
        this.eliminated = state;
        specialAbility();
    }

    @Override
    public boolean canReach() {
//        float distance = Rockets.getDistance(spawnPoint, targetPos);
//        return distance <= maxDistance;
        return true;
    }
}
