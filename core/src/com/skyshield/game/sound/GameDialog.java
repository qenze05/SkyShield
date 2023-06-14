package com.skyshield.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.skyshield.game.gui.dialog.DialogText;

public class GameDialog {

    private static Sound sound;

    public static void addSound() {
        if(DialogText.textCounter>19) return;
        sound = Gdx.audio.newSound(Gdx.files.internal("replicas/"+ DialogText.textCounter+".mp3"));
        sound.play(0.3f);
    }

    public static void removeSound() {
        if(sound!=null) {
            sound.stop();
            sound.dispose();
            sound = null;
        }

    }

    public static void pauseSound() {
        if(sound!=null) {
            sound.pause();
        }
    }

    public static void resumeSound() {
        if(sound!=null) {
            sound.resume();
        }
    }
}
