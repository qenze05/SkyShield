package com.skyshield.game.gui.pause;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PauseBg extends Table {

    public PauseBg() {
        setFillParent(true);
        Image image = new Image(new Texture(Gdx.files.internal("pause/bg.png")));
        setBackground(image.getDrawable());
        addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1, 1f)));

    }
}
