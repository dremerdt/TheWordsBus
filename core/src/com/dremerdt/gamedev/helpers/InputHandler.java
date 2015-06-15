package com.dremerdt.gamedev.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.dremerdt.gamedev.gameobjects.Bus;
import com.dremerdt.gamedev.gameworld.GameWorld;
import com.dremerdt.gamedev.ui.SimpleButton;
import com.dremerdt.gamedev.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements InputProcessor {
    private Bus mBus;
    private GameWorld mGameWorld;

    private int mOldPosX;

    private List<SimpleButton> mMenuButtons;
    private List<SimpleButton> mListOfThemes;

    private SimpleButton mPlayButton, mExitButton, mSoundButton;

    private float mScaleFactorX;
    private float mScaleFactorY;

    public InputHandler(GameWorld gameWorld) {
        mScaleFactorX = Constants.SCALE_RATIO_WIDTH;
        mScaleFactorY = Constants.SCALE_RATIO_HEIGHT;
        Gdx.app.log("TAG", Constants.SCALE_RATIO_HEIGHT + "");
        mMenuButtons = new ArrayList<SimpleButton>();
        mPlayButton = new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                Constants.SCREEN_HEIGHT/2 - AssetsLoader.sSignPressed.getRegionHeight()/2, 240, 80,
                AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Play",0);
        mSoundButton = new SimpleButton(Constants.SCREEN_WIDTH - 80, 5, 64,64, AssetsLoader.sSoundOn, AssetsLoader.sSoundOff,"",0);
        mExitButton = new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                mPlayButton.getTileY() + 20, 240, 80, AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Exit",0);
        mMenuButtons.add(mPlayButton);
        mMenuButtons.add(mExitButton);
        createListOfThemes();
        mGameWorld = gameWorld;
        mBus = gameWorld.getBus();
    }
    @Override
    public boolean keyDown(int keycode) {
        if (mGameWorld.isRunning() || mGameWorld.isReady() || mGameWorld.isGameOver()) {
            if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                mGameWorld.stop();
                mGameWorld.levelSelection();
                return true;
            }
        } else if (mGameWorld.isLevelSelection()) {
            if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                mGameWorld.menu();
                return true;
            }
        } else if (mGameWorld.isMenu()) {
            if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                Gdx.app.exit();
                return true;
            }
        }
        if (mGameWorld.isRunning()) {
            if (keycode == Input.Keys.LEFT)
                mBus.changeSide(true);
            else if (keycode == Input.Keys.RIGHT)
                mBus.changeSide(false);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        mOldPosX = screenX;
        //System.out.println(screenX + " " + screenY);
        mSoundButton.isTouchDown(screenX, screenY);

        if (mGameWorld.isMenu()) {
            mPlayButton.isTouchDown(screenX, screenY);
            mExitButton.isTouchDown(screenX, screenY);
        } else if (mGameWorld.isLevelSelection()) {
            for (SimpleButton sb : mListOfThemes)
                sb.isTouchDown(screenX, screenY);
        } else if (mGameWorld.isReady()) {
            mGameWorld.start();
        } else if (mGameWorld.isGameOver())
            mGameWorld.levelSelection();
        /*if (screenX < Constants.SCREEN_WIDTH/2)
            mBus.changeSide(true);
        else
            mBus.changeSide(false);*/
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (mSoundButton.isTouchUp(screenX,screenY)) {
            mSoundButton.switchTextures();
            mGameWorld.soundTouched();
        }

        if (mGameWorld.isMenu()) {
            if (mPlayButton.isTouchUp(screenX, screenY)) {
                mGameWorld.levelSelection();
                return true;
            } else if (mExitButton.isTouchUp(screenX, screenY)) {
                Gdx.app.exit();
            }
        } else if (mGameWorld.isLevelSelection()) {
            for (int i = 0; i < mListOfThemes.size(); i++) {
                if (mListOfThemes.get(i).isTouchUp(screenX, screenY)) {
                    try {

                    switch (i) {
                        case 0:
                            if (mGameWorld.isPause()) {
                                mGameWorld.ready();
                                mGameWorld.play();
                            }
                            break;
                        case 1:
                            WordsHelper.getInstance().initialize(Constants.PATH_EQUIPMENT);
                            mGameWorld.setCurrentTheme(1);
                            mGameWorld.ready();
                            mGameWorld.getScrollHandler().reInit();
                            mGameWorld.restart();
                            break;
                        case 2:
                            WordsHelper.getInstance().initialize(Constants.PATH_HOBBY);
                            mGameWorld.setCurrentTheme(2);
                            mGameWorld.ready();
                            mGameWorld.getScrollHandler().reInit();
                            mGameWorld.restart();
                            break;
                        case 3:
                            WordsHelper.getInstance().initialize(Constants.PATH_MEDIA);
                            mGameWorld.setCurrentTheme(3);
                            mGameWorld.ready();
                            mGameWorld.getScrollHandler().reInit();
                            mGameWorld.restart();
                            break;
                        case 4:
                            WordsHelper.getInstance().initialize(Constants.PATH_SPACE);
                            mGameWorld.setCurrentTheme(4);
                            mGameWorld.ready();
                            mGameWorld.getScrollHandler().reInit();
                            mGameWorld.restart();
                            break;
                        case 5:
                            WordsHelper.getInstance().initialize(Constants.PATH_WORK);
                            mGameWorld.setCurrentTheme(5);
                            mGameWorld.ready();
                            mGameWorld.getScrollHandler().reInit();
                            mGameWorld.restart();
                            break;
                        default:
                            WordsHelper.getInstance().initialize(Constants.PATH_EQUIPMENT);
                            mGameWorld.setCurrentTheme(1);
                            mGameWorld.ready();
                            mGameWorld.getScrollHandler().reInit();
                            mGameWorld.restart();
                    }

                } catch (IOException e) {
                        Gdx.app.log("INPUTHELPER", e.getMessage());
                    }
                }
            }
        } else if (mGameWorld.isRunning()) {
            if (mOldPosX - 50 > screenX)
                mBus.changeSide(true);
            else if (mOldPosX + 50 < screenX)
                mBus.changeSide(false);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    private int scaleX(int screenX) {
        return (int) (screenX / mScaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / mScaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return mMenuButtons;
    }

    public List<SimpleButton> getListOfThemes() {return mListOfThemes;}

    private void createListOfThemes() {
        mListOfThemes = new ArrayList<SimpleButton>();
        mListOfThemes.add(
                new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                        80,240,80,AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Continue",0));
        mListOfThemes.add(
                new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                        mListOfThemes.get(0).getTileY() + 20,
                        240,80,AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Equipment",
                        AssetsLoader.getScore("equipmentScore")));
        mListOfThemes.add(
                new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                        mListOfThemes.get(1).getTileY() + 20,
                        240,80,AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Hobby",
                        AssetsLoader.getScore("hobbyScore")));
        mListOfThemes.add(
                new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                        mListOfThemes.get(2).getTileY() + 20,
                        240,80,AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Media",
                        AssetsLoader.getScore("mediaScore")));
        mListOfThemes.add(
                new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                        mListOfThemes.get(3).getTileY() + 20,
                        240,80,AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Cosmos",
                        AssetsLoader.getScore("cosmosScore")));
        mListOfThemes.add(
                new SimpleButton(Constants.SCREEN_WIDTH/2 - AssetsLoader.sSignPressed.getRegionWidth()/2,
                        mListOfThemes.get(4).getTileY() + 20,
                        240,80,AssetsLoader.sSingUpped, AssetsLoader.sSignPressed, "Work",
                        AssetsLoader.getScore("workScore")));

    }

    public SimpleButton getSoundButton() {return mSoundButton;}
}
