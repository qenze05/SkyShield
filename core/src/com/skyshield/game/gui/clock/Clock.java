package com.skyshield.game.gui.clock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.GameScreen;

import java.util.Arrays;


public class Clock {

    public static int[] time = new int[]{0, 0};
    public static int day = 0;
    public static BitmapFont font;
    public static long timeMillis = TimeUtils.millis();

    public static int[] setTimer(float seconds, int[] from) {
        int clockShift = (int) (seconds * 24);
        int[] timer = Arrays.copyOf(from, from.length);
        for (int i = 0; i <= clockShift; i++) {
            timer[2]++;

            if (timer[2] == 60) {
                timer[2] = 0;
                timer[1]++;
            }

            if (timer[1] == 24) {
                timer[1] = 0;
                timer[0]++;
            }
        }
        return timer;
    }

    /**
     * @return true if greater, false if not;
     */
    public static boolean compareTimer(int[] timer, int[] start) {
        if (timer[0] == start[0]) {
            if (timer[1] == start[1]) {
                return timer[2] > start[2];
            } else return timer[1] > start[1];
        } else return timer[0] > start[0];
    }

    public static int[] getTime() {
        return new int[]{day, time[0], time[1]};
    }

    public static void setFontSize(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Ayuthaya.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        font = fontGenerator.generateFont(fontParameter);
        GameScreen.disposableFonts.add(font);
        fontGenerator.dispose();
    }


    public static void drawClock() {
        updateClock();

        String AMPM = (time[0] < 12) ? "AM" : "PM";
        int hour = (time[0] == 0 || time[0] == 12) ? 12 : ((time[0] > 12) ? time[0] - 12 : time[0]);

        GameScreen.stage.getBatch().begin();
        font.draw(GameScreen.stage.getBatch(), hour + AMPM, 15, 645);
        font.draw(GameScreen.stage.getBatch(), "Day: " + day, 15, 678);
        GameScreen.stage.getBatch().end();
    }

    public static void updateTime() {
        timeMillis = TimeUtils.millis();
    }

    public static void updateClock() {

        if (GUIComponents.dialogWindow != null
                || DialogActions.afterDialogActionActive
                || GUIComponents.goldTable != null
                || Phase.draw) return;

        if (TimeUtils.millis() - Clock.timeMillis >= (1000 / 24) / GameScreen.gameSpeed) {
            time[1]++;

            if (time[1] >= 60) {
                time[1] = 0;
                time[0]++;
            }

            if (time[0] == 24) {
                time[0] = 0;
                day++;
            }

            updateTime();
        }
    }
}
