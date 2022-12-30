package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Entities.*;

import java.util.ArrayList;
import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {

	Player player;
	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Entity> unpassableEntities = new ArrayList<>();
	Entity tree;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	AssetManager assetManager;
	SpriteBatch spriteBatch;
	@Override
	public void create () {
		player = new Player();
		enemies.add(new Mage(100, 100));
		enemies.add(new BabyDragon(50, 100));
		enemies.add(new BabyDragonBlue(150, 100));
		enemies.add(new Enemy(200, 200, 32, 32, 100, "Slime.png"));
		tree = new Entity(250, 250, 64, 64);
		tree.setTexture(new Texture(Gdx.files.internal("Tree.png")));
		shapeRenderer = new ShapeRenderer();
		assetManager = new AssetManager();
		camera = new OrthographicCamera();
		spriteBatch = new SpriteBatch();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		unpassableEntities.add(tree);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.5f, 0.8f, 0.5f, 1);
		for (Enemy enemy : enemies) {
			enemy.handlePhysics(Gdx.graphics.getDeltaTime(), player, unpassableEntities);
		}
		player.move(Gdx.graphics.getDeltaTime(), unpassableEntities, shapeRenderer);
		camera.position.set(player.getHurtBox().getCenter(), 0);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		tree.render(spriteBatch);
		player.render(spriteBatch);
		for (Enemy enemy : enemies) {
			enemy.render(spriteBatch);
		}
		Iterator<Enemy> enemyIterator = enemies.iterator();
		while(enemyIterator.hasNext()){
			if(enemyIterator.next().isDead()){
				enemyIterator.remove();
			}
		}
		spriteBatch.end();
		player.showBoxes(shapeRenderer);
		for(Enemy enemy : enemies){
			enemy.showBoxes(shapeRenderer);
		}
		tree.showBoxes(shapeRenderer);
	}
	
	@Override
	public void dispose () {
	}
}
