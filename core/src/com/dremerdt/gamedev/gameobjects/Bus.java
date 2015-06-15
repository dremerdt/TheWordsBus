package com.dremerdt.gamedev.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.dremerdt.gamedev.utils.Constants;

public class Bus {
    private Vector2 mPosition;

    private int mWidth;
    private int mHeight;

    private short mSide;

    private int mVelocity;

    private float mRotation;


    public Bus(float x, float y, int width, int height) {
        mVelocity = 8;
        mSide = 1;
        mWidth = width;
        mHeight = height;
        mPosition = new Vector2(x, y);

    }

    public void onRestart() {
        mSide = 1;
    }

    public void update(float delta) {

        switch (mSide) {
            case 0:
                if(mPosition.x > Constants.LEFT_SIDE_POSITION) {
                    mPosition.x -= mVelocity;
                    mRotation -= 600 * delta;
                    if (mRotation < -20)
                        mRotation = -20;
                } else  mRotation = 0;
                break;
            case 1:
                if(mPosition.x > Constants.CENTER_POSITION) {
                    mPosition.x -= mVelocity;
                    mRotation -= 600 * delta;
                    if (mRotation < -20)
                        mRotation = -20;
                }
                else  if(mPosition.x < Constants.CENTER_POSITION) {
                    mPosition.x += mVelocity;
                    mRotation -= 600 * delta;
                    if (mRotation < 20)
                        mRotation = 20;
                } else mRotation = 0;
                if (mPosition.y > 600)
                    mPosition.y -= 2;
                break;
            case 2: if(mPosition.x < Constants.RIGHT_SIDE_POSITION) {
                        mPosition.x += mVelocity;
                        mRotation -= 600 * delta;
                        if (mRotation < 20)
                            mRotation = 20;
                    }
                else mRotation = 0;
                break;
            default: break;
        }
    }

    public void changeSide(boolean side) {
        if (side) {
            if (mSide == 0)
                return;
            else mSide--;
        } else {
            if (mSide == 2)
                return;
            else mSide++;
        }
    }



    public float getX() {
        return mPosition.x;
    }

    public float getY() {
        return mPosition.y;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public float getRotation() {
        return mRotation;
    }

}
