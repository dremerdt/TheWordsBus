package com.dremerdt.gamedev.gameobjects;


public class RoadMark extends Scrollable {

    private boolean mIsTrueMark;

    private String mWord;

    public RoadMark(float x, float y, int width, int height,
                    float scrollSpeed, boolean isTrueMark) {
        super(x, y, width, height, scrollSpeed);
        mIsTrueMark = isTrueMark;
    }

    @Override
    public void reset(float newY){
        super.reset(newY);
    }

    public boolean isTrueMark() {
        return mIsTrueMark;
    }

    public void setTrueMark(boolean trueMark) {
        mIsTrueMark = trueMark;
    }

    //public void setWord(String word) {mWord = word;}

    //public String getWord() {return mWord;}
}
