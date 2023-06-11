package com.skyshield.game.gui.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.utils.ItemsList;

public class DialogActions {
    public static boolean afterDialogActionActive = false;
    private static int counter = -1;

    public static void action() {
        switch (DialogText.textCounter) {
            case 1 -> {
                if (counter == -1) counter++;

                if(GUIComponents.movingButton == null && counter < 2) {
                    ImageButton button = new ImageButton((new Image(ItemsList.getAirDef("KronaMK1").getTexture()).getDrawable()));
                    button.setName(ItemsList.getAirDef("KronaMK1").getName());
                    GUIComponents.addMovingButton(button,
                            ItemsList.getAirDef("KronaMK1").getCircleTexture(),
                            ItemsList.getAirDef("KronaMK1").getCircleHitbox());
                    counter++;

                }else if(counter == 2 && GUIComponents.movingButton == null) {
                    Rockets.rockets = new Array<>();
                    Rockets.spawnRocket("simpleRocket", "City-6", Rockets.spawn[3]);
                    counter++;
                }else if(counter == 3 && Rockets.rockets.size == 0) {
                    Rockets.spawnRocket("simpleRocket", "City-3", Rockets.spawn[3]);
                    counter++;
                }else if(counter == 4 && Rockets.rockets.size == 0) {
                    counter = -1;
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    GUIComponents.addDialogTable();
                }
            }
            case 2 -> {
                if(counter == -1 && Rockets.rockets.size == 0) {
                    Rockets.spawnRocket("immortalrocket", "City-5", Rockets.spawn[2]);
                    counter++;
                }else if(counter == 0 && Rockets.rockets.size == 0) {
                    counter = -1;
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    GUIComponents.addDialogTable();
                    Rockets.rockets = null; //temp
                }
            }

            default -> afterDialogActionActive = false;
        }

    }
}
