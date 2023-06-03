package com.skyshield.game.gui.clock;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.screens.GameScreen;


public class Clock {

    public static int[] time = new int[]{0, 0};
    public static int day = 0;
    private static BitmapFont font;
    public static long timeMillis = TimeUtils.millis();

    public static void setFontSize(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Ayuthaya.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }


    public static void drawClock() {
        String timeString = time[0] + (time[1] < 10 ? ":0" + time[1] : ":" + time[1]);
//        GameScreen.game.batch.begin();
//        font.draw(GameScreen.game.batch, timeString, 15, 645);
//        font.draw(GameScreen.game.batch, "Day: " + day, 15, 678);
//        GameScreen.game.batch.end();
        GameScreen.stage.getBatch().begin();
        font.draw(GameScreen.stage.getBatch(), timeString, 15, 645);
        font.draw(GameScreen.stage.getBatch(), "Day: " + day, 15, 678);
        GameScreen.stage.getBatch().end();
    }

    public static void updateTime() {
        timeMillis = TimeUtils.millis();
    }

    public static void updateClock() {

        time[1]++;

        if (time[1] == 60) {
            time[1] = 0;
            time[0]++;
        }

        if (time[0] == 24) {
            time[0] = 0;
            day++;
        }
    }
}
