package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedHashMap;

/**
 * Screen that displays once the player has lost all of his health
 */
public class GameOverScreen extends Screen {
    private int score;

    /**
     * initializes the gameOver screen
     */
    public GameOverScreen(){
        super("Game Over", ScreenState.GAME_OVER);
        LinkedHashMap<String, ScreenState> screenStateMap = new LinkedHashMap<>();
        screenStateMap.put("Play Again", ScreenState.GAME_LOOP);
        screenStateMap.put("Return to Main Menu", ScreenState.MAIN_MENU);
        createButtonColumn(screenStateMap);
    }

    /**
     * displays the GameOverScreen
     * @param shapeRenderer displays button rectangles
     * @param spriteBatch renders text
     */
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
