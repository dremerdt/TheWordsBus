package com.dremerdt.gamedev.gameobjects;


public class Road extends Scrollable {

    public Road(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    public void onRestart(float y, float velocity) {
        mPosition.y = y;
        mVelocity.y = velocity;
    }
}
