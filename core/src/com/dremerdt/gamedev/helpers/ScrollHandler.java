package com.dremerdt.gamedev.helpers;


import com.badlogic.gdx.Gdx;
import com.dremerdt.gamedev.gameobjects.Bus;
import com.dremerdt.gamedev.gameobjects.Road;
import com.dremerdt.gamedev.gameobjects.RoadSign;
import com.dremerdt.gamedev.utils.Constants;

import java.util.Random;

public class ScrollHandler {
    private Road mFrontRoad;
    private Road mBackRoad;
   // private RoadMarksHelper[] mWalls = new RoadMarksHelper[2];
    private RoadMarksHelper mWalls;

    private RoadSign mPanelTaksedWord;

    private int mIndexTaskWord;

    //private int mLastIndexTaskWord = 0;

    private int mTrueIndexAnswer = 0;



    //public static final int SCROLL_SPEED = 59;
    //public static final int WALL_GAP = 100;

    public ScrollHandler() {
        mFrontRoad = new Road(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                Constants.SCROLL_SPEED);
        mBackRoad = new Road(0, -mFrontRoad.getTileY(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT,
                Constants.SCROLL_SPEED);
        //mWalls[1] = new RoadMarksHelper(-500);
        mPanelTaksedWord = new RoadSign(0,0, Constants.SCREEN_WIDTH, 50/*, mIndexTaskWord*/);
        mWalls = new RoadMarksHelper(-100/*, mTrueIndexAnswer*/);
        //reInit();

    }

    public void reInit() {
        initWords();
        mWalls.setTrueIndex(mTrueIndexAnswer);
        mPanelTaksedWord.setIndexText(mIndexTaskWord);
    }

    private void initWords() {
        Random mRandom = new Random();
        int taskedRandInt;
        do {
            taskedRandInt = mRandom.nextInt(WordsHelper.getInstance().count());
        } while ((taskedRandInt % 2 != 0)/* || (taskedRandInt == mLastIndexTaskWord)*/);
        mIndexTaskWord = taskedRandInt;
        mTrueIndexAnswer = taskedRandInt + 1;
        //mLastIndexTaskWord = taskedRandInt;
        Gdx.app.log("Answer", WordsHelper.getInstance().getWord(mTrueIndexAnswer));
    }


    public void update(float delta) {

        mFrontRoad.update(delta);
        mBackRoad.update(delta);
        if (mFrontRoad.isScrolledBottom())
            mFrontRoad.reset(-760);
        if (mBackRoad.isScrolledBottom())
            mBackRoad.reset(-760);
        mWalls.update(delta);
        //mWalls[1].update(delta);

        if (mWalls.isScrolledBottom()) {
            if (!mWalls.isCollidedWithFalseMark())
                WordsHelper.getInstance().removeWord(mIndexTaskWord);
            if (WordsHelper.getInstance().count() > 4) {
                initWords();
                mWalls.setTrueIndex(mTrueIndexAnswer);
                mPanelTaksedWord.setIndexText(mIndexTaskWord);

            }

        }
        //mWalls[1].isScrolledBottom();



    }

    public void updateReady(float delta) {
        mFrontRoad.update(delta);
        mBackRoad.update(delta);

        if (mFrontRoad.isScrolledBottom())
            mFrontRoad.reset(-750);
        if (mBackRoad.isScrolledBottom())
            mBackRoad.reset(-750);
    }

    public void onRestart() {
        mFrontRoad.onRestart(0, Constants.SCROLL_SPEED);
        mBackRoad.onRestart(-mFrontRoad.getTileY(), Constants.SCROLL_SPEED);
        initWords();
        mWalls.onRestart(mTrueIndexAnswer);
        mPanelTaksedWord.setIndexText(mIndexTaskWord);
    }

    public void stop() {
        mFrontRoad.stop();
        mBackRoad.stop();
        mWalls.stop();
    }

    public void play() {
        mFrontRoad.play();
        mBackRoad.play();
        mWalls.play();
    }

    public int collides(Bus bus) {
        return mWalls.collides(bus);
    }

    public Road getFrontRoad() {
        return mFrontRoad;
    }
    public Road getBackRoad() {
        return mBackRoad;
    }

    public RoadMarksHelper getWall() {
        //return mWalls[i];
        return mWalls;
    }

    public RoadSign getTaskedWord() {return mPanelTaksedWord;}

}
