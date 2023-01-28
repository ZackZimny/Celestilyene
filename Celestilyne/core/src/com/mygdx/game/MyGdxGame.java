package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameHelpers.CrashLogHandler;
import com.mygdx.game.GameHelpers.EventLogHandler;
import com.mygdx.game.Screens.ScreenManager;
import com.mygdx.game.Screens.ScreenProjectionHandler;

import java.util.InputMismatchException;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {
	// initializes variables
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private ScreenManager screenManager;
	@Override
	public void create () {
		try {
			CrashLogHandler.start();
			EventLogHandler.start();
			spriteBatch = new SpriteBatch();
			shapeRenderer = new ShapeRenderer();
			shapeRenderer.setAutoShapeType(true);
			screenManager = new ScreenManager();
			try {
				screenManager.getAssetManagerHandler().getAssetManager().finishLoading();
				screenManager.load();
				screenManager.loadGameLoop();
			} catch (GdxRuntimeException e){
				CrashLogHandler.logSevere("There was an error while loading in assets: ", e.getMessage());
			} catch (InputMismatchException e){
				CrashLogHandler.logSevere("There was an error while loading the tileset: ", e.getMessage());
			}
		} catch (Exception e){
			CrashLogHandler.logSevere("There was an error during the loading process: ", e.getMessage());
		}
		EventLogHandler.log("Assets have been properly loaded.");
	}

	@Override
	public void render () {
		try {
			ScreenUtils.clear(Color.BLACK);
			ScreenProjectionHandler.getAspectRatioViewport().apply();
			screenManager.render(spriteBatch, shapeRenderer);
		} catch (Exception e){
			CrashLogHandler.logSevere("There was an error during the rendering process. ", e.getMessage());
		}
	}

	@Override
	public void resize(int width, int height){
		ScreenProjectionHandler.getAspectRatioViewport().update(width, height, true);
	}
}
