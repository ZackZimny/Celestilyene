package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameHelpers.Record;
import com.mygdx.game.GameHelpers.TimeManager;
import com.mygdx.game.GameHelpers.TimeRecord;

import java.util.LinkedHashMap;

/**
 * Screen that shows when the executable is run
 */
public class MainMenuScreen extends Screen {
    private Record highestRecord;
    private TimeRecord highestTimeRecord;
    private final FontHandler recordFontHandler;
    private final FontHandler setByFontHandler;

    /**
     * Initializes the MainMenuScreen
     */
    public MainMenuScreen() {
        super("Celestilyne", ScreenState.MAIN_MENU);
        LinkedHashMap<String, ScreenState> titleStateHashMap = new LinkedHashMap<>();
        titleStateHashMap.put("Start", ScreenState.GAME_LOOP);
        titleStateHashMap.put("Time Trial", ScreenState.GAME_LOOP);
        titleStateHashMap.put("Tutorial", ScreenState.TUTORIAL1);
        titleStateHashMap.put("Records", ScreenState.RECORDS);
        titleStateHashMap.put("Options", ScreenState.OPTIONS);
        createButtonColumn(titleStateHashMap);
        recordFontHandler = new FontHandler(20, Color.MAGENTA);
        setByFontHandler = new FontHandler(15, Color.MAGENTA);
    }

    /**
     * Displays the screen
     * @param shapeRenderer renders button rectangles
     * @param spriteBatch displays text
     */
    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        spriteBatch.begin();
        String recordString = String.format("Top Record: %d", highestRecord.getScore());
        String setByString = String.format("Set By: %s", highestRecord.getName());
        float screenHeight = ScreenProjectionHandler.getWorldHeight();
        recordFontHandler.getFont().draw(spriteBatch, recordString,
                getCenterTextX(recordString, recordFontHandler.getFont()) - 200, screenHeight - 150);
        setByFontHandler.getFont().draw(spriteBatch, setByString, getCenterTextX(setByString,
                setByFontHandler.getFont()) - 200, screenHeight - 185);
        recordString = String.format("Fastest Time: %s", TimeManager.secondsToTime(highestTimeRecord.getTime()));
        setByString = String.format("Set By: %s", highestTimeRecord.getName());
        recordFontHandler.getFont().draw(spriteBatch, recordString,
                getCenterTextX(recordString, recordFontHandler.getFont()) + 200, screenHeight - 150);
        setByFontHandler.getFont().draw(spriteBatch, setByString, getCenterTextX(setByString,
                setByFontHandler.getFont()) + 200, screenHeight - 185);
        spriteBatch.end();
        super.render(shapeRenderer, spriteBatch);
    }

    public void setHighestRecord(Record highestRecord) {
        this.highestRecord = highestRecord;
    }

    public void setHighestTimeRecord(TimeRecord highestTimeRecord){this.highestTimeRecord = highestTimeRecord; }
}
