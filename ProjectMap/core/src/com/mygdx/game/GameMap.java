package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
public class GameMap extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;


	
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		TiledMap WorldMap = new TmxMapLoader().load("1080x1920.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(WorldMap);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		MoveCamera();

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	public boolean keyDown(int keycode){
		return false;
	}


	public void MoveCamera(){
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			camera.translate(-20,0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			camera.translate(20, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			camera.translate(0, 20, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			camera.translate(0, -20, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			camera.zoom -= 0.02;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
