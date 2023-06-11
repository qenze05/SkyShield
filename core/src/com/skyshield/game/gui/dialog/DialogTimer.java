package com.skyshield.game.gui.dialog;

import com.badlogic.gdx.math.MathUtils;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.clock.Clock;

public class DialogTimer {

    public static int[] start;
    public static int[] textStart;

    public static void startText(String text) {
        if(Clock.compareTimer(Clock.getTime(), textStart)) {
            GUIComponents.addDialogText(text);
            GUIComponents.addSkipButton();
        }
    }


}
