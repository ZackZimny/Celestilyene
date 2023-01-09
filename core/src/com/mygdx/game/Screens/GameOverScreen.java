package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameHelpers.GameSounds;

import java.util.LinkedHashMap;

public class GameOverScreen extends Screen {
    private LinkedHashMap<String, ScreenState> screenStateMap = new LinkedHashMap<>();
    private int score;

    public GameOverScreen(){
        super("Game Over", ScreenState.GAME_OVER);
        screenStateMap.put("Play Again", ScreenState.GAME_LOOP);
        screenStateMap.put("Return to Main Menu", ScreenState.MAIN_MENU);
        createButtonColumn(screenStateMap);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        ScreenManager.renderBackground(shapeRenderer, Color.RED);
        String scoreString = String.format("Your Score: %d", score);
        spriteBatch.begin();
        getTitleFontHandler().getFont().draw(spriteBatch, scoreString,
                getCenterTextX(scoreString), ScreenProjectionHandler.getWorldHeight() - 150);
        spriteBatch.end();
        super.render(shapeRenderer, spriteBatch);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
