package com.skyshield.game.gui.phase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.skyshield.game.gameLogic.events.Attack;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.sound.GameMusic;
import com.skyshield.game.sound.Sounds;

public class Phase {

    public static boolean draw;
    public static Image label, bg;
    public static Texture labelTexture;
    public static Texture bgTexture = new Texture(Gdx.files.internal("phase/bg.png"));

    public static void drawPhase() {
        if (label != null && bg != null) {

            GameScreen.stage.getBatch().begin();

            label.act(1 / 60f);
            bg.act(1 / 60f);

            bg.draw(GameScreen.stage.getBatch(), 1);
            label.draw(GameScreen.stage.getBatch(), 1);

            GameScreen.stage.getBatch().end();

            if (Phase.label.getX() == GameScreen.screenWidth) {
                Phase.label = null;
                Phase.bg = null;
                Phase.draw = false;
            }

        }
    }

    public static void addPhase(int n) {
        Sounds.addSound("event");
        labelTexture = new Texture(Gdx.files.internal("phase/" + n + ".png"));

        label = new Image(labelTexture);
        bg = new Image(bgTexture);

        label.setPosition(-GameScreen.screenWidth, 0);
        bg.setPosition(GameScreen.screenWidth, 0);

        label.addAction(Actions.sequence(Actions.moveTo(-GameScreen.screenWidth * 1f / 20, 0, 0.4f, Interpolation.sine),
                Actions.moveTo(GameScreen.screenWidth * 1f / 20, 0, 0.7f, Interpolation.linear),
                Actions.moveTo(GameScreen.screenWidth, 0, 0.4f, Interpolation.sine)));
        bg.addAction(Actions.sequence(Actions.moveTo(GameScreen.screenWidth * 1f / 20, 0, 0.4f, Interpolation.sine),
                Actions.moveTo(-GameScreen.screenWidth * 1f / 20, 0, 0.7f, Interpolation.linear),
                Actions.moveTo(-GameScreen.screenWidth, 0, 0.4f, Interpolation.sine)));

        draw = true;

        if(n != Attack.phase) {
            GameMusic.removeSound();
            GameMusic.addSound(Attack.phase + "-2");
        }

    }
}
