package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.Entities.*;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Loads and displays levels during the GameLoop
 */
public class LevelLoader {
    //TileLayer containing the entities within the screen
    private final TiledMapTileLayer objectLayer;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    //entities that can not be walked through
    private final ArrayList<Entity> unpassableEntities = new ArrayList<>();
    private Player player;
    private int playerHealth;
    private final AssetManagerHandler assetManagerHandler;
    private final GameSounds gameSounds;
    private final String fileName;

    /**
     * Creates a new LevelLoader
     * @param fileName name of the file with the Tilemap in it with file extension .tmx
     * @param playerHealth health that the player should load in with
     * @param assetManagerHandler assetManagerHandler with all assets loaded in
     * @param gameSounds all gameSounds loaded in
     */
    public LevelLoader(String fileName, int playerHealth, AssetManagerHandler assetManagerHandler, GameSounds gameSounds){
        TiledMap tiledMap = new TmxMapLoader().load(fileName);
        objectLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        this.playerHealth = playerHealth;
        this.assetManagerHandler = assetManagerHandler;
        this.gameSounds = gameSounds;
        this.fileName = fileName;
    }

    /**
     * generates the map based on the data passed in through the .tmx file
     */
    public void generateMap() throws InputMismatchException {
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        for(int i = objectLayer.getHeight() - 1; i >= 0; i--){
            for(int j = 0; j < objectLayer.getWidth(); j++){
                TiledMapTileLayer.Cell cell = objectLayer.getCell(j, i);
                if(cell != null) {
                    int id = cell.getTile().getId();
                    Entity entity = generateEntity(id, j, i);
                    entities.add(entity);
                }
            }
        }
    }

    /**
     * gets a texture from the assetManager reference
     * @param fileName name of the texture file
     * @return Texture object connected to the fileName
     */
    private Texture getTexture(String fileName){
        return assetManagerHandler.getAssetManager().get(fileName, Texture.class);
    }

    /**
     * Generates the entity within the position given
     * @param id id number of the entity; determines which entity to generate
     * @param x x position of the entity in a 32x32 grid
     * @param y y position of the entity in a 32x32 grid
     * @return Entity created at the position and id number
     * @throws InputMismatchException if an id number does not match up with any of the defined id numbers 1-6
     */
    private Entity generateEntity(int id, int x, int y) throws InputMismatchException {
        switch(id){
            case 1:
                BabyDragonBlue babyDragonBlue = new BabyDragonBlue(x*32, y*32, assetManagerHandler);
                enemies.add(babyDragonBlue);
                return babyDragonBlue;
            case 2:
                player = new Player(x * 32, y * 32, playerHealth, assetManagerHandler, gameSounds);
                return player;
            case 3:
                BabyDragon babyDragon = new BabyDragon(x * 32, y * 32, assetManagerHandler);
                enemies.add(babyDragon);
                return babyDragon;
            case 4:
                Mage mage = new Mage(x * 32, y * 32, assetManagerHandler);
                enemies.add(mage);
                return mage;
            case 5:
                Entity tree = new Entity(x * 32, y * 32, 42, 42, -11, -11);
                tree.setTexture(getTexture("Tree.png"));
                unpassableEntities.add(tree);
                return tree;
            case 6:
                Enemy slime = new Enemy(x*32, y*32, 32, 32, 100, getTexture("Slime.png"));
                enemies.add(slime);
                return slime;
        }
        throw new InputMismatchException("There is an undefined tile number within this map" + fileName);
    }

    /**
     * Displays the level to the screen
     * @param camera game camera that displays the whole game
     * @param spriteBatch renders the level
     */
    public void render(Camera camera, SpriteBatch spriteBatch){
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        for(Entity e : entities){
            e.render(spriteBatch);
        }
    }

    /**
     * handles all position updates and physics within the game
     * @param deltaTime change in time in seconds between the current frame and the last frame
     */
    public void handlePhysics(float deltaTime){
        player.move(deltaTime, unpassableEntities);
        ArrayList<Enemy> deadEnemies = new ArrayList<>();
        for(Enemy e : enemies){
            e.handlePhysics(deltaTime, player, unpassableEntities);
            if(e.isDead())
                deadEnemies.add(e);
        }
        enemies.removeAll(deadEnemies);
        entities.removeAll(deadEnemies);
    }

    /**
     * determines if all enemies have been killed
     * @return if all enemies have been killed
     */
    public boolean isCompleted(){
        return enemies.isEmpty();
    }

    /**
     * gets the current player health
     * @return current player health
     */
    public int getPlayerHealth(){ return player.getHealth(); }

    /**
     * replaces the current player health with a new value
     * @param playerHealth new player health value
     */
    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }


}
