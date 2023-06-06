package com.skyshield.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gameObjects.airDefence.AirDef;
import com.skyshield.game.gameObjects.buildings.City;
import com.skyshield.game.gui.camera.Camera;
import com.skyshield.game.gui.shop.ShopBackground;
import com.skyshield.game.gui.shop.ShopScrollBar;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.CountryTerritory;


public class GUIComponents {

    private static ShopBackground shopBackground = new ShopBackground();
    private static ShopScrollBar shopScrollBar = new ShopScrollBar();
    private static Skin skin;
    public static ImageButton movingButton;
    public static Sprite movingButtonCircle;
    public static TextButton zoomInButton, zoomOutButton, shopButton, gameSpeedButton;
    public static int animationFrame = 0;
    private static long popUpTimer;
    private static Texture popUpTexture;
    public static Actor popUpImage;
    public static boolean buttonJustPressed = false;
    public static Table sellTable;

    public static void addStageInputListener() {
        GameScreen.stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    removeShop();
                    shopButton.setChecked(false);
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

    public static void addButtonsTable() {
        Table buttonsTable = new Table();
        buttonsTable.setBounds(300, GameScreen.screenHeight - (float) GameScreen.screenHeight / 8,
                (float) GameScreen.screenWidth / 2, (float) GameScreen.screenHeight / 8);

        GameScreen.stage.addActor(buttonsTable);

        zoomInButton = new TextButton("+", skin);
        zoomOutButton = new TextButton("-", skin);
        shopButton = new TextButton("Shop", skin);
        gameSpeedButton = new TextButton("Speed: 1x", skin);

        buttonsTable.add(shopButton).left().top().expand();
        buttonsTable.add(zoomInButton).left().top().expand();
        buttonsTable.add(zoomOutButton).left().top().expand();
        buttonsTable.add(gameSpeedButton).left().top().expand();

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
                    if (!CountryTerritory.isInsideTerritory(coords[0], coords[1])) {
                        return false;
                    }
                    placeWeapon();
                }
                removeMovingButton();
                return true;
            }
        });
    }

    public static void showAvailableArea() {

        Texture territory = CountryTerritory.getTerritoryTexture();

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
        pos[1] += movingButton.getHeight() * Camera.camera.zoom  / 2;
        AirDefence.addAirDef(pos, movingButton.getName());
        buttonJustPressed = true;
    }

    public static void addPopUpMenu(int x, int y) {

        if(popUpImage != null) {
            popUpImage.remove();
            popUpImage = null;
        }
        if(popUpTimer == 0) {

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
        if(TimeUtils.millis() - popUpTimer > 1000 && !popUpImage.isVisible()) {
            popUpImage.setVisible(true);
        }
    }

    public static void removePopUpMenu() {
        if(popUpImage != null){
            popUpTimer = 0;
            popUpImage.remove();
            popUpImage = null;
        }
    }

    public static void addSellAirDefMenu(AirDef airDef) {

        if(buttonJustPressed) return;

        TextElements.addSellValueText(airDef.getPrice()/2);

        Texture sellButtonTexture = new Texture(Gdx.files.internal("sellButton.png"));
        Texture cancelButtonTexture = new Texture(Gdx.files.internal("cancelButton.png"));
        Texture bgTexture = new Texture(Gdx.files.internal("sellAirDefBg.png"));

        sellTable = new Table();
        sellTable.setBounds((float) GameScreen.screenWidth /2 - (float) bgTexture.getWidth() /2,
                (float) GameScreen.screenHeight /2 - (float) bgTexture.getHeight() /2,
                bgTexture.getWidth(), bgTexture.getHeight());

        GameScreen.stage.addActor(sellTable);

        ImageButton sellButton = new ImageButton(new Image(sellButtonTexture).getDrawable());
        ImageButton cancelButton = new ImageButton(new Image(cancelButtonTexture).getDrawable());

        Table buttonsTable = new Table();
        buttonsTable.setSize(sellButton.getWidth(), sellButton.getHeight()*2);
        buttonsTable.add(sellButton);
        buttonsTable.row();
        buttonsTable.add(cancelButton);

        sellTable.setBackground(new Image(bgTexture).getDrawable());
        sellTable.add(buttonsTable).bottom().expand();

        sellButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AirDefence.removeAirDef(airDef);
                City.sellItem((float) airDef.getPrice() /2);
                TextElements.deleteSellValue();
                sellTable.remove();
                sellTable = null;
                buttonJustPressed = true;
                return true;
            }
        });

        cancelButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellTable.remove();
                TextElements.deleteSellValue();
                sellTable = null;
                buttonJustPressed = true;
                return true;
            }
        });
    }
}
