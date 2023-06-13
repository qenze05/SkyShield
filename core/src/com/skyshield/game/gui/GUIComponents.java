package com.skyshield.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameObjects.buildings.City;
import com.skyshield.game.gui.camera.Camera;
import com.skyshield.game.gui.dialog.DialogActions;
import com.skyshield.game.gui.dialog.DialogText;
import com.skyshield.game.gui.dialog.DialogTimer;
import com.skyshield.game.gui.dialog.DialogWindow;
import com.skyshield.game.gui.shop.ShopBackground;
import com.skyshield.game.gui.shop.ShopScrollBar;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.CountryTerritory;


public class GUIComponents {

    public static ShopBackground shopBackground = new ShopBackground();
    public static ShopScrollBar shopScrollBar = new ShopScrollBar();
    private static Skin skin;
    public static ImageButton movingButton, okButton, skipButton;
    public static Sprite movingButtonCircle;
    public static TextButton zoomInButton, zoomOutButton, shopButton, gameSpeedButton;
    public static int animationFrame = 0;
    private static long popUpTimer;
    private static Texture popUpTexture;
    public static Actor popUpImage;
    public static boolean airDefButtonJustPressed = false;
    public static boolean buildingButtonJustPressed = false;
    public static Table sellTable;
    public static Table repairTable;
    public static DialogWindow dialogWindow;
    public static DialogText dialogText;
    public static boolean dialogWindowIsClosing;

    public static Table goldTable;

    public static void addGoldTable(String amount) {
        Texture bg = new Texture(Gdx.files.internal("gold++/"+amount+".png"));
        goldTable = new Table();
        goldTable.setBounds(535, GameScreen.screenHeight, bg.getWidth(), bg.getHeight());
        goldTable.setBackground(new Image(bg).getDrawable());
        goldTable.addAction(Actions.sequence(Actions.moveTo(535, GameScreen.screenHeight-goldTable.getHeight(), 1f, Interpolation.sine),
                Actions.moveBy(0, 0, 1f),
                Actions.moveTo(535, GameScreen.screenHeight, 1f, Interpolation.sine)));
        GameScreen.stage.addActor(goldTable);
    }

    public static void removeGoldTable() {
        goldTable.remove();
        goldTable = null;
    }

