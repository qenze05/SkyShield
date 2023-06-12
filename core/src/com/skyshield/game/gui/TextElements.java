package com.skyshield.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.skyshield.game.gameObjects.buildings.City;
import com.skyshield.game.screens.GameScreen;

public class TextElements {
    private static BitmapFont moneyFont, hpFont;
    private static int sellValue = -1;
    private static int repairValue = -1;
    private static String hpValue = "";

    public static void setFontStyle(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Ayuthaya.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = size;
        fontParameter.color = Color.YELLOW;
        fontParameter.borderWidth = 1;
        fontParameter.borderColor = Color.BLACK;
        moneyFont = fontGenerator.generateFont(fontParameter);

        fontParameter.color = Color.RED;
        hpFont = fontGenerator.generateFont(fontParameter);

        fontGenerator.dispose();
    }

    public static void draw() {

        GameScreen.stage.getBatch().begin();
        if(moneyFont == null || hpFont == null) setFontStyle(20);

        if(GUIComponents.shopButton.isChecked() && GUIComponents.shopBackground.isVisible()) {
            moneyFont.draw(GameScreen.stage.getBatch(), String.valueOf(City.getTotalMoney()), 30, 90);
        }

        if(sellValue != -1) {
            moneyFont.draw(GameScreen.stage.getBatch(), String.valueOf(sellValue), 630, 400);
        }

        if(repairValue != -1) {
            moneyFont.draw(GameScreen.stage.getBatch(), String.valueOf(repairValue), 630, 380);
        }

        if(!hpValue.equals("")) {
            hpFont.draw(GameScreen.stage.getBatch(), hpValue, 630, 420);
        }

        GameScreen.stage.getBatch().end();
    }

    public static void addSellValueText(int value) {
        sellValue = value;
    }

    public static void deleteSellValue() {
        sellValue = -1;
    }

    public static void addRepairValueText(int value) {
        repairValue = value;
    }

    public static void deleteRepairValue() {
        repairValue = -1;
    }

    public static void addHpValueText(String value) {
        hpValue = value;
    }

    public static void deleteHpValue() {
        hpValue = "";
    }


}
