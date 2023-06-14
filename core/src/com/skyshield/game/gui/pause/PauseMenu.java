package com.skyshield.game.gui.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.screens.LoadingScreen;
import com.skyshield.game.screens.MainMenu;
import com.skyshield.game.sound.GameDialog;
import com.skyshield.game.sound.GameMusic;

public class PauseMenu extends Table {

    public PauseMenu() {
        ImageButton resume = new ImageButton(new Image(new Texture(Gdx.files.internal("pause/resume.png"))).getDrawable());
        ImageButton newGame = new ImageButton(new Image(new Texture(Gdx.files.internal("pause/newgame.png"))).getDrawable());
        ImageButton encyclopedia = new ImageButton(new Image(new Texture(Gdx.files.internal("pause/encyclopedia.png"))).getDrawable());
        ImageButton exit = new ImageButton(new Image(new Texture(Gdx.files.internal("pause/exit.png"))).getDrawable());

        setBounds(GameScreen.screenWidth/2f-resume.getWidth()/2f, 170, resume.getWidth(), GameScreen.screenHeight-340);

        add(resume).expand();
        row();
        add(newGame).expand();
        row();
        add(encyclopedia).expand();
        row();
        add(exit).expand();

        resume.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.game.resume();
                return true;
            }
        });

        newGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.game.resume();
                GameScreen.gameRestarted = true;
                GameScreen.game.setScreen(new LoadingScreen(GameScreen.game));
                return true;
            }
        });

        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.game.resume();
                GameScreen.gameContinued = true;
                GameDialog.pauseSound();
                GameDialog.removeSound();
                GameMusic.pauseSound();
                GameMusic.removeSound();
                GameScreen.game.setScreen(new MainMenu(GameScreen.game));
                return true;
            }
        });
    }
}
