package com.skyshield.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.skyshield.game.airDefence.AirDefRocket;

public class AirDefLogic {

    public static void moveRocket(AirDefRocket rocket) {

        Rectangle hitbox = rocket.getHitbox();

        rocket.setAngle(RocketMovement.rotateRocket(
                new float[]{hitbox.x + hitbox.getWidth() / 2, hitbox.y + hitbox.getHeight() / 2},
                new float[]{rocket.getTarget().getHitbox().x, rocket.getTarget().getHitbox().y},
                rocket.getAngle(), 15));

        if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
        else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);

        hitbox.setPosition(hitbox.x + RocketMovement.getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                hitbox.y + RocketMovement.getMaxSpeedShiftY(rocket.getTarget().getSpeed(), rocket.getAngle()));
    }
}
