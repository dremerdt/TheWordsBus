package com.dremerdt.gamedev.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.Locale;

public class FontFactory {


    public static final String ENGLISH_FONT_NAME = "fonts/goodfishrg.ttf";
    public static final String RUSSIAN_FONT_NAME = "fonts/Capture_it.ttf";


    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";


    private static FontFactory instance = new FontFactory();

    private BitmapFont enFont;
    private BitmapFont ruFont;


    private FontFactory() { super(); }

    public static synchronized FontFactory getInstance() {
        return instance;
    }

    public void initialize() {
        // If fonts are already generated, dispose it!
        if (enFont != null) enFont.dispose();
        if (ruFont != null) ruFont.dispose();

        enFont = generateFont(ENGLISH_FONT_NAME, FreeTypeFontGenerator.DEFAULT_CHARS);

        ruFont = generateFont(RUSSIAN_FONT_NAME, RUSSIAN_CHARACTERS);
    }


    private BitmapFont generateFont(String fontName, String characters) {

        // Configure font parameters
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.characters = characters;
        if (fontName.equals("fonts/goodfishrg.ttf"))
            parameter.size = 32;
        else
            parameter.size = 20;

        parameter.flip = true;

        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        BitmapFont font = generator.generateFont(parameter);

        // Dispose resources
        generator.dispose();

        return font;
    }


    public BitmapFont getFont(Locale locale) {
        if      ("en".equals(locale.getLanguage())) return enFont;
        else if ("ru".equals(locale.getLanguage())) return ruFont;
        else throw new IllegalArgumentException("Not supported language");
    }

    public void dispose() {
        enFont.dispose();
        ruFont.dispose();
    }
}
