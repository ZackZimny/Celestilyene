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
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		assetManager = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		spriteBatch = new SpriteBatch();
		levelLoader = new LevelLoader("Easy6.tmx");
		levelLoader.generateMap();
		GameMusic = Gdx.audio.newMusic(Gdx.files.internal("ZimzMusic2 copy 3.wav"));
		GameMusic.setLooping(true);
		GameMusic.play();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.1f, 0.6f, 0.2f, 1);
		levelLoader.handlePhysics(Gdx.graphics.getDeltaTime(), shapeRenderer);
		shapeRenderer.setAutoShapeType(true);
		spriteBatch.begin();
		levelLoader.render(camera, spriteBatch, shapeRenderer);
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		assetManager.dispose();
	}
}