package com.skyshield.game.gui.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.skyshield.game.screens.GameScreen;

public class ShopBackground extends Table{

    public Texture bg = new Texture(Gdx.files.internal("shop-menu/background.png"));


    public ShopBackground() {
        setBounds(0, 0, GameScreen.screenWidth, bg.getHeight());
        add(new Image(bg));
    }


}
