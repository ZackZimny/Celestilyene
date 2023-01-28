package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.GameHelpers.*;

import java.util.HashMap;

/**
 * Loads in all screens, handles redirects between screens, and interactions with the database
 */
public class ScreenManager {
    private ScreenState screenState = ScreenState.MAIN_MENU;
    private ScreenState prevScreenState = ScreenState.MAIN_MENU;
    private GameLoop gameLoop;
    //Pair screens and ScreenStates
    private final HashMap<ScreenState, Screen> screenHashMap = new HashMap<>();
    private final GameOverScreen gameOverScreen = new GameOverScreen();
    private final TimeTrialSuccessScreen successScreen = new TimeTrialSuccessScreen();
    private final MainMenuScreen mainMenuScreen = new MainMenuScreen();
    private RecordScreen recordScreen;
    private final DatabaseManager databaseManager = new DatabaseManager();
    private RuntimeConfigurations runtimeConfigurations;
    private OptionsScreen optionsScreen;
    private final AssetManagerHandler assetManagerHandler = new AssetManagerHandler();
    private GameSounds gameSounds;
    private boolean loaded = false;

    /**
     * Loads in all assets and databases
     * @throws GdxRuntimeException if an error occurs while loading
     */
    public void load() throws GdxRuntimeException {
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
            TutorialScreen1 tutorialScreen1 = new TutorialScreen1(dummyPlayer);
            TutorialScreen2 tutorialScreen2 = new TutorialScreen2(assetManagerHandler);
            TutorialScreen3 tutorialScreen3 = new TutorialScreen3(assetManagerHandler);
            TutorialScreen4 tutorialScreen4 = new TutorialScreen4(assetManagerHandler);
            recordScreen = new RecordScreen(databaseManager.getRecords());
            mainMenuScreen.setHighestRecord(databaseManager.getRecords().get(0));
            mainMenuScreen.setHighestTimeRecord(databaseManager.getTimeRecords().get(0));
            screenHashMap.put(ScreenState.MAIN_MENU, mainMenuScreen);
            screenHashMap.put(ScreenState.OPTIONS, optionsScreen);
            screenHashMap.put(ScreenState.GAME_OVER, gameOverScreen);
            screenHashMap.put(ScreenState.TUTORIAL1, tutorialScreen1);
            screenHashMap.put(ScreenState.TUTORIAL2, tutorialScreen2);
            screenHashMap.put(ScreenState.TUTORIAL3, tutorialScreen3);
            screenHashMap.put(ScreenState.TUTORIAL4, tutorialScreen4);
            screenHashMap.put(ScreenState.RECORDS, recordScreen);
            screenHashMap.put(ScreenState.SUCCESS, successScreen);
            loaded = true;
        }
    }

    /**
     * Creates the game loop
     * @throws GdxRuntimeException if there is an error creating the game loop
     */
    public void loadGameLoop() throws GdxRuntimeException {
        gameSounds = new GameSounds(runtimeConfigurations.getSfxVolume(), runtimeConfigurations.getMusicVolume(),
                assetManagerHandler.getAssetManager());
        gameSounds.playBackgroundMusic();
        gameLoop = new GameLoop(assetManagerHandler, gameSounds);
    }

    /**
     * updates the database with new runtime configurations
     */
    private void handleDatabaseUpdate() {
        databaseManager.updateRuntimeConfigurations(optionsScreen.getRuntimeConfigurations());
        runtimeConfigurations = databaseManager.getRuntimeConfigurations();
        gameSounds.setSfxVolume(runtimeConfigurations.getSfxVolume());
        gameSounds.setMusicVolume(runtimeConfigurations.getMusicVolume());
        gameSounds.playBackgroundMusic();
        gameSounds.playPlayerShot();
        EventLogHandler.log("New runtime_configuration: " + runtimeConfigurations);
    }

    /**
     * Handles loading and database scripts associated with the game being over
     */
    private void handleGameOver() {
        gameOverScreen.setScore(gameLoop.getScore());
        databaseManager.insertScore(gameLoop.getScore(), optionsScreen.getRuntimeConfigurations().getName());
        mainMenuScreen.setHighestRecord(databaseManager.getRecords().get(0));
        recordScreen.setRecords(databaseManager.getRecords());
        successScreen.setTime(gameLoop.getTime());
        if(gameLoop.isTimeTrialMode() && gameLoop.getScore() == 16){
            databaseManager.insertTime(gameLoop.getTime(), runtimeConfigurations.getName());
            mainMenuScreen.setHighestTimeRecord(databaseManager.getTimeRecords().get(0));
        }
        gameLoop = new GameLoop(assetManagerHandler, gameSounds);
    }

    /**
     * Toggles fullscreen depending on runtime configurations
     */
    private void determineFullscreen() {
        if(getRuntimeConfigurations().isFullscreen()){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode((int) (ScreenProjectionHandler.getWorldWidth()),
                    (int) (ScreenProjectionHandler.getWorldHeight()));
            Gdx.graphics.setUndecorated(false);
        }
    }

    /**
     * Displays the background square in screens
     * @param shapeRenderer displays background square
     * @param color color of the square
     */
    public static void renderBackground(ShapeRenderer shapeRenderer, Color color) {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.setColor(color);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, ScreenProjectionHandler.getWorldWidth(), ScreenProjectionHandler.getWorldHeight());
        shapeRenderer.end();
    }

    /**
     * displays current screen
     * @param spriteBatch renders sprites and text
     * @param shapeRenderer renders shapes within the screen
     */
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            EventLogHandler.log("The game has been closed using the escape key.");
            Gdx.app.exit();
        }
        if(!loaded)
            return;
        renderBackground(shapeRenderer, new Color(0.1f, 0.6f, 0.2f, 1));
        if(screenState != ScreenState.GAME_LOOP){
            if(screenState == ScreenState.MAIN_MENU && mainMenuScreen.getStringButtonHashMap().get("Time Trial").isClicked()){
                gameLoop.setTimeTrialMode(true);
                gameLoop.setCurrLoader(gameLoop.getLevelLoaders().get(0));
            }
            if(screenState == ScreenState.GAME_OVER && gameOverScreen.getStringButtonHashMap().get("Play Again").isClicked()){
                gameLoop.setTimeTrialMode(true);
                gameLoop.setCurrLoader(gameLoop.getLevelLoaders().get(0));
            }
            Screen currScreen = screenHashMap.get(screenState);
            currScreen.render(shapeRenderer, spriteBatch);
            screenState = currScreen.getScreenState();
            if(screenState != prevScreenState){
                EventLogHandler.log("The screen has changed to " + screenState.name());
                prevScreenState = screenState;
            }
            if(screenState == ScreenState.OPTIONS && optionsScreen.getStringButtonHashMap().get("Apply").isClicked()){
                handleDatabaseUpdate();
                determineFullscreen();
                EventLogHandler.log("Runtime configurations have been updated successfully.");
            }
        } else {
            gameLoop.render();
            screenState = gameLoop.getScreenState();
            if(screenState != ScreenState.GAME_LOOP){
                EventLogHandler.log("The game loop is done.");
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
}
