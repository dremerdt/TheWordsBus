package com.dremerdt.gamedev.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dremerdt.gamedev.gameobjects.Bus;
import com.dremerdt.gamedev.gameobjects.RoadMark;
import com.dremerdt.gamedev.utils.Constants;
import com.dremerdt.gamedev.utils.FontFactory;

import java.util.Locale;
import java.util.Random;

public class RoadMarksHelper {

    private int mCurrentTrueIndex;
    private int mCurrentFirstIndex;
    private int mCurrentSecondIndex;
    private int mTrueMark;
    private boolean mCollied = false;
    private boolean mCollidedWithFalseMark = false;

    private Random mRandom;

    private String[] mAnswers;

    private RoadMark[] mMarks = new RoadMark[3];

    public RoadMarksHelper(int posY/*, int index*/) {
        mRandom = new Random();
        mAnswers = new String[3];
        mMarks[0] = new RoadMark(33, posY, 124, 64, Constants.SCROLL_SPEED,
                false);
        mMarks[1] = new RoadMark(mMarks[0].getTileX() + 12, posY, 130, 64,
                Constants.SCROLL_SPEED,
                false);
        mMarks[2] = new RoadMark(mMarks[1].getTileX() + 12, posY, 134, 64,
                Constants.SCROLL_SPEED,
                false);
        /*mCurrentTrueIndex = index;
        setIndexes();
        mMarks[mTrueMark].setTrueMark(true);*/
    }

    public void onRestart(int index) {
        reset(-100);
        mCollidedWithFalseMark = false;
        mCurrentTrueIndex = index;
        setTrueIndex(index);
        for (int i = 0; i < 3; i++) {
            mMarks[i].setVelocity(Constants.SCROLL_SPEED);
        }
    }

    public void update(float delta) {
        for (int i = 0; i < 3; i++) {
            mMarks[i].update(delta);
        }
    }

    public void reset(float newY) {
        for (int i = 0; i < 3; i++) {
            mMarks[i].reset(newY);
            mMarks[i].setTrueMark(false);
        }
        mCollied = false;

    }

    public boolean isScrolledBottom() {
        if (mMarks[0].isScrolledBottom()) {
            reset(-100);

            return true;
        }
        else return false;
    }

    public void draw(SpriteBatch spriteBatch, TextureRegion textureRegion) {
        for (int i = 0; i < 3; i++)
            spriteBatch.draw(textureRegion, mMarks[i].getX(),
                    mMarks[i].getY(), mMarks[i].getWidth(), mMarks[i].getHeight());
        //FontFactory.getInstance().getFont(new Locale("ru", "RU")).setColor(Color.WHITE);
        for (int i = 0; i < 3; i++)
            FontFactory.getInstance().getFont(new Locale("ru", "RU")).draw(spriteBatch,
                mAnswers[i], mMarks[i].getX()+ mMarks[i].getWidth()/2 -
                    FontFactory.getInstance().getFont(new Locale("ru", "RU")).getBounds(mAnswers[i]).width/2
                    , mMarks[i].getY() + 18);
    }

    private void setIndexes() {
        mCollidedWithFalseMark = false;
        mTrueMark = mRandom.nextInt(3);
        /*for (int i = 0; i < 3; i++)
            mAnswers[i] = "";*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread

                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // process the result, e.g. add it to an Array<Result> field of the ApplicationListener.
                        mAnswers[mTrueMark] = WordsHelper.getInstance().getWord(mCurrentTrueIndex);
                        do {
                            mCurrentFirstIndex = mRandom.nextInt(WordsHelper.getInstance().count());
                        } while ((mCurrentTrueIndex == mCurrentFirstIndex)
                                || (mCurrentFirstIndex % 2 == 0));
                        do {
                            mCurrentSecondIndex = mRandom.nextInt(WordsHelper.getInstance().count());
                        } while ((mCurrentTrueIndex == mCurrentSecondIndex)
                                /*|| (mCurrentSecondIndex == mCurrentFirstIndex)*/
                                || (mCurrentSecondIndex % 2 == 0));

                        int randInt;
                        do {
                            randInt = mRandom.nextInt(3);
                        } while (randInt == mTrueMark);
                        mAnswers[randInt] = WordsHelper.getInstance().getWord(mCurrentFirstIndex);
                        int randInt1;
                        do {
                            randInt1 = mRandom.nextInt(3);
                        } while ((randInt1 == mTrueMark) || (randInt == randInt1));
                        mAnswers[randInt1] = WordsHelper.getInstance().getWord(mCurrentSecondIndex);
                    }
                });
            }
        }).start();


    }

    public int collides(Bus bus) {
        for (int i = 0; i < 3; i++) {
            if (!mMarks[i].isTrueMark()) {
                /*if         (mMarks[i].getX() < (bus.getX() + bus.getWidth())
                        && (mMarks[i].getX() + mMarks[i].getWidth()) > bus.getX()
                        &&  mMarks[i].getY() < (bus.getY() + bus.getHeight())
                        && (mMarks[i].getY() + mMarks[i].getHeight() > bus.getY()))
                    return true;*/
                if         (mMarks[i].getX() < (bus.getX() + bus.getWidth())
                        && (mMarks[i].getX() + mMarks[i].getWidth()) > bus.getX()
                        && (mMarks[i].getY() < (bus.getY() + bus.getHeight()))
                        && (mMarks[i].getY() + mMarks[i].getHeight() > bus.getY())) {
                    mCollidedWithFalseMark = true;
                    mCollied = true;
                    return 1;
                }
            } else if (mMarks[i].isTrueMark()) {
                if (mMarks[i].getX() < (bus.getX() + bus.getWidth())
                        && (mMarks[i].getX() + mMarks[i].getWidth()) > bus.getX()
                        && (mMarks[i].getY() < (bus.getY() + bus.getHeight()))
                        && (mMarks[i].getY() + mMarks[i].getHeight() > bus.getY())) {
                    mCollied = true;
                    return 0;
                }
            }

        }
        return -1;
    }

    public void stop() {
        for (int i = 0; i < 3; i++)
            mMarks[i].stop();
    }

    public void play() {
        for (int i = 0; i < 3; i++)
            mMarks[i].play();
    }

    public void setTrueIndex(int index) {
        mCurrentTrueIndex = index;
        setIndexes();
        mMarks[mTrueMark].setTrueMark(true);

    }

    public boolean isCollied() {
        return mCollied;
    }

    public boolean isCollidedWithFalseMark() {return mCollidedWithFalseMark;}
}
