package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Screens.FontHandler;
import com.mygdx.game.Screens.ScreenProjectionHandler;
import com.mygdx.game.Screens.ScreenState;

import java.util.ArrayList;

/**
 * Handles all logic that deals with the Celestilyne game directly that is not a menu or database
 */
public class GameLoop {
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final ArrayList<LevelLoader> levelLoaders = new ArrayList<>();
    //current level being played
    private LevelLoader currLoader;
    private final HealthStat healthBar;
    private final OrthographicCamera uiCamera;
    //number of levels beaten
    private int score = 0;
    private final FontHandler fontHandler;
    //Determine dimensions of a set of text
    private final GlyphLayout layout;
    //adds more levels to the level pool past level 8 up to 16
    private int levelsUnlocked = 8;
    private boolean timeTrialMode = false;
    private float time = 0;

    /**
     * Loads the Celestilyne main game
     * @param assetManagerHandler holds all preloaded assets
     * @param gameSounds holds all preloaded sounds
     */
    public GameLoop(AssetManagerHandler assetManagerHandler, GameSounds gameSounds) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        fontHandler = new FontHandler();
        layout = new GlyphLayout();
        layout.setText(fontHandler.getFont(), String.valueOf(score));

        // setup for camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenProjectionHandler.getWorldWidth(),
                ScreenProjectionHandler.getWorldHeight());
        camera.update();
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, (int) ScreenProjectionHandler.getWorldWidth(),
                (int) ScreenProjectionHandler.getWorldHeight());
        uiCamera.update();

        spriteBatch = new SpriteBatch();

        healthBar = new HealthStat(10, (int) ScreenProjectionHandler.getWorldHeight() - 50, 3);
        for(int i = 1; i <= 16; i++){
            currLoader = new LevelLoader(String.format("Level%d.tmx", i), 3, assetManagerHandler, gameSounds);
            currLoader.generateMap();
            levelLoaders.add(currLoader);
        }
        refreshRandomLoader(3);
    }

    private void handleLevelChange() {
        if(currLoader.isCompleted()){
            int prevHealth = currLoader.getPlayerHealth();
            currLoader.generateMap();
            score++;
            layout.setText(fontHandler.getFont(), String.valueOf(score));
            if (score > 8 && score <= 16) {
                levelsUnlocked++;
            }
            refreshRandomLoader(prevHealth);
            Rumble.rumble(2, .2f);
        }
    }
    /**
     * Displays the gameLoop to the screen
     */
    public void render() {
        //renders green square background
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.setColor(0.1f, 0.6f, 0.2f, 1);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, ScreenProjectionHandler.getWorldWidth(), ScreenProjectionHandler.getWorldHeight());
        shapeRenderer.end();

        currLoader.handlePhysics(Gdx.graphics.getDeltaTime());
        time += Gdx.graphics.getDeltaTime();

        //begins and ends rendering
        currLoader.render(camera, spriteBatch);

        //loads new level once the game over screen is shown
        handleLevelChange();

        if(timeTrialMode){
            layout.setText(fontHandler.getFont(), TimeManager.secondsToTime(time));
        }

        //rumbles the screen and resets it once the rumble is over
        if(Rumble.getRumbleTimeLeft() > 0){
            Rumble.tick(Gdx.graphics.getDeltaTime());
            camera.translate(Rumble.getPos());
        } else {
            Vector3 screenCenter = new Vector3(ScreenProjectionHandler.getWorldWidth() / 2f,
                    ScreenProjectionHandler.getWorldHeight() / 2f, 0);
            camera.position.set(screenCenter);
        }

        //displace health and score HUD elements
        healthBar.setHealth(currLoader.getPlayerHealth());
        healthBar.render(uiCamera, spriteBatch);
        float screenWidth = ScreenProjectionHandler.getWorldWidth();
        float screenHeight = ScreenProjectionHandler.getWorldHeight();
        String text = timeTrialMode ? TimeManager.secondsToTime(time) : String.valueOf(score);
        spriteBatch.begin();
        fontHandler.getFont().draw(spriteBatch, text,
                screenWidth - layout.width - 5, screenHeight - layout.height);
        spriteBatch.end();
    }

    /**
     * Communicates to the ScreenManager what screen should be shown
     * @return GAME_LOOP is alive; GAME_OVER if dead
     */
    public ScreenState getScreenState(){
        if(timeTrialMode && score == 16){
            return ScreenState.SUCCESS;
        }
        return currLoader.getPlayerHealth() <= 0 ? ScreenState.GAME_OVER : ScreenState.GAME_LOOP;
    }

    /**
     * Resets the state of a loader
     * @param health health of the player within the loader
     */
    public void refreshRandomLoader(int health) {
        if(!timeTrialMode) {
            currLoader = levelLoaders.get((int) (Math.random() * levelsUnlocked));
        } else {
            currLoader = levelLoaders.get(score);
        }
        currLoader.setPlayerHealth(health);
        currLoader.generateMap();
    }

    /**
     * Gets the current number of levels beat
     * @return levels beat as an Integer
     */
    public int getScore() {
        return score;
    }

    public void setCurrLoader(LevelLoader currLoader) {
        this.currLoader = currLoader;
    }

    public LevelLoader getCurrLoader() {
        return currLoader;
    }

    public ArrayList<LevelLoader> getLevelLoaders() {
        return levelLoaders;
    }

    public void setTimeTrialMode(boolean timeTrialMode) {
        this.timeTrialMode = timeTrialMode;
    }

    public float getTime() {
        return time;
    }

    public boolean isTimeTrialMode() {
        return timeTrialMode;
    }
}
