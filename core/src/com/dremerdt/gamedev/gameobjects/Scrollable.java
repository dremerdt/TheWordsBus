package com.dremerdt.gamedev.gameobjects;


import com.badlogic.gdx.math.Vector2;
import com.dremerdt.gamedev.utils.Constants;

public class Scrollable {

    protected Vector2 mPosition;
    protected Vector2 mVelocity;
    protected int mWidth;
    protected int mHeight;


    //protected boolean mIsScrolledBottom;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        mPosition = new Vector2(x, y);
        mVelocity = new Vector2(0, scrollSpeed);
        mWidth = width;
        mHeight = height;
        //mIsScrolledBottom = false;
    }

    public void update(float delta) {
        mPosition.add(mVelocity.cpy().scl(delta));

        //if (mPosition.y > Constants.SCREEN_HEIGHT)
            //mIsScrolledBottom = true;
    }

    public void reset(float newY) {
        mPosition.y = newY;
        //mIsScrolledBottom = false;
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

    public float getTileX() {
        return mPosition.x + mWidth;
    }

    public float getTileY() {
        return mPosition.y + mHeight;
    }

    public boolean isScrolledBottom() {
        return /*mIsScrolledBottom;*/(mPosition.y > Constants.SCREEN_HEIGHT);
    }
    public void stop() {
        mVelocity.y = 0;
    }
    public void play() {mVelocity.y = Constants.SCROLL_SPEED;}
    public void setVelocity(float velocity) {mVelocity.y = velocity;}
}
