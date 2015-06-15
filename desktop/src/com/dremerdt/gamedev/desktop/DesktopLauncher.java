package com.dremerdt.gamedev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dremerdt.gamedev.TheWordBus;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "The Word Bus";
        config.width = 480;
        config.height = 800;
        config.resizable = false;
		new LwjglApplication(new TheWordBus(), config);
	}
}
