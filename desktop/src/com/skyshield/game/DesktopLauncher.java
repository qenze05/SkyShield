package com.skyshield.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.skyshield.game.SkyShield;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("SkyShield");
		config.setWindowedMode(1280, 693);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new SkyShield(), config);
	}
}
