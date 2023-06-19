package com.skyshield.game.gui.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameLogic.entities.Buildings;
import com.skyshield.game.gameObjects.buildings.*;
import com.skyshield.game.gui.encyclopedia.EncyclopediaTable;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.screens.LoadingScreen;
import com.skyshield.game.screens.MainMenu;
import com.skyshield.game.sound.GameDialog;
import com.skyshield.game.sound.GameMusic;

public class PauseMenu extends Table {

    public long timePaused;
    public PauseMenu() {
        timePaused = TimeUtils.millis();
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
                long time = TimeUtils.millis() - timePaused;
                for(Barracks barrack : Buildings.barracks) {
                    barrack.timeSinceLastProduction -= time;
                }
                for(City city : Buildings.cities) {
                    city.timeSinceLastProductionMoney -= time;
                    city.timeSinceLastProductionPeople -= time;
                }
                for(Factory factory : Buildings.factories) {
                    factory.timeSinceLastProduction -= time;
                }
                for(Hub1 hub1 : Buildings.hub1s) {
                    hub1.timeSinceLastProduction -= time;
                }
                for(Hub2 hub2 : Buildings.hub2s) {
                    hub2.timeSinceLastProduction -= time;
                }
                for(Hub3 hub3 : Buildings.hub3s) {
                    hub3.timeSinceLastProduction -= time;
                }
                for(SuperFactory superFactory : Buildings.superFactories) {
                    superFactory.timeSinceLastProduction -= time;
                }
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

        encyclopedia.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.pauseStage.addActor(new EncyclopediaTable());
            }
        });

        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.gameContinued = true;
                GameDialog.pauseSound();
                GameMusic.pauseSound();
                GameScreen.game.setScreen(new MainMenu(GameScreen.game));
                return true;
            }
        });
    }
}
