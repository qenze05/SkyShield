package com.skyshield.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.skyshield.game.gameObjects.buildings.City;
import com.skyshield.game.screens.GameScreen;

public class TextElements {
    private static BitmapFont font;
    private static int sellValue = -1;

    public static void setFontStyle(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Ayuthaya.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        fontParameter.color = Color.YELLOW;
        fontParameter.borderWidth = 1;
        fontParameter.borderColor = Color.BLACK;
        font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    public static void draw() {

        GameScreen.game.batch.begin();
        if(font == null) setFontStyle(20);

        if(GUIComponents.shopButton.isChecked()) {
            font.draw(GameScreen.game.batch, String.valueOf(City.getTotalMoney()), 30, 90);
        }

        if(sellValue != -1) {
            font.draw(GameScreen.game.batch, String.valueOf(sellValue), 630, 400);
        }

        GameScreen.game.batch.end();
    }

    public static void addSellValueText(int value) {
        sellValue = value;
    }

    public static void deleteSellValue() {
        sellValue = -1;
    }
}
