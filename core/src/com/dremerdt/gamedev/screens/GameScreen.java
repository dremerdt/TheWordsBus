package com.dremerdt.gamedev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.dremerdt.gamedev.gameworld.GameRenderer;
import com.dremerdt.gamedev.gameworld.GameWorld;
import com.dremerdt.gamedev.helpers.AssetsLoader;
import com.dremerdt.gamedev.helpers.InputHandler;
import com.dremerdt.gamedev.helpers.WordsHelper;
import com.dremerdt.gamedev.utils.Constants;
import com.dremerdt.gamedev.utils.FontFactory;


public class GameScreen implements Screen {

    private GameWorld mWorld;
    private GameRenderer mRenderer;

    public GameScreen() {
        mWorld = new GameWorld();
        Gdx.input.setInputProcessor(new InputHandler(mWorld));
        Gdx.input.setCatchBackKey(true);
        mRenderer = new GameRenderer(mWorld);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.app.log("FPS: ", String.valueOf(1/delta));
        mWorld.update(delta);
        mRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
