package com.skyshield.game.gui.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.ItemsList;

public class ShopBackground extends Table{

    Texture bg = new Texture(Gdx.files.internal("shop-menu/background.png"));


    public ShopBackground() {
        setBounds(0, 0, GameScreen.camera.viewportWidth, bg.getHeight());
        add(new Image(bg));
    }


}
