package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Entities.*;
import com.mygdx.game.Screens.AssetManagerHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelLoader {
    private TiledMap tiledMap;
    private TiledMapTileLayer objectLayer;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Entity> unpassableEntities = new ArrayList<>();
    private int[][] unpassableMap;
    private Player player;
    private int playerHealth;
    private AssetManagerHandler assetManagerHandler;
    private GameSounds gameSounds;
    public LevelLoader(String fileName, int playerHealth, AssetManagerHandler assetManagerHandler, GameSounds gameSounds){
        tiledMap = new TmxMapLoader().load(fileName);
        objectLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        unpassableMap = new int[objectLayer.getHeight()][objectLayer.getWidth()];
        this.playerHealth = playerHealth;
        this.assetManagerHandler = assetManagerHandler;
        this.gameSounds = gameSounds;
    }

    public void generateMap(){
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

    private Texture getTexture(String fileName){
        return assetManagerHandler.getAssetManager().get(fileName, Texture.class);
    }

    private Entity generateEntity(int id, int x, int y){
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
                tree.setPassable(true);
                unpassableEntities.add(tree);
                unpassableMap[y][x] = 1;
                return tree;
            case 6:
                Enemy slime = new Enemy(x*32, y*32, 32, 32, 100, getTexture("Slime.png"));
                enemies.add(slime);
                return slime;
        }
        System.out.println("Entity not found " + id);
        return new Entity(0, 0, 0, 0);
    }

    public void render(Camera camera, SpriteBatch spriteBatch){
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        for(Entity e : entities){
            e.render(spriteBatch);
        }
    }

    public void handlePhysics(float deltaTime, ShapeRenderer shapeRenderer){
        player.move(deltaTime, unpassableEntities, shapeRenderer);
        ArrayList<Enemy> deadEnemies = new ArrayList<>();
        for(Enemy e : enemies){
            e.handlePhysics(deltaTime, player, unpassableEntities, unpassableMap);
            if(e.isDead())
                deadEnemies.add(e);
        }
        enemies.removeAll(deadEnemies);
        entities.removeAll(deadEnemies);
    }

    public boolean isCompleted(){
        return enemies.isEmpty();
    }

    public int getPlayerHealth(){ return player.getHealth(); }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }


}
