package com.skyshield.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.skyshield.game.screens.GameScreen;

public class Sounds {

    public static void addSound(String name) {
        float volume = MathUtils.random(0.35f, 0.55f);
        if(name.equals("building_explode")) volume = MathUtils.random(0.4f, 0.65f);
        else if(name.equals("city_explode")) volume = MathUtils.random(0.4f, 0.65f);
        else if(name.equals("rocket_explode")) volume = MathUtils.random(0.15f, 0.35f);
        else if(name.equals("rocket_start")) volume = MathUtils.random(0.05f, 0.15f);
        else if(name.equals("airdef_start")) volume = MathUtils.random(0.10f, 0.15f);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/" + name + ".mp3"));
        sound.play(volume);
        GameScreen.disposableSounds.add(sound);
    }
}
