package com.skyshield.game.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.airDefence.AirDef;
import com.skyshield.game.airDefence.AirDefRocket;
import com.skyshield.game.rockets.Rocket;

public class AirDefLogic {

    public static void moveRocket(AirDefRocket rocket) {

        Rectangle hitbox = rocket.getHitbox();

        if(rocket.getTarget() != null) {
            rocket.setAngle(RocketMovement.rotateRocket(
                    new float[]{hitbox.x + hitbox.getWidth() / 2, hitbox.y + hitbox.getHeight() / 2},
                    new float[]{rocket.getTarget().getHitbox().x, rocket.getTarget().getHitbox().y},
                    rocket.getAngle(), 10));

            if (rocket.getAngle() < 0) rocket.setAngle(rocket.getAngle() + 360);
            else if (rocket.getAngle() > 360) rocket.setAngle(rocket.getAngle() - 360);
        }

        hitbox.setPosition(hitbox.x + RocketMovement.getMaxSpeedShiftX(rocket.getSpeed(), rocket.getAngle()),
                hitbox.y + RocketMovement.getMaxSpeedShiftY(rocket.getSpeed(), rocket.getAngle()));
    }

    public static boolean isTargetedByThisAirDef(Array<AirDefRocket> airDefRockets, Rocket rocket, AirDef airDef) {
        if(airDefRockets == null) return false;
        for(AirDefRocket airDefRocket : airDefRockets) {
            if(airDefRocket.getTarget() == null) continue;
            if(airDefRocket.getTarget().equals(rocket) && airDefRocket.getOrigin().equals(airDef)) return true;
        }
        return false;
    }
}
