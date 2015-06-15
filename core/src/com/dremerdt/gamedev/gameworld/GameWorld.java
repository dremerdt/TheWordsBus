package com.dremerdt.gamedev.gameworld;


import com.badlogic.gdx.Gdx;
import com.dremerdt.gamedev.gameobjects.Bus;
import com.dremerdt.gamedev.helpers.ScrollHandler;
import com.dremerdt.gamedev.helpers.AssetsLoader;
import com.dremerdt.gamedev.helpers.InputHandler;
import com.dremerdt.gamedev.helpers.WordsHelper;
import com.dremerdt.gamedev.ui.SimpleButton;
import com.dremerdt.gamedev.utils.Constants;
import com.dremerdt.gamedev.utils.FontFactory;

import java.io.IOException;
import java.util.ArrayList;

public class GameWorld {

    private Bus mBus;
    private ScrollHandler mScrollHandler;

    private boolean mIsAlive = true;
    private boolean mIsPause = false;
    private boolean mSoundOn = true;
    private int mScore = 0;
    private int mCurrentTheme = 0;

    private GameState mCurrentGameState;



    public enum GameState {
        MENU, LEVEL_SELECTION, READY, RUNNING, GAME_OVER
    }

    public GameWorld() {

        mCurrentGameState = GameState.MENU;

        mBus = new Bus(200, 800, 60, 120);
        //WORDS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try {
            WordsHelper.getInstance().initialize(Constants.PATH_EQUIPMENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FontFactory.getInstance().initialize();
        mScrollHandler = new ScrollHandler();
        mSoundOn = AssetsLoader.getSoundStatus();
    }

    public void update(float delta) {
        if (mCurrentGameState == GameState.RUNNING)
            updateRunning(delta);
        else if (mCurrentGameState == GameState.READY)
            updateReady(delta);
    }
    private void updateReady(float delta) {
        //mBus.updateReady(delta);
        mScrollHandler.updateReady(delta);
    }
    private void updateRunning(float delta) {
        if (mIsAlive)
            mBus.update(delta);
        mScrollHandler.update(delta);
        //WINNER-------------------------------------
        if ((WordsHelper.getInstance().count() <= 4) && !isGameOver()) {
            mScrollHandler.stop();
            if (mSoundOn)
                AssetsLoader.sWinSound.play();
            setScore();
            resetScore();
            mCurrentGameState = GameState.GAME_OVER;
        }

        if (!mScrollHandler.getWall().isCollied()) {
            if (mScrollHandler.collides(mBus) == 1) {
                mScore-=2;
                if (mScore >= 0 && mSoundOn)
                    AssetsLoader.sFailWay.play();
            } else if (mScrollHandler.collides(mBus) == 0) {
                mScore++;
                if (mSoundOn)
                    AssetsLoader.sCorrectWay.play();
            }
            //LOOSER---------------------
            if (mIsAlive && (mScore < 0)) {
                // Clean up on game over
                mScrollHandler.stop();
                if (mSoundOn)
                    AssetsLoader.sLoosingSound.play();
                mIsAlive = false;
                mScore = 0;
                mCurrentGameState = GameState.GAME_OVER;
            }
        }

    }

    public Bus getBus() {
        return mBus;
    }

    public ScrollHandler getScrollHandler() {
        return mScrollHandler;
    }

    public int getScore() {return mScore;}

    public void restart() {
        mIsAlive = true;
        mIsPause = false;
        mCurrentGameState = GameState.READY;
        mScrollHandler.onRestart();
        mBus.onRestart();
        mScore = 0;
    }

    public boolean isReady() {
        return mCurrentGameState == GameState.READY;
    }

    public boolean isGameOver() {
        return mCurrentGameState == GameState.GAME_OVER;
    }

    public boolean isMenu() {return mCurrentGameState == GameState.MENU;}

    public boolean isRunning() {return mCurrentGameState == GameState.RUNNING;}

    public boolean isLevelSelection() {return mCurrentGameState == GameState.LEVEL_SELECTION;}

    public boolean isPause() {return mIsPause;}

    public void start() {
        mCurrentGameState = GameState.RUNNING;
    }

    public void ready() {mCurrentGameState = GameState.READY;}

    public void levelSelection() {mCurrentGameState = GameState.LEVEL_SELECTION;}

    public void menu() {mCurrentGameState = GameState.MENU;}

    public void stop() {
        mIsPause = true;
        mScrollHandler.stop();
    }

    public void play() { mScrollHandler.play(); }

    public void soundTouched() {
        mSoundOn = !mSoundOn;
        AssetsLoader.setSoundStatus(mSoundOn);
    }

    public void setCurrentTheme(int theme) {mCurrentTheme = theme;}

    private void setScore() {
        switch (mCurrentTheme) {
            case 1: AssetsLoader.setScore("equipmentScore", mScore);
                break;
            case 2: AssetsLoader.setScore("hobbyScore", mScore);
                break;
            case 3: AssetsLoader.setScore("mediaScore", mScore);
                break;
            case 4: AssetsLoader.setScore("cosmosScore", mScore);
                break;
            case 5: AssetsLoader.setScore("workScore", mScore);
                break;
            default:break;
        }
    }

    private void resetScore() {
        ArrayList<SimpleButton> mListOfThemes = (ArrayList<SimpleButton>)((InputHandler) Gdx.input.getInputProcessor())
                .getListOfThemes();
        for (int i = 1; i < mListOfThemes.size(); i++) {
            switch (i) {
                case 1: mListOfThemes.get(i).setScore(AssetsLoader.getScore("equipmentScore"));
                    break;
                case 2: mListOfThemes.get(i).setScore(AssetsLoader.getScore("hobbyScore"));
                    break;
                case 3: mListOfThemes.get(i).setScore(AssetsLoader.getScore("mediaScore"));
                    break;
                case 4: mListOfThemes.get(i).setScore(AssetsLoader.getScore("cosmosScore"));
                    break;
                case 5: mListOfThemes.get(i).setScore(AssetsLoader.getScore("workScore"));
                    break;

            }
        }
    }
}
