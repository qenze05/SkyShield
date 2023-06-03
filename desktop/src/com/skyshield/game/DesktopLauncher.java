package com.skyshield.game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("SkyShield");
		config.setWindowedMode(1280, 693);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new SkyShield(), config);
	}
}