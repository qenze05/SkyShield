package com.skyshield.game.gameObjects.rockets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.ItemsList;

public class TroyanskyyKin extends Rocket {

    private final float maxDistance;
    private final float speed;
    private final float power;
    private final float rocketSize;
    private final float[] spawnPoint;
    private final Texture texture;
    private Rectangle hitbox;
    private int frame;
    private float angle;
    private boolean targeted;
    private boolean eliminated;
    private String targetName;
    private Rectangle targetHitbox;
    private float[] targetPos;
    private boolean abilityDisabled;

    public TroyanskyyKin(String target, float[] spawnPoint) {
        super(target, spawnPoint);

        this.targetName = target;
        setTargetHitbox();
        setTargetPos();

        this.spawnPoint = spawnPoint;
        this.maxDistance = 2000;
        this.speed = 1000;
        this.power = 50;
        this.rocketSize = 5;

        this.texture = new Texture(Gdx.files.internal("rockets/TroyanskyyKin.png"));
        this.hitbox = new Rectangle(spawnPoint[0], spawnPoint[1],
                texture.getWidth() * GameScreen.textureScale,
                texture.getHeight() * GameScreen.textureScale);

        this.frame = 0;

        if (targetHitbox.x < spawnPoint[0]) angle = MathUtils.random(160, 360);
        else angle = MathUtils.random(0, 200);

        this.targeted = false;
        this.eliminated = false;
        this.abilityDisabled = false;
    }

    @Override
    public void specialAbility() {
        if(abilityDisabled || Rockets.targetReached(hitbox, targetHitbox)) return;
        int n = MathUtils.random(5, 7);
        String type = (MathUtils.random(1, 4) > 1) ? "Mukha" : "Elektra";
        float x = hitbox.x+hitbox.width/2;
        float y = hitbox.y+hitbox.height/2;
        for(int i = 0; i < n; i++) {
            Rockets.spawnRocket(type, ItemsList.getRandomBuilding(),
                    new float[]{MathUtils.random(x-hitbox.width/2, x+hitbox.width/2),
                            MathUtils.random(y-hitbox.height/2, y+hitbox.height/2)});
        }
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
