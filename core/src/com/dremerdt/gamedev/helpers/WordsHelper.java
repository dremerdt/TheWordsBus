package com.dremerdt.gamedev.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.dremerdt.gamedev.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordsHelper {
    private static WordsHelper instance = new WordsHelper();

    private static List<String> mListOfWords;

    private static String mPath  = "";

    private WordsHelper() { super(); }

    public void initialize(String path) throws IOException {
        if (!mPath.equals(path)) {
            mPath = path;
            FileHandle file = Gdx.files.internal(path);
            Gdx.app.log("WORDHELPER", file.readString("utf8"));
            BufferedReader reader = new BufferedReader(file.reader("utf8"));
            mListOfWords = new ArrayList<String>();
            try {
                String line = reader.readLine();
                while( line != null ) {
                    mListOfWords.add(line);
                    line = reader.readLine();

                }
            } catch (IOException e) {
                Gdx.app.log("WORDHELPER", e.getMessage());
            } finally {
                reader.close();
            }
        }
    }

    public static synchronized WordsHelper getInstance() {
        return instance;
    }

    public List<String> getListOfWords() {return mListOfWords;}

    public int count() {return mListOfWords.size();}

    public String getWord(int index) {return mListOfWords.get(index);}

    public void removeWord(int index) {
        mListOfWords.remove(index);
        mListOfWords.remove(index);
        Gdx.app.log("Size", mListOfWords.size() + "");
    }

}
