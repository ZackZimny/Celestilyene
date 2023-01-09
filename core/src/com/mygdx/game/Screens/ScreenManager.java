package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.GameHelpers.DatabaseManager;
import com.mygdx.game.GameHelpers.GameLoop;
import com.mygdx.game.GameHelpers.GameSounds;
import com.mygdx.game.GameHelpers.RuntimeConfigurations;

import java.util.HashMap;

public class ScreenManager {
    private ScreenState screenState = ScreenState.MAIN_MENU;
    private GameLoop gameLoop;
    private HashMap<ScreenState, Screen> screenHashMap = new HashMap<>();
    private GameOverScreen gameOverScreen = new GameOverScreen();
    private MainMenuScreen mainMenuScreen = new MainMenuScreen();
    private TutorialScreen1 tutorialScreen1;
    private TutorialScreen2 tutorialScreen2;
    private TutorialScreen3 tutorialScreen3;
    private TutorialScreen4 tutorialScreen4;
    private RecordScreen recordScreen;
    private DatabaseManager databaseManager = new DatabaseManager();
    private RuntimeConfigurations runtimeConfigurations;
    private OptionsScreen optionsScreen;
    private AssetManagerHandler assetManagerHandler = new AssetManagerHandler();
    private GameSounds gameSounds;
    private boolean loaded = false;
    public void load(){
        databaseManager.createDatabase();
        databaseManager.createTables();
        runtimeConfigurations = databaseManager.getRuntimeConfigurations();
        determineFullscreen();
        optionsScreen = new OptionsScreen(runtimeConfigurations);
        float playerX = ScreenProjectionHandler.getWorldWidth() / 2 - 16;
        float playerY = ScreenProjectionHandler.getWorldHeight() / 2 - 16;
        if(assetManagerHandler.getAssetManager().isFinished()) {
            gameSounds = new GameSounds(runtimeConfigurations.getSfxVolume(), runtimeConfigurations.getMusicVolume(), assetManagerHandler.getAssetManager());
            Player dummyPlayer = new Player((int) playerX, (int) playerY, 3, assetManagerHandler, gameSounds);
            tutorialScreen1 = new TutorialScreen1(dummyPlayer);
            tutorialScreen2 = new TutorialScreen2(assetManagerHandler);
            tutorialScreen3 = new TutorialScreen3(assetManagerHandler);
            tutorialScreen4 = new TutorialScreen4(assetManagerHandler);
            recordScreen = new RecordScreen(databaseManager.getRecords());
            mainMenuScreen.setHighestRecord(databaseManager.getRecords().get(0));
            screenHashMap.put(ScreenState.MAIN_MENU, mainMenuScreen);
            screenHashMap.put(ScreenState.OPTIONS, optionsScreen);
            screenHashMap.put(ScreenState.GAME_OVER, gameOverScreen);
            screenHashMap.put(ScreenState.TUTORIAL1, tutorialScreen1);
            screenHashMap.put(ScreenState.TUTORIAL2, tutorialScreen2);
            screenHashMap.put(ScreenState.TUTORIAL3, tutorialScreen3);
            screenHashMap.put(ScreenState.TUTORIAL4, tutorialScreen4);
            screenHashMap.put(ScreenState.RECORDS, recordScreen);
            loaded = true;
        }
    }

    public void loadGameLoop() {
        gameSounds = new GameSounds(runtimeConfigurations.getSfxVolume(), runtimeConfigurations.getMusicVolume(),
                assetManagerHandler.getAssetManager());
        gameSounds.playBackgroundMusic();
        gameLoop = new GameLoop(assetManagerHandler, gameSounds);
    }

    private void handleDatabaseUpdate() {
        databaseManager.updateRuntimeConfigurations(optionsScreen.getRuntimeConfigurations());
        runtimeConfigurations = databaseManager.getRuntimeConfigurations();
        gameSounds.setSfxVolume(runtimeConfigurations.getSfxVolume());
        gameSounds.setMusicVolume(runtimeConfigurations.getMusicVolume());
        gameSounds.playBackgroundMusic();
        gameSounds.playPlayerShot();
    }

    private void handleGameOver() {
        gameOverScreen.setScore(gameLoop.getScore());
        databaseManager.insertScore(gameLoop.getScore(), optionsScreen.getRuntimeConfigurations().getName());
        mainMenuScreen.setHighestRecord(databaseManager.getRecords().get(0));
        recordScreen.setRecords(databaseManager.getRecords());
        gameLoop = new GameLoop(assetManagerHandler, gameSounds);
    }

    private void determineFullscreen() {
        if(getRuntimeConfigurations().isFullscreen()){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode((int) (ScreenProjectionHandler.getWorldWidth()),
                    (int) (ScreenProjectionHandler.getWorldHeight()));
            Gdx.graphics.setUndecorated(false);
        }
    }

    public static void renderBackground(ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, ScreenProjectionHandler.getWorldWidth(), ScreenProjectionHandler.getWorldHeight());
        shapeRenderer.end();
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        if(!loaded)
            return;
        renderBackground(shapeRenderer, new Color(0.1f, 0.6f, 0.2f, 1));
        if(screenState != ScreenState.GAME_LOOP){
            Screen currScreen = screenHashMap.get(screenState);
            currScreen.render(shapeRenderer, spriteBatch);
            screenState = currScreen.getScreenState();
            if(screenState == ScreenState.OPTIONS && optionsScreen.getStringButtonHashMap().get("Apply").isClicked()){
                handleDatabaseUpdate();
                determineFullscreen();
            }
        } else {
            gameLoop.render();
            screenState = gameLoop.getScreenState();
            if(screenState != ScreenState.GAME_LOOP){
                handleGameOver();
            }
        }
    }

    public AssetManagerHandler getAssetManagerHandler() {
        return assetManagerHandler;
    }

    public RuntimeConfigurations getRuntimeConfigurations() {
        return runtimeConfigurations;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
