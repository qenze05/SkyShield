package com.skyshield.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gui.shop.ShopBackground;
import com.skyshield.game.gui.shop.ShopScrollBar;
import com.skyshield.game.screens.GameScreen;


public class GUIComponents {

    private static ShopBackground shopBackground = new ShopBackground();
    private static ShopScrollBar shopScrollBar = new ShopScrollBar();
    public static ImageButton movingButton;

    public static void addShop() {
        shopBackground = new ShopBackground();
        shopScrollBar = new ShopScrollBar();
        GameScreen.stage.addActor(shopBackground);
        GameScreen.stage.addActor(shopScrollBar);
    }

    public static void removeShop() {
        shopBackground.remove();
        shopScrollBar.remove();
    }

    public static void addButton(ImageButton button) {
        movingButton = new ImageButton(button.getImage().getDrawable());
        movingButton.setName(button.getName());
        GameScreen.stage.addActor(movingButton);

        movingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                placeWeapon();
                removeButton();
            }
        });
    }

    public static void moveButton() {
        movingButton.setPosition(Gdx.input.getX()-32, 693-Gdx.input.getY()-16);
        GameScreen.stage.draw();
    }

    public static void removeButton() {
        movingButton.remove();
        movingButton = null;
    }

    private static void placeWeapon() {
        float[] pos = new float[]{movingButton.getX()+32, movingButton.getY()+16};
        AirDefence.addAirDef(pos, movingButton.getName());
    }
}
