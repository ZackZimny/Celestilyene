package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screens.AssetManagerHandler;
import com.mygdx.game.Screens.FontHandler;
import com.mygdx.game.Screens.ScreenProjectionHandler;
import com.mygdx.game.Screens.ScreenState;

import java.util.ArrayList;

public class GameLoop {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private ArrayList<LevelLoader> levelLoaders = new ArrayList<>();
    private LevelLoader currLoader;
    private HealthStat healthBar;
    private OrthographicCamera uiCamera;
    private int score = 0;
    private FontHandler fontHandler;
    private GlyphLayout layout;
    private ArrayList<LevelLoader> hardLevels = new ArrayList<>();
    private int levelsUnlocked = 8;

    public GameLoop(AssetManagerHandler assetManagerHandler, GameSounds gameSounds) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        assetManager = new AssetManager();
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

    public void render() {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.setColor(0.1f, 0.6f, 0.2f, 1);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, ScreenProjectionHandler.getWorldWidth(), ScreenProjectionHandler.getWorldHeight());
        shapeRenderer.end();

        currLoader.handlePhysics(Gdx.graphics.getDeltaTime(), shapeRenderer);

        //begins and ends rendering
        currLoader.render(camera, spriteBatch);

        if(currLoader.isCompleted()){
            int prevHealth = currLoader.getPlayerHealth();
            currLoader.generateMap();
            score++;
            layout.setText(fontHandler.getFont(), String.valueOf(score));
            if(score > 8 && score <= 16){
                levelsUnlocked++;
            }
            refreshRandomLoader(prevHealth);
        }

        healthBar.setHealth(currLoader.getPlayerHealth());
        healthBar.render(uiCamera, spriteBatch);

        float screenWidth = ScreenProjectionHandler.getWorldWidth();
        float screenHeight = ScreenProjectionHandler.getWorldHeight();
        spriteBatch.begin();
        fontHandler.getFont().draw(spriteBatch, String.valueOf(score),
                screenWidth - layout.width - 5, screenHeight - layout.height);
        spriteBatch.end();
    }

    public ScreenState getScreenState(){
        return currLoader.getPlayerHealth() <= 0 ? ScreenState.GAME_OVER : ScreenState.GAME_LOOP;
    }

    public void refreshRandomLoader(int health) {
        currLoader = levelLoaders.get((int) (Math.random() * levelsUnlocked));
        currLoader.setPlayerHealth(health);
        currLoader.generateMap();
    }

    public int getScore() {
        return score;
    }
}
