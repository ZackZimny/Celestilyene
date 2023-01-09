package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameHelpers.Record;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RecordScreen extends Screen {
    private ArrayList<Record> records;
    private FontHandler fontHandler;
    public RecordScreen(ArrayList<Record> records){
        super("Records", ScreenState.RECORDS);
        this.records = records;
        LinkedHashMap<String, ScreenState> screenStateHashMap = new LinkedHashMap<>();
        screenStateHashMap.put("Return to Main Menu", ScreenState.MAIN_MENU);
        createButtonColumn(screenStateHashMap);
        fontHandler = new FontHandler(15, Color.BLACK);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        GlyphLayout layout = new GlyphLayout();
        int i = 0;
        for(Record record : records){
            float paneY = ScreenProjectionHandler.getWorldHeight() - 200 - 60 * i;
            float paneX = 25;
            float height = 50;
            float padding = 10;
            shapeRenderer.begin();
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.MAGENTA);
            shapeRenderer.rect(paneX, paneY, ScreenProjectionHandler.getWorldWidth() - paneX * 2, height);
            shapeRenderer.end();
            spriteBatch.begin();
            String nameString = i + 1 + ". " + record.getName();
            layout.setText(fontHandler.getFont(), nameString);
            fontHandler.getFont().draw(spriteBatch, nameString, paneX + padding, paneY - layout.height + height);
            String scoreString = String.valueOf(record.getScore());
            layout.setText(fontHandler.getFont(), scoreString);
            fontHandler.getFont().draw(spriteBatch, scoreString,
                    ScreenProjectionHandler.getWorldWidth() - layout.width - paneX - padding, paneY - layout.height + height);
            spriteBatch.end();
            if(i >= 6)
                break;
            i++;
        }
        super.render(shapeRenderer, spriteBatch);
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
}
