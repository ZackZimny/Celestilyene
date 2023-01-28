package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedHashMap;

public class TimeTrialSuccessScreen extends Screen{
    private String time = "not set";
    /**
     * initializes the gameOver screen
     */
    public TimeTrialSuccessScreen(){
        super("Success!", ScreenState.SUCCESS);
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
        ScreenManager.renderBackground(shapeRenderer, Color.LIME);
        String scoreString = "Your Time: " + time;
        spriteBatch.begin();
        getTitleFontHandler().getFont().draw(spriteBatch, scoreString,
                getCenterTextX(scoreString), ScreenProjectionHandler.getWorldHeight() - 150);
        spriteBatch.end();
        super.render(shapeRenderer, spriteBatch);
    }

    public void setTime(float seconds) {
        int minutes = (int) (seconds / 60);
        seconds -= minutes * 60;
        time = String.format("%d:%.2f", minutes, seconds);
    }
}
