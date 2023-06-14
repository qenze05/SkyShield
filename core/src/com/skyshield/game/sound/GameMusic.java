package com.skyshield.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.gui.dialog.DialogText;

public class GameMusic {

    private static Music music;

    public static void addSound(String name) {
        if(Attack.phase>8) return;
        music = Gdx.audio.newMusic(Gdx.files.internal("music/"+name+".mp3"));
        music.setVolume(0.05f);
        if (name.contains("-2") || name.equals("8") || name.equals("menu")) music.setVolume(0.1f);
        music.play();
    }

    public static void removeSound() {
        if(music!=null) {
            music.stop();
            music.dispose();
            music = null;
        }

    }

    public static void pauseSound() {
        if(music!=null) {
            music.pause();
        }
    }

    public static void resumeSound() {
        if(music!=null) {
            music.play();
        }
    }
}
