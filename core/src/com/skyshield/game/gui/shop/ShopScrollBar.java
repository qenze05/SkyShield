package com.skyshield.game.gui.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.ItemsList;

public class ShopScrollBar extends Table {

    public ShopScrollBar() {
        setBounds(1280 / 3.2f, 25, 1280 / 1.55f, 120);
        add(addScrollPane());

    }

    private ScrollPane addScrollPane() {
        Table paneTable = new Table();
        ScrollPane pane = new ScrollPane(paneTable);
        pane.setSmoothScrolling(true);
        paneTable.left().top();
        GameScreen.stage.setScrollFocus(pane);
        Texture airDefTexture;
        for (int i = 0; i < ItemsList.uniqueAirDefs.size; i++) {
            if(ItemsList.uniqueAirDefs.get(i).getName().contains("OkoHora")) break;
            airDefTexture = ItemsList.uniqueAirDefs.get(i).getTexture();
            boolean locked = ItemsList.uniqueAirDefs.get(i).isLocked();

            Texture circleTexture = ItemsList.uniqueAirDefs.get(i).getCircleTexture();
            Rectangle circleSize = ItemsList.uniqueAirDefs.get(i).getCircleHitbox();

            Image t = new Image(airDefTexture);
            ImageButton button = new ImageButton(t.getDrawable());

            button.setName(ItemsList.uniqueAirDefs.get(i).getName());

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (GUIComponents.movingButton == null && !locked)
                        GUIComponents.addMovingButton(button, circleTexture, circleSize);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    GUIComponents.addPopUpMenu((int) (Gdx.input.getX()+button.getWidth()/4), (int) (GameScreen.screenHeight - Gdx.input.getY()+button.getHeight()));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    GUIComponents.removePopUpMenu();
                }
            });

            paneTable.add(button);
        }

        return pane;
    }
}
