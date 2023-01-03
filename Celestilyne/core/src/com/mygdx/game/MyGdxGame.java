package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Entities.*;
import com.mygdx.game.GameHelpers.LevelLoader;
import com.badlogic.gdx.audio.Music;
import java.util.ArrayList;
import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {
	// initializes variables
	Player player;
	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Entity> unpassableEntities = new ArrayList<>();
	Entity tree;
	ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	AssetManager assetManager;
	SpriteBatch spriteBatch;
	LevelLoader levelLoader;
	Music GameMusic;
	HealthStat HealthBar;


	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		assetManager = new AssetManager();

		// setup for camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

		spriteBatch = new SpriteBatch();
		levelLoader = new LevelLoader("Easy6.tmx");
		levelLoader.generateMap();

		// Handles Music - one song playing on loops
		GameMusic = Gdx.audio.newMusic(Gdx.files.internal("ZimzMusic2 copy 3.wav"));
		GameMusic.setLooping(true);
		GameMusic.play();
		//creates 3 Hearts for HP, but HP won't go down yet - need Zack to code collision
		HealthBar = new HealthStat();
		HealthBar.CreateHealth(100, 400, 200, 100);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.1f, 0.6f, 0.2f, 1);

		Healthbar.renderHealth();

		levelLoader.handlePhysics(Gdx.graphics.getDeltaTime(), shapeRenderer);
		shapeRenderer.setAutoShapeType(true);

		//begins and ends rendering
		spriteBatch.begin();
		levelLoader.render(camera, spriteBatch, shapeRenderer);
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		spriteBatch.dispose();		//disposes assets after program is done using them
		assetManager.dispose();
	}
}
