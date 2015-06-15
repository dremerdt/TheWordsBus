package com.dremerdt.gamedev;

import com.badlogic.gdx.Game;
import com.dremerdt.gamedev.helpers.AssetsLoader;
import com.dremerdt.gamedev.screens.GameScreen;
import com.dremerdt.gamedev.screens.SplashScreen;
import com.dremerdt.gamedev.utils.FontFactory;

public class TheWordBus extends Game {
    @Override
    public void create() {
        AssetsLoader.load();
        //setScreen(new GameScreen());
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        //AssetsLoader.dispose();
        //FontFactory.getInstance().dispose();
    }
}
