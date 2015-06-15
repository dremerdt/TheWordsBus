package com.dremerdt.gamedev.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AssetsLoader {
    public static Texture sTexture;

    public static TextureRegion sBus, sBricksWall, sRoad, sSign, sSignPressed, sSingUpped,
    sWBLogo, sLogo, sSoundOn, sSoundOff;
    public static Sound sDead, sFailWay, sCorrectWay, sWinSound, sLoosingSound;

    public static List<Integer> mScores;

    private static Preferences prefs;

    public static void load() {
        sTexture = new Texture(Gdx.files.internal("texture.png"));
        sTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        sRoad = new TextureRegion(sTexture, 0, 0, 480, 794);
        sRoad.flip(false, true);

        sBus = new TextureRegion(sTexture, 483, 0, 104, 253);
        sBus.flip(false, true);

        sBricksWall = new TextureRegion(sTexture, 488, 260, 66, 66);//mb 64
        sBricksWall.flip(false, true);

        sSign = new TextureRegion(sTexture, 481, 328, 240, 88);
        sSign.flip(false, true);

        sSignPressed = new TextureRegion(sTexture, 485, 421, 234, 83);
        sSignPressed.flip(false, true);

        sSingUpped = new TextureRegion(sTexture, 485, 506, 234, 83);
        sSingUpped.flip(false, true);

        sWBLogo = new TextureRegion(sTexture, 591, 13, 352, 235);
        sWBLogo.flip(false, true);

        sLogo = new TextureRegion(sTexture, 486, 599, 415, 339);
        //sLogo.flip(false, true);

        sSoundOn = new TextureRegion(sTexture, 556, 260, 64, 64);
        sSoundOn.flip(false, true);

        sSoundOff = new TextureRegion(sTexture, 621, 260, 64, 64);
        sSoundOff.flip(false, true);

        sFailWay = Gdx.audio.newSound(Gdx.files.internal("Sounds/WhistleShort.wav"));

        sCorrectWay = Gdx.audio.newSound(Gdx.files.internal("Sounds/CorrectSound.wav"));

        sWinSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/TaDa.wav"));

        sLoosingSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/LoosingSound.wav"));

        prefs = Gdx.app.getPreferences("TheWordsBus");
        if (!prefs.contains("soundStatus"))
            prefs.putBoolean("soundStatus", true);
        loadScores();
    }

    public static boolean getSoundStatus() {
        return prefs.getBoolean("soundStatus");
    }

    public static void setSoundStatus(boolean status) {
        prefs.putBoolean("soundStatus", status);
        prefs.flush();
    }

    private static void loadScores() {
        if (!prefs.contains("equipmentScore"))
            prefs.putInteger("equipmentScore", 0);
        if (!prefs.contains("hobbyScore"))
            prefs.putInteger("hobbyScore", 0);
        if (!prefs.contains("mediaScore"))
            prefs.putInteger("mediaScore", 0);
        if (!prefs.contains("cosmosScore"))
            prefs.putInteger("cosmosScore", 0);
        if (!prefs.contains("workScore"))
            prefs.putInteger("workScore", 0);
    }

    public static int getScore(String theme) { return prefs.getInteger(theme); }

    public static void setScore(String theme, int score) {
        if (prefs.getInteger(theme) < score) {
            prefs.putInteger(theme, score);
            prefs.flush();
        }
    }

    public static void dispose() {
        sTexture.dispose();
    }
}
