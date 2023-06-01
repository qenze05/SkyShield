package com.skyshield.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skyshield.game.screens.MainMenu;

public class SkyShield extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 693;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
