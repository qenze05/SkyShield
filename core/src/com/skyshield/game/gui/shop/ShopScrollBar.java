package com.skyshield.game.gui.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.ItemsList;

public class ShopScrollBar extends Table {

    public ShopScrollBar() {
        setBounds(1280/3.2f, 1280/25.6f, 1280/1.55f, ItemsList.airDefs.get(0).getTexture().getHeight());
        add(addScrollPane());

    }

    private ScrollPane addScrollPane() {
        Table paneTable = new Table();
        paneTable.setDebug(true);
        ScrollPane pane = new ScrollPane(paneTable);
        pane.setSmoothScrolling(true);
        paneTable.left().top();
        GameScreen.stage.setScrollFocus(pane);
        Texture airDefTexture;
        for (int i = 0; i < ItemsList.airDefs.size; i++) {
            airDefTexture = ItemsList.airDefs.get(i).getTexture();
            ImageButton button = new ImageButton(new TextureRegionDrawable(airDefTexture));
            button.setName(ItemsList.airDefs.get(i).getName());
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                        if (GUIComponents.movingButton == null) GUIComponents.addButton(button);
                }
            });
            paneTable.add(button);
        }

        return pane;
    }
}
