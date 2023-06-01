package com.skyshield.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.skyshield.game.gameLogic.entities.AirDefence;
import com.skyshield.game.gui.shop.ShopBackground;
import com.skyshield.game.gui.shop.ShopScrollBar;
import com.skyshield.game.screens.GameScreen;
import com.skyshield.game.utils.CountryTerritory;


public class GUIComponents {

    private static ShopBackground shopBackground = new ShopBackground();
    private static ShopScrollBar shopScrollBar = new ShopScrollBar();
    private static Table buttonsTable = new Table();
    private static Skin skin;
    public static ImageButton movingButton;
    public static TextButton zoomInButton, zoomOutButton, shopButton;
    public static int animationFrame = 0;

    public static void addStageInputListener() {
        GameScreen.stage.addListener(new InputListener() {
            @Override
            public boolean keyDown (InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE) {
                    removeShop();
                    shopButton.setChecked(false);
                }
                return true;
            }
        });
    }

    public static void addButtonsTable() {
        buttonsTable = new Table();
        buttonsTable.setBounds(0, GameScreen.screenHeight - (float) GameScreen.screenHeight / 8,
                (float) GameScreen.screenWidth / 2, (float) GameScreen.screenHeight / 8);

        GameScreen.stage.addActor(buttonsTable);

        zoomInButton = new TextButton("+", skin);
        zoomOutButton = new TextButton("-", skin);
        shopButton = new TextButton("Shop", skin);

        buttonsTable.add(shopButton).left().top().expand();
        buttonsTable.add(zoomInButton).left().top().expand();
        buttonsTable.add(zoomOutButton).left().top().expand();

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

    public static void addMovingButton(ImageButton button) {
        movingButton = new ImageButton(button.getImage().getDrawable());
        movingButton.setName(button.getName());
        GameScreen.stage.addActor(movingButton);

        movingButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    if(!CountryTerritory.isInsideTerritory(Gdx.input.getX(), Gdx.input.getY())) {
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

        if(animationFrame<=30) {
            territorySprite.setColor(1f, 0, 0, animationFrame/90f);
            animationFrame++;
        }else if(animationFrame<60){
            territorySprite.setColor(1f, 0, 0, (60-animationFrame)/90f);
            animationFrame++;
        }else if(animationFrame==60) {
            territorySprite.setColor(1f, 0, 0, 0f);
            animationFrame = 0;
        }



        GameScreen.game.batch.begin();
        territorySprite.draw(GameScreen.game.batch);
        GameScreen.game.batch.end();

    }

    public static void moveMovingButton() {
        movingButton.setPosition(Gdx.input.getX() - 32,GameScreen.screenHeight - Gdx.input.getY() - 16);
        shopBackground.setVisible(false);
        shopScrollBar.setVisible(false);
        GameScreen.stage.draw();
    }

    public static void removeMovingButton() {
        movingButton.remove();
        movingButton = null;

        shopBackground.setVisible(true);
        shopScrollBar.setVisible(true);
    }

    private static void placeWeapon() {

        float width = GameScreen.screenWidth;
        float height = GameScreen.screenHeight;

        float[] curr = new float[]{movingButton.getX(), movingButton.getY()};

        float xShift = (Camera.cameraPos.x- (width / 2));
        float yShift = (Camera.cameraPos.y- (height / 2));

        System.out.println(Camera.cameraPos.x);
        float xPos = 32 + xShift + (width / 2) + (width / 2) * (curr[0] - width / 2) * Camera.camera.zoom / (width / 2);
        float yPos = 16 + yShift + (height / 2) + (height / 2) * (curr[1] - height / 2) * Camera.camera.zoom / (height / 2);

        float[] pos = new float[]{xPos, yPos};
        AirDefence.addAirDef(pos, movingButton.getName());
    }
}
