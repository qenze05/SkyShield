package com.skyshield.game.gui.dialog;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.skyshield.game.gameLogic.entities.Buildings;
import com.skyshield.game.gameLogic.entities.Rockets;
import com.skyshield.game.gui.GUIComponents;
import com.skyshield.game.gui.phase.Phase;
import com.skyshield.game.screens.FailScreen;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.screens.LoadingScreen;
import com.skyshield.game.sound.GameDialog;
import com.skyshield.game.sound.GameMusic;
import com.skyshield.game.utils.ItemsList;

public class DialogActions {
    public static boolean afterDialogActionActive = false;
    public static int counter = -1;

    public static void action() {
        switch (DialogText.textCounter) {
            case 1 -> {
                if (counter == -1) {
                    ItemsList.unlockAirDefs(1);
                    counter++;
                }

                if (!GUIComponents.buildingButtonJustPressed) GUIComponents.buildingButtonJustPressed = true;
                if (!GUIComponents.airDefButtonJustPressed) GUIComponents.airDefButtonJustPressed = true;

                if (GUIComponents.movingButton == null && counter < 2) {
                    ImageButton button = new ImageButton((new Image(ItemsList.getAirDef("KronaMK1").getTexture()).getDrawable()));
                    button.setName(ItemsList.getAirDef("KronaMK1").getName());
                    GUIComponents.addMovingButton(button,
                            ItemsList.getAirDef("KronaMK1").getCircleTexture(),
                            ItemsList.getAirDef("KronaMK1").getCircleHitbox());
                    counter++;

                } else if (counter == 2 && GUIComponents.movingButton == null) {
                    Rockets.rockets = new Array<>();
                    Rockets.spawnRocket("simpleRocket", "City-6", Rockets.spawn[3]);
                    counter++;
                } else if (counter == 3 && Rockets.rockets.size == 0) {
                    Rockets.spawnRocket("simpleRocket", "City-3", Rockets.spawn[3]);
                    counter++;
                } else if (counter == 4 && Rockets.rockets.size == 0) {
                    counter = -1;
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    GUIComponents.addDialogTable();
                    ItemsList.unlockAirDefs(3);
                }
            }
            case 2 -> {
                if (!GUIComponents.buildingButtonJustPressed) GUIComponents.buildingButtonJustPressed = true;
                if (!GUIComponents.airDefButtonJustPressed) GUIComponents.airDefButtonJustPressed = true;

                if (counter == -1 && Rockets.rockets.size == 0) {
                    Rockets.spawnRocket("immortalrocket", "City-5", Rockets.spawn[2]);
                    counter++;
                } else if (counter == 0 && Rockets.rockets.size == 0) {
                    counter = -1;
                    afterDialogActionActive = false;
                    GUIComponents.buildingButtonJustPressed = false;
                    DialogText.textCounter++;
                    GUIComponents.addDialogTable();
                }
            }
            case 3 -> {
                if (Buildings.cities.get(5).getHealth() == Buildings.cities.get(5).getMaxhealth()) {
                    afterDialogActionActive = false;

                    GUIComponents.airDefButtonJustPressed = false;
                    GUIComponents.buildingButtonJustPressed = false;
                    DialogText.textCounter++;
                    GUIComponents.addDialogTable();
                }
            }
            case 4 -> {
                afterDialogActionActive = false;
                DialogText.textCounter++;
                Rockets.rockets = null;
            }
            case 5 -> {
                if (GUIComponents.goldTable == null) {
                    GUIComponents.addGoldTable("20k");
                }

                else if(GUIComponents.goldTable.getY() == GameScreen.screenHeight) {
                    GUIComponents.removeGoldTable();
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    Phase.addPhase(0);
                }
            }
            case 7 -> {
                if (GUIComponents.goldTable == null) {
                    GUIComponents.addGoldTable("30k");
                }

                else if(GUIComponents.goldTable.getY() == GameScreen.screenHeight) {
                    GUIComponents.removeGoldTable();
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    Phase.addPhase(0);
                }
            }
            case 8 -> {
                ItemsList.unlockAirDefs(5);
                afterDialogActionActive = false;
                DialogText.textCounter++;
            }
            case 9 -> {
                if (GUIComponents.goldTable == null) {
                    GUIComponents.addGoldTable("50k");
                }

                else if(GUIComponents.goldTable.getY() == GameScreen.screenHeight) {
                    GUIComponents.removeGoldTable();
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    Phase.addPhase(0);
                }
            }
            case 10 -> {
                ItemsList.unlockAirDefs(7);
                afterDialogActionActive = false;
                DialogText.textCounter++;
            }
            case 11 -> {
                if (GUIComponents.goldTable == null) {
                    GUIComponents.addGoldTable("75k");
                }

                else if(GUIComponents.goldTable.getY() == GameScreen.screenHeight) {
                    GUIComponents.removeGoldTable();
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    Phase.addPhase(0);
                }
            }
            case 12 -> {
                ItemsList.unlockAirDefs(9);
                afterDialogActionActive = false;
                DialogText.textCounter++;
            }
            case 13 -> {
                if (GUIComponents.goldTable == null) {
                    GUIComponents.addGoldTable("100k");
                }

                else if(GUIComponents.goldTable.getY() == GameScreen.screenHeight) {
                    GUIComponents.removeGoldTable();
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    Phase.addPhase(0);
                }
            }
            case 14 -> {
                if(counter == -1) counter = 1;
                if (!GUIComponents.buildingButtonJustPressed) GUIComponents.buildingButtonJustPressed = true;
                if (!GUIComponents.airDefButtonJustPressed) GUIComponents.airDefButtonJustPressed = true;

                if (GUIComponents.movingButton == null && counter <= 3) {
                    ImageButton button = new ImageButton((new Image(ItemsList.getAirDef("OkoHora"+counter).getTexture()).getDrawable()));
                    button.setName(ItemsList.getAirDef("OkoHora"+counter).getName());
                    GUIComponents.addMovingButton(button,
                            ItemsList.getAirDef("OkoHora"+counter).getCircleTexture(),
                            ItemsList.getAirDef("OkoHora"+counter).getCircleHitbox());
                    counter++;

                } else if (counter == 4 && GUIComponents.movingButton == null) {
                    counter = -1;

                    afterDialogActionActive = false;

                    DialogText.textCounter++;

                    ItemsList.unlockAirDefs(11);
                }
            }
            case 15 -> {
                if (GUIComponents.goldTable == null) {
                    GUIComponents.addGoldTable("150k");
                }

                else if(GUIComponents.goldTable.getY() == GameScreen.screenHeight) {
                    GUIComponents.removeGoldTable();
                    afterDialogActionActive = false;
                    DialogText.textCounter++;
                    Phase.addPhase(0);
                }
            }
            case 16 -> {
                ItemsList.unlockAirDefs(13);
                afterDialogActionActive = false;
                DialogText.textCounter++;
            }
            case 19 -> {
                GameMusic.removeSound();
                GameDialog.removeSound();
                GameScreen.win = true;
                GameScreen.game.setScreen(new FailScreen(GameScreen.game));
            }

            default -> {
                afterDialogActionActive = false;
                DialogText.textCounter++;
            }
        }

    }
}
