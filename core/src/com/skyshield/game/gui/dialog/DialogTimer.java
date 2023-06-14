package com.skyshield.game.gui.dialog;

import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gui.GUIComponents;

public class DialogTimer {

    public static long start;
    public static long textStart;

    public static void startText(String text) {
        if(TimeUtils.millis() > textStart) {
            GUIComponents.addDialogText(text);
            GUIComponents.addSkipButton();
        }
    }


}
