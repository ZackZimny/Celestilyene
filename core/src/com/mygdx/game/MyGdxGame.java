package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screens.FontHandler;
import com.mygdx.game.Screens.ScreenManager;
import com.mygdx.game.Screens.ScreenProjectionHandler;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {
	// initializes variables
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private ScreenManager screenManager;
	private FontHandler fontHandler;
	private GlyphLayout glyphLayout;
	private String loadingText = "Loading...";
	private boolean loaded = false;
	private int screenWidth = (int) ScreenProjectionHandler.getWorldWidth();
	private int screenHeight = (int) ScreenProjectionHandler.getWorldHeight();
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		fontHandler = new FontHandler();
		glyphLayout = new GlyphLayout();
		glyphLayout.setText(fontHandler.getFont(), loadingText);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		screenManager = new ScreenManager();
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		ScreenProjectionHandler.getAspectRatioViewport().apply();
		if(!loaded) {
			screenManager.getAssetManagerHandler().getAssetManager().finishLoading();
			screenManager.load();
			screenManager.loadGameLoop();
			loaded = true;
		} else {
			if(screenManager.isLoaded())
				screenManager.render(spriteBatch, shapeRenderer);
		}
	}

	@Override
	public void resize(int width, int height){
		ScreenProjectionHandler.getAspectRatioViewport().update(width, height, true);
		screenWidth = width;
		screenHeight = height;
	}
	@Override
	public void dispose () {
	}
}
