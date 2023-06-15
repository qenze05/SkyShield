package com.skyshield.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.skyshield.game.gui.dialog.DialogText;

public class GameDialog {

    private static Sound sound;

    public static void addSound() {
        System.out.println(DialogText.textCounter+" added");
        if(DialogText.textCounter>19) return;
        sound = Gdx.audio.newSound(Gdx.files.internal("replicas/"+ DialogText.textCounter+".mp3"));
        float volume = GameMusic.isMusicPlaying() ? 0.5f : 0.3f;
        sound.play(volume);

    }

    public static void removeSound() {
        System.out.println(DialogText.textCounter+" removed");
        if(sound!=null) {
            sound.stop();
            sound.dispose();
            sound = null;
        }

    }

    public static void pauseSound() {
        System.out.println("paused");
        if(sound!=null) {
            sound.pause();
        }
    }

    public static void resumeSound() {
        System.out.println("resumed");
        if(sound!=null) {
            sound.resume();
        }
    }
}
