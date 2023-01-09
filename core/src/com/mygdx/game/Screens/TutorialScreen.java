package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TutorialScreen extends Screen {
    private FontHandler fontHandler;
    private String[] lines;
    private Texture image;
    public TutorialScreen(String title, ScreenState defaultScreen, ScreenState previousScreen, ScreenState nextScreen,
                          String text) {
        super(title, defaultScreen);
        fontHandler = new FontHandler(15, Color.BLACK);
        LinkedHashMap<String, ScreenState> buttonStateHashMap = new LinkedHashMap<>();
        buttonStateHashMap.put("Next", nextScreen);
        buttonStateHashMap.put("Back", previousScreen);
        createButtonColumn(buttonStateHashMap);
        lines = text.split("\\.");
        for(int i = 1; i < lines.length; i++){
            lines[i] = lines[i].substring(1);
        }
    }

    public TutorialScreen(String title, ScreenState defaultScreen, ScreenState previousScreen, ScreenState nextScreen,
                          String text, Texture image){
        super(title, defaultScreen);
        this.image = image;
        fontHandler = new FontHandler(15, Color.BLACK);
        LinkedHashMap<String, ScreenState> buttonStateHashMap = new LinkedHashMap<>();
        buttonStateHashMap.put("Next", nextScreen);
        buttonStateHashMap.put("Back", previousScreen);
        createButtonColumn(buttonStateHashMap);
        lines = text.split("\\.");
        for(int i = 1; i < lines.length; i++){
            lines[i] = lines[i].substring(1);
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for(int i = 0; i < lines.length; i++){
            fontHandler.getFont().draw(spriteBatch, lines[i], 20, 330 - 25 * i);
        }
        spriteBatch.end();
        spriteBatch.begin();
        if(image != null) {
            float imageDivider = 3f;
            spriteBatch.draw(image, ScreenProjectionHandler.getWorldWidth() / 2 - image.getWidth() / (2f * imageDivider),
                    ScreenProjectionHandler.getWorldHeight() - 375,
                    image.getWidth() / imageDivider, image.getHeight() / imageDivider);
        }
        spriteBatch.end();
        super.render(shapeRenderer, spriteBatch);
    }
}