    public static void addStageInputListener() {
        GameScreen.stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    removeShop();
                    shopButton.setChecked(false);
                }
                if (keycode == Input.Keys.SPACE) {
                    if(skipButton != null) {
                        skipButton.remove();
                        skipButton = null;
                        dialogText.skip();
                        addOkButton();
                    }else if(okButton != null) {
                        okButton.remove();
                        dialogText.remove();
                        DialogTimer.start = 0;
                        DialogTimer.textStart = 0;
                        dialogText = null;
                        okButton = null;
                        hideDialogTable();
                        DialogActions.afterDialogActionActive = true;
                    }
                }
                return true;
            }
        });
    }

    public static void addTimeTable() {

        Texture timeBackground = new Texture(Gdx.files.internal("time.png"));
        Table timeTable = new Table();
        timeTable.setBounds(0, GameScreen.screenHeight - timeBackground.getHeight(),
                timeBackground.getWidth(), timeBackground.getHeight());

        timeTable.add(new Image(timeBackground));

        GameScreen.stage.addActor(timeTable);

    }

    public static void addDialogTable() {
        dialogWindowIsClosing = false;
        dialogWindow = new DialogWindow();
        GameScreen.stage.addActor(dialogWindow);
    }

    public static void addDialogText(String text) {
        if (dialogText != null) dialogText = null;
        dialogText = new DialogText(text);
    }

    public static void addSkipButton() {
        if(dialogWindow != null) {
            skipButton = new ImageButton(new Image(new Texture(Gdx.files.internal("dialog-skip.png"))).getDrawable());
            dialogWindow.add(skipButton).right().bottom().expand();
            skipButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    skipButton.remove();
                    skipButton = null;
                    dialogText.skip();
                    addOkButton();
                    return true;
                }
            });

        }
    }

    public static void addOkButton() {
        if(dialogWindow != null) {
            okButton = new ImageButton(new Image(new Texture(Gdx.files.internal("dialog-ok.png"))).getDrawable());
            dialogWindow.add(okButton).right().bottom().expand();
            okButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    okButton.remove();
                    dialogText.remove();
                    DialogTimer.start = 0;
                    DialogTimer.textStart = 0;
                    dialogText = null;
                    okButton = null;
                    hideDialogTable();
                    DialogActions.afterDialogActionActive = true;
                    return true;
                }
            });

        }
    }
    public static void updateDialogText() {
        if(dialogText == null) return;
        dialogText.update(1/60f);
    }

    public static void hideDialogTable() {
        dialogWindowIsClosing = true;
        dialogWindow.addAction(Actions.moveTo(140, GameScreen.screenHeight + dialogWindow.getHeight(), 1f, Interpolation.sineOut));
    }

    public static void addButtonsTable() {
        Table buttonsTable = new Table();
        buttonsTable.setBounds(0, GameScreen.screenHeight - (float) GameScreen.screenHeight / 1.5f,
                (float) GameScreen.screenWidth / 2, (float) GameScreen.screenHeight / 2);

        GameScreen.stage.addActor(buttonsTable);

        zoomInButton = new TextButton("+", skin);
        zoomOutButton = new TextButton("-", skin);
        shopButton = new TextButton("Shop", skin);
        gameSpeedButton = new TextButton("Speed: 1x", skin);

        buttonsTable.add(shopButton).left().bottom().expandX().size(100, 50);
        buttonsTable.row();
        buttonsTable.add(zoomInButton).left().bottom().expandX().size(100, 50);
        buttonsTable.row();
        buttonsTable.add(zoomOutButton).left().bottom().expandX().size(100, 50);
        buttonsTable.row();
        buttonsTable.add(gameSpeedButton).left().bottom().expandX().size(150, 50);

        zoomInButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Camera.camera.zoom /= 1.1f;
            }
        });

        zoomOutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (Camera.camera.zoom != 1) {
                    Camera.camera.zoom *= 1.1f;
                    if (Camera.cameraPos.x > Camera.getMaxCameraX()) Camera.cameraPos.x = Camera.getMaxCameraX();
                    if (Camera.cameraPos.y > Camera.getMaxCameraY()) Camera.cameraPos.y = Camera.getMaxCameraY();
                    if (Camera.cameraPos.x < Camera.getMinCameraX()) Camera.cameraPos.x = Camera.getMinCameraX();
                    if (Camera.cameraPos.y < Camera.getMinCameraY()) Camera.cameraPos.y = Camera.getMinCameraY();
                    Camera.camera.position.lerp(Camera.cameraPos, 1);
                } else Camera.resetCameraPos();
            }
        });

        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (shopButton.isChecked()) {
                    GUIComponents.addShop();
                } else {
                    GUIComponents.removeShop();
                }
            }
        });

        gameSpeedButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.changeGameSpeed();
                gameSpeedButton.setText("Speed: " + GameScreen.gameSpeed + "x");
                return true;
            }
        });
    }

    public static void setSkin(String path) {
        skin = new Skin(Gdx.files.internal(path));
    }

    public static void addShop() {
        shopBackground = new ShopBackground();
        shopScrollBar = new ShopScrollBar();
        GameScreen.stage.addActor(shopBackground);
        GameScreen.stage.addActor(shopScrollBar);
    }

    public static void removeShop() {
        shopBackground.remove();
        shopScrollBar.remove();
    }

    public static void addMovingButton(ImageButton button, Texture circleTexture, Rectangle circleSize) {

        movingButton = new ImageButton(button.getImage().getDrawable());
        movingButton.setSize(40, 40);
        movingButton.setName(button.getName());

        movingButtonCircle = new Sprite(circleTexture);
        movingButtonCircle.setColor(1f, 1f, 1f, 0.5f);
        movingButtonCircle.setSize(circleSize.getWidth(), circleSize.getHeight());

        GameScreen.stage.addActor(movingButton);

        movingButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    float[] coords = Camera.getRelativeCoords(movingButton.getX() + movingButton.getWidth() / 2,
                            movingButton.getY() + movingButton.getHeight() / 2);
                    if (!CountryTerritory.isInsideTerritory(coords[0], coords[1], movingButton.getName())) {
                        return false;
                    }
                    placeWeapon();
                }
                removeMovingButton();
                return true;
            }
        });
    }

    public static void showAvailableArea(boolean sea) {

        Texture territory = CountryTerritory.getTerritoryTexture(sea);

        Sprite territorySprite = new Sprite(territory);

        if (animationFrame <= 30) {
            territorySprite.setColor(1f, 0, 0, animationFrame / 90f);
            animationFrame++;
        } else if (animationFrame < 60) {
            territorySprite.setColor(1f, 0, 0, (60 - animationFrame) / 90f);
            animationFrame++;
        } else if (animationFrame == 60) {
            territorySprite.setColor(1f, 0, 0, 0f);
            animationFrame = 0;
        }

        GameScreen.game.batch.begin();
        territorySprite.draw(GameScreen.game.batch);
        GameScreen.game.batch.end();
    }

    public static void moveMovingButton() {

        movingButton.setPosition(Gdx.input.getX() - movingButton.getWidth() / 2,
                GameScreen.screenHeight - Gdx.input.getY() - movingButton.getHeight() / 2);

        float[] circlePos = Camera.getRelativeCoords(Gdx.input.getX(),
                GameScreen.screenHeight - Gdx.input.getY());
        movingButtonCircle.setPosition(circlePos[0] - movingButtonCircle.getWidth() / 2,
                circlePos[1] - movingButtonCircle.getHeight() / 2);

        shopBackground.setVisible(false);
        shopScrollBar.setVisible(false);

        GameScreen.stage.draw();

        GameScreen.game.batch.begin();
        movingButtonCircle.draw(GameScreen.game.batch);
        GameScreen.game.batch.end();
    }

    public static void removeMovingButton() {
        movingButton.remove();
        movingButton = null;

        shopBackground.setVisible(true);
        shopScrollBar.setVisible(true);
    }

    private static void placeWeapon() {
        float[] pos = Camera.getRelativeCoords(movingButton.getX(), movingButton.getY());
        pos[0] += movingButton.getWidth() * Camera.camera.zoom / 2;
        pos[1] += movingButton.getHeight() * Camera.camera.zoom / 2;
        AirDefence.addAirDef(pos, movingButton.getName());
        airDefButtonJustPressed = true;
    }

    public static void addPopUpMenu(int x, int y) {

        if (popUpImage != null) {
            popUpImage.remove();
            popUpImage = null;
        }
        if (popUpTimer == 0) {

            popUpTimer = TimeUtils.millis();

            popUpTexture = new Texture(Gdx.files.internal("popup.png"));
            popUpImage = new Image(popUpTexture);
            popUpImage.setPosition(x, y);
            popUpImage.setVisible(false);
            popUpImage.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    showPopUpMenu();
                    return true;
                }
            });

            GameScreen.stage.addActor(popUpImage);
        }
    }

    public static void showPopUpMenu() {
        if (TimeUtils.millis() - popUpTimer > 1000 && !popUpImage.isVisible()) {
            popUpImage.setVisible(true);
        }
    }

    public static void removePopUpMenu() {
        if (popUpImage != null) {
            popUpTimer = 0;
            popUpImage.remove();
            popUpImage = null;
        }
    }

    public static void addSellAirDefMenu(AirDef airDef) {

        if (airDefButtonJustPressed) return;

        TextElements.addSellValueText(airDef.getPrice() / 2);

        Texture sellButtonTexture = new Texture(Gdx.files.internal("sellButton.png"));
        Texture cancelButtonTexture = new Texture(Gdx.files.internal("cancelButton.png"));
        Texture bgTexture = new Texture(Gdx.files.internal("sellAirDefBg.png"));

        sellTable = new Table();
        sellTable.setBounds((float) GameScreen.screenWidth / 2 - (float) bgTexture.getWidth() / 2,
                (float) GameScreen.screenHeight / 2 - (float) bgTexture.getHeight() / 2,
                bgTexture.getWidth(), bgTexture.getHeight());

        GameScreen.stage.addActor(sellTable);

        ImageButton sellButton = new ImageButton(new Image(sellButtonTexture).getDrawable());
        ImageButton cancelButton = new ImageButton(new Image(cancelButtonTexture).getDrawable());

        Table buttonsTable = new Table();
        buttonsTable.setSize(sellButton.getWidth(), sellButton.getHeight() * 2);
        buttonsTable.add(sellButton);
        buttonsTable.row();
        buttonsTable.add(cancelButton);

        sellTable.setBackground(new Image(bgTexture).getDrawable());
        sellTable.add(buttonsTable).bottom().expand();

        sellButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AirDefence.removeAirDef(airDef);
                City.sellItem((float) airDef.getPrice() / 2);
                TextElements.deleteSellValue();
                sellTable.remove();
                sellTable = null;
                airDefButtonJustPressed = true;
                return true;
            }
        });

        cancelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TextElements.deleteSellValue();
                sellTable.remove();
                sellTable = null;
                airDefButtonJustPressed = true;
                return true;
            }
        });
    }

    public static void addRepairBuildingMenu(String name, String hp, int price) {

        if (airDefButtonJustPressed) return;

        TextElements.addRepairValueText(price);
        TextElements.addHpValueText(hp);

        Texture repairButtonTexture = new Texture(Gdx.files.internal("repairButton.png"));
        Texture cancelButtonTexture = new Texture(Gdx.files.internal("cancelButton.png"));
        Texture bgTexture = new Texture(Gdx.files.internal("repairBuildingBg.png"));

        repairTable = new Table();
        repairTable.setBounds((float) GameScreen.screenWidth / 2 - (float) bgTexture.getWidth() / 2,
                (float) GameScreen.screenHeight / 2 - (float) bgTexture.getHeight() / 2,
                bgTexture.getWidth(), bgTexture.getHeight());

        GameScreen.stage.addActor(repairTable);

        ImageButton repairButton = new ImageButton(new Image(repairButtonTexture).getDrawable());
        ImageButton cancelButton = new ImageButton(new Image(cancelButtonTexture).getDrawable());

        Table buttonsTable = new Table();
        buttonsTable.setSize(repairButton.getWidth(), repairButton.getHeight() * 2);
        buttonsTable.add(repairButton);
        buttonsTable.row();
        buttonsTable.add(cancelButton);

        repairTable.setBackground(new Image(bgTexture).getDrawable());
        repairTable.add(buttonsTable).bottom().expand();

        repairButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TextElements.deleteRepairValue();
                TextElements.deleteHpValue();
                repairTable.remove();
                repairTable = null;
                airDefButtonJustPressed = true;
                return true;
            }
        });

        cancelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TextElements.deleteRepairValue();
                TextElements.deleteHpValue();
                repairTable.remove();
                repairTable = null;
                airDefButtonJustPressed = true;
                return true;
            }
        });
    }
}
