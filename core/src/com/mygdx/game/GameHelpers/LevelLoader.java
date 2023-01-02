package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Entities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelLoader {
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer decorationLayer;
    private TiledMapTileLayer objectLayer;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Entity> unpassableEntities = new ArrayList<>();
    private Player player;
    public LevelLoader(String fileName){
        tiledMap = new TmxMapLoader().load(fileName);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
        decorationLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        objectLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Object Layer");
    }

    public void generateMap(){
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

    private Entity generateEntity(int id, int x, int y){
        switch(id){
            case 3:
                BabyDragon babyDragon = new BabyDragon(x * 32, y * 32);
                enemies.add(babyDragon);
                return babyDragon;
            case 4:
                BabyDragonBlue babyDragonBlue = new BabyDragonBlue(x*32, y*32);
                enemies.add(babyDragonBlue);
                return babyDragonBlue;
            case 5:
                Mage mage = new Mage(x*32, y*32);
                enemies.add(mage);
                return mage;
            case 6:
                Player player1 = new Player(x * 32, y * 32);
                player = player1;
                return player1;
            case 7:
                Entity tree = new Entity(x * 32, y * 32, 64, 64);
                tree.setTexture(new Texture(Gdx.files.internal("Tree.png")));
                tree.setPassable(true);
                unpassableEntities.add(tree);
                return tree;
            case 9:
                Enemy slime = new Enemy(x*32, y*32, 32, 32, 50, "Slime.png");
                enemies.add(slime);
                return slime;
            case 11:
                Entity road = new Entity(x * 32, y * 32, 64, 64);
                road.setTexture(new Texture(Gdx.files.internal("Road.png")));
                return road;
        }
        System.out.println("Entity not found " + id);
        return new Entity(0, 0, 0, 0);
    }

    public void render(OrthographicCamera camera, SpriteBatch spriteBatch){
        //tiledMapRenderer.renderTileLayer(decorationLayer);
        tiledMapRenderer.setView(camera);
        for(Entity e : entities){
            if(e.getTexture() != null)
                e.render(spriteBatch);
        }
    }

    public void handlePhysics(float deltaTime, ShapeRenderer shapeRenderer){
        player.move(deltaTime, unpassableEntities, shapeRenderer);
        ArrayList<Enemy> deadEnemies = new ArrayList<>();
        for(Enemy e : enemies){
            e.handlePhysics(deltaTime, player, unpassableEntities);
            if(e.isDead())
                deadEnemies.add(e);
        }
        enemies.removeAll(deadEnemies);
        entities.removeAll(deadEnemies);
    }
}
