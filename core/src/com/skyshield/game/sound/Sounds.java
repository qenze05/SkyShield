package com.skyshield.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.skyshield.game.screens.GameScreen;

public class Sounds {

    public static void addSound(String name) {
        MathUtils.random(0.35f, 0.55f);
        float volume = switch (name) {
            case "building_explode", "city_explode" -> MathUtils.random(0.4f, 0.65f);
            case "rocket_explode" -> MathUtils.random(0.15f, 0.35f);
            case "rocket_start" -> MathUtils.random(0.05f, 0.15f);
            case "airdef_start" -> MathUtils.random(0.10f, 0.15f);
            default -> MathUtils.random(0.35f, 0.55f);
        };
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + name + ".mp3"));
        sound.play(volume);
        GameScreen.disposableSounds.add(sound);
    }
}
