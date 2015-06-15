package com.dremerdt.gamedev.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dremerdt.gamedev.helpers.WordsHelper;
import com.dremerdt.gamedev.utils.Constants;
import com.dremerdt.gamedev.utils.FontFactory;

import java.util.Currency;
import java.util.Locale;

public class RoadSign {

    private Vector2 mPosition;
    private int mWidth;
    private int mHeight;
    private int mCurrentIndex;

    public RoadSign(float x, float y, int width, int height/*, int index*/) {
        mPosition = new Vector2(x, y);
        mWidth = width;
        mHeight = height;
        //mCurrentIndex = index;
        //Gdx.app.log("Panel", mCurrentIndex + "");
    }

    public void draw(SpriteBatch spriteBatch) {

        if (mCurrentIndex % 2 != 0)
            FontFactory.getInstance().getFont(new Locale("ru", "RU"))
                    .draw(spriteBatch, WordsHelper.getInstance().getWord(mCurrentIndex),
                            FontFactory.getInstance().getFont(new Locale("ru", "RU"))
                                    .getBounds(WordsHelper.getInstance().getWord(mCurrentIndex)).width/2 , 25);
        else
            FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .draw(spriteBatch, WordsHelper.getInstance().getWord(mCurrentIndex),Constants.SCREEN_WIDTH/2 -
                            FontFactory.getInstance().getFont(new Locale("en", "US"))
                                    .getBounds(WordsHelper.getInstance().getWord(mCurrentIndex)).width/2 , 25);
    }

    public void reset(float newY) {
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

    public void setIndexText(int index) {mCurrentIndex = index;}

}
