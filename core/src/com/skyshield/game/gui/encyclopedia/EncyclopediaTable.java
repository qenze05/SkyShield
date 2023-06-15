package com.skyshield.game.gui.encyclopedia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.screens.GameScreen;

public class EncyclopediaTable extends Table {

    private final Array<Texture> disposable = new Array<>();

    public EncyclopediaTable() {
        setBounds(100, 50, GameScreen.screenWidth - 200, GameScreen.screenHeight - 100);
        add(addCloseButton());
        row();
        add(addScrollPane());

    }

    private ImageButton addCloseButton() {
        Texture texture = new Texture(Gdx.files.internal("cancelButton.png"));
        disposable.add(texture);
        ImageButton button = new ImageButton(new Image(texture).getDrawable());

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                disposeTextures();
                EncyclopediaTable.this.remove();
            }
        });

        return button;
    }

    private void disposeTextures() {
        for(Texture texture : disposable) {
            texture.dispose();
        }
        disposable.clear();
    }
    private ScrollPane addScrollPane() {
        Table paneTable = new Table();
        Texture bg = new Texture(Gdx.files.internal("encyclopedia/bg.png"));
        disposable.add(bg);
        Image bgImage = new Image(bg);

        paneTable.add(bgImage);
        ScrollPane pane = new ScrollPane(paneTable);
        pane.setSmoothScrolling(true);

        GameScreen.pauseStage.setScrollFocus(pane);

        return pane;
    }
}
