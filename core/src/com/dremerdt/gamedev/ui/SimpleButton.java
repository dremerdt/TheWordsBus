package com.dremerdt.gamedev.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dremerdt.gamedev.utils.FontFactory;

import java.util.Locale;

public class SimpleButton {

    private float mPositionX, mPositionY, mWidth, mHeight;
    private String mText;
    private int mScore;

    private TextureRegion mButtonUp;
    private TextureRegion mButtonDown;

    private Rectangle mBounds;

    private boolean mIsPressed = false;

    public SimpleButton(float x, float y, float width, float height,
                        TextureRegion buttonUp, TextureRegion buttonDown, String text, int score) {
        mPositionX = x;
        mPositionY = y;
        mWidth = width;
        mHeight = height;
        mButtonUp = buttonUp;
        mButtonDown = buttonDown;
        mText = text;
        mScore = score;
        mBounds = new Rectangle(x, y, width, height);

    }

    public boolean isClicked(int screenX, int screenY) {
        return mBounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (mIsPressed) {
            batcher.draw(mButtonDown, mPositionX, mPositionY, mWidth, mHeight);
            FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.YELLOW);
            FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .draw(batcher, mText, mPositionX + (mWidth/2 - FontFactory.getInstance()
                            .getFont(new Locale("en", "US")).getBounds(mText).width/2),
                            mPositionY + mHeight/2 - FontFactory.getInstance()
                                    .getFont(new Locale("en", "US")).getBounds(mText).height/2);
            if (mScore > 0)
                FontFactory.getInstance().getFont(new Locale("en", "US"))
                        .draw(batcher, String.valueOf(mScore), mPositionX + mWidth - 50,
                                mPositionY + mHeight/2 - FontFactory.getInstance()
                                        .getFont(new Locale("en", "US")).getBounds(String.valueOf(mScore)).height/2);

            FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.WHITE);
        }
        else {
            batcher.draw(mButtonUp, mPositionX, mPositionY, mWidth, mHeight);
            FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .draw(batcher, mText, mPositionX + (mWidth/2 - FontFactory.getInstance()
                                    .getFont(new Locale("en", "US")).getBounds(mText).width/2),
                            mPositionY + mHeight/2 - FontFactory.getInstance()
                                    .getFont(new Locale("en", "US")).getBounds(mText).height/2);
            if (mScore > 0)
                FontFactory.getInstance().getFont(new Locale("en", "US"))
                        .draw(batcher, String.valueOf(mScore), mPositionX + mWidth - 50,
                                mPositionY + mHeight/2 - FontFactory.getInstance()
                                        .getFont(new Locale("en", "US")).getBounds(String.valueOf(mScore)).height/2);
        }

    }
    public void switchTextures() {
        TextureRegion temp = mButtonDown;
        mButtonDown = mButtonUp;
        mButtonUp = temp;
    }
    public boolean isTouchDown(int screenX, int screenY) {
        if (mBounds.contains(screenX, screenY)) {
            mIsPressed = true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {

        // It only counts as a touchUp if the button is in a pressed state.
        if (mBounds.contains(screenX, screenY) && mIsPressed) {
            mIsPressed = false;
            return true;
        }

        // Whenever a finger is released, we will cancel any presses.
        mIsPressed = false;
        return false;
    }

    public void setScore(int score) {mScore = score;}

    public float getTileY() {return mPositionY + mHeight;}
}
