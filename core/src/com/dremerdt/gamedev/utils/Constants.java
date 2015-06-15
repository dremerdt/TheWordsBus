package com.dremerdt.gamedev.utils;

import com.badlogic.gdx.Gdx;

public class Constants {

    //Bus position
    public static final float LEFT_SIDE_POSITION = 60;
    public static final float CENTER_POSITION = 200;
    public static final float RIGHT_SIDE_POSITION = 350;

    //Screen resolution
    public static final int SCREEN_WIDTH = 480;
    public static final int SCREEN_HEIGHT = 800;
    public static float SCALE_RATIO_WIDTH = (float)Gdx.graphics.getWidth() / SCREEN_WIDTH;
    public static float SCALE_RATIO_HEIGHT = (float)Gdx.graphics.getHeight() / SCREEN_HEIGHT;


    //
    public static final int SCROLL_SPEED = 200;

    //
    public static final String PATH_EQUIPMENT = "Words/Equipment.dict";
    public static final String PATH_HOBBY = "Words/Hobby.dict";
    public static final String PATH_MEDIA = "Words/Media.dict";
    public static final String PATH_SPACE = "Words/Space.dict";
    public static final String PATH_WORK = "Words/Work.dict";
}
