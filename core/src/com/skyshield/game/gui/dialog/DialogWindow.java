package com.skyshield.game.gui.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.skyshield.game.screens.GameScreen;

public class DialogWindow extends Table {



    public DialogWindow (){
        Texture bg = new Texture(Gdx.files.internal("dialog.png"));
        setBounds(160, GameScreen.screenHeight,
                bg.getWidth(), bg.getHeight()+44);
        setBackground(new Image(bg).getDrawable());
        addAction(Actions.moveTo(160, GameScreen.screenHeight-this.getHeight(),2f, Interpolation.sine));
    }
}
