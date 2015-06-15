package com.dremerdt.gamedev.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dremerdt.gamedev.gameobjects.RoadSign;
import com.dremerdt.gamedev.helpers.RoadMarksHelper;
import com.dremerdt.gamedev.gameobjects.Bus;
import com.dremerdt.gamedev.gameobjects.Road;
import com.dremerdt.gamedev.helpers.ScrollHandler;
import com.dremerdt.gamedev.helpers.AssetsLoader;
import com.dremerdt.gamedev.helpers.InputHandler;
import com.dremerdt.gamedev.tweenaccessors.Value;
import com.dremerdt.gamedev.tweenaccessors.ValueAccessor;
import com.dremerdt.gamedev.ui.SimpleButton;
import com.dremerdt.gamedev.utils.Constants;
import com.dremerdt.gamedev.utils.FontFactory;

import java.util.List;
import java.util.Locale;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class GameRenderer {

    private GameWorld mWorld;
    private OrthographicCamera mCamera;
    private ShapeRenderer mShapeRenderer;

    private SpriteBatch mBatcher;

    //Game Objects
    private Bus mBus;
    private ScrollHandler mScrollHandler;
    private Road mFRoad;
    private Road mBRoad;
    private RoadSign mTaskedWord;
    //private RoadMarksHelper mMarks[] = new RoadMarksHelper[2];
    private RoadMarksHelper mMarks;

    //Game Assets
    private TextureRegion mRoadAsset,
            mBusAsset,
            mMarkAsset,
            mSignAsset;

    private TweenManager mManager;
    private Value mAlpha = new Value();
    private List<SimpleButton> mMenuButtons;
    private List<SimpleButton> mListOfThemes;
    private SimpleButton mSoundButton;

    public GameRenderer(GameWorld world) {
        mMenuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();
        mListOfThemes = ((InputHandler) Gdx.input.getInputProcessor())
                .getListOfThemes();
        mSoundButton = ((InputHandler) Gdx.input.getInputProcessor())
                .getSoundButton();

        mWorld = world;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(true, 480, 800);

        mBatcher = new SpriteBatch();
        mBatcher.setProjectionMatrix(mCamera.combined);
        mShapeRenderer = new ShapeRenderer();
        mShapeRenderer.setProjectionMatrix(mCamera.combined);

        initGameObjects();
        initAssets();
        setupTweens();
    }

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        mManager = new TweenManager();
        Tween.to(mAlpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(mManager);
    }

    public void render(float delta) {

        Bus bus = mWorld.getBus();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mBatcher.begin();

        //mBatcher.disableBlending();
        drawRoad();
        //mBatcher.enableBlending();
        if (mWorld.isRunning()) {
            drawMarks();

            mBatcher.draw(AssetsLoader.sBus,
                    bus.getX(), bus.getY(),
                    bus.getWidth()/2.f, bus.getHeight()/2.f,
                    bus.getWidth(), bus.getHeight(), 1,1,
                    bus.getRotation());

            mBatcher.draw(mSignAsset, 0, 0, Constants.SCREEN_WIDTH, 74);
            mTaskedWord.draw(mBatcher);
            drawScore();
        }
        if (mWorld.isMenu())
            drawMenuUI();
        else if (mWorld.isLevelSelection())
            drawListOfThemes();
        else if (mWorld.isReady()) {
            drawReady();
        }
        else if (mWorld.isGameOver())
            drawGameOver();
        mSoundButton.draw(mBatcher);
        mBatcher.end();
        drawTransition(delta);


    }

    private void drawTransition(float delta) {
        if (mAlpha.getValue() > 0) {
            mManager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            mShapeRenderer.setColor(1, 1, 1, mAlpha.getValue());
            mShapeRenderer.rect(0, 0, 136, 300);
            mShapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

    private void drawMenuUI() {
        /*batcher.draw(AssetLoader.sLogo, 480 / 2 - 56, midPointY - 50,
                AssetLoader.sLogo.getRegionWidth() / 1.2f,
                AssetLoader.sLogo.getRegionHeight() / 1.2f);*/

        for (SimpleButton button : mMenuButtons)
            button.draw(mBatcher);

        mBatcher.draw(AssetsLoader.sWBLogo, Constants.SCREEN_WIDTH/2 - AssetsLoader.sWBLogo.getRegionWidth()/2, 50);

    }

    private void drawListOfThemes() {
        for (SimpleButton sb : mListOfThemes)
            sb.draw(mBatcher);
    }

    private void drawRoad() {
        mBatcher.draw(mRoadAsset, mFRoad.getX(), mFRoad.getY(),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        mBatcher.draw(mRoadAsset, mBRoad.getX(), mBRoad.getY(),
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }
    private void drawMarks() {
        mMarks.draw(mBatcher, mMarkAsset);
        //mMarks[1].draw(mBatcher, mMarkAsset);
    }

    private void drawScore() {
        String scorestr = "Score: " + mWorld.getScore();
        FontFactory.getInstance().getFont(new Locale("en", "US"))
                .draw(mBatcher, scorestr,420 -
                        FontFactory.getInstance().getFont(new Locale("en", "US"))
                                .getBounds(scorestr).width/2 , 80);
    }

    private void drawReady() {
        FontFactory.getInstance().getFont(new Locale("en", "US")).setScale(1.5f);
        FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.YELLOW);
        FontFactory.getInstance().getFont(new Locale("en", "US"))
                .draw(mBatcher, "Touch to start!",Constants.SCREEN_WIDTH/2-
                        FontFactory.getInstance().getFont(new Locale("en", "US"))
                                .getBounds("Touch to start!").width/2 , Constants.SCREEN_HEIGHT/2 - 100);
        FontFactory.getInstance().getFont(new Locale("en", "US")).setScale(1.f);
        FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.WHITE);
    }

    private void drawGameOver() {
        if (mWorld.getScore() > 0) {
            float posX = Constants.SCREEN_WIDTH/2 - FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .getBounds("You are winner!").width/2-20;
            FontFactory.getInstance().getFont(new Locale("en", "US")).setScale(1.5f);
            FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.YELLOW);
            FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .draw(mBatcher, "You are winner!",posX,
                            Constants.SCREEN_HEIGHT/2 - 100);
            FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .draw(mBatcher, "Your score: "+mWorld.getScore(),posX ,
                            Constants.SCREEN_HEIGHT/2 - 40);
            FontFactory.getInstance().getFont(new Locale("en", "US")).setScale(1.f);
        } else {
            FontFactory.getInstance().getFont(new Locale("en", "US")).setScale(1.5f);
            FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.YELLOW);
            FontFactory.getInstance().getFont(new Locale("en", "US"))
                    .draw(mBatcher, "You lost!",Constants.SCREEN_WIDTH/2-
                            FontFactory.getInstance().getFont(new Locale("en", "US"))
                                    .getBounds("You lost!").width/2 , Constants.SCREEN_HEIGHT/2 - 100);
            FontFactory.getInstance().getFont(new Locale("en", "US")).setScale(1.f);
        }
        FontFactory.getInstance().getFont(new Locale("en", "US")).setColor(Color.WHITE);

    }

    private void initGameObjects() {
        mBus = mWorld.getBus();
        mScrollHandler = mWorld.getScrollHandler();
        mFRoad = mScrollHandler.getFrontRoad();
        mBRoad = mScrollHandler.getBackRoad();
        mMarks = mScrollHandler.getWall();
        mTaskedWord = mScrollHandler.getTaskedWord();
        //mMarks[1] = mScrollHandler.getWall(1);

    }

    private void initAssets() {
        mBusAsset = AssetsLoader.sBus;
        mRoadAsset = AssetsLoader.sRoad;
        mMarkAsset = AssetsLoader.sBricksWall;
        mSignAsset = AssetsLoader.sSign;
        if (!AssetsLoader.getSoundStatus())
            mSoundButton.switchTextures();
    }
}
