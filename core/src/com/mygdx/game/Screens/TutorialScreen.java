package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedHashMap;

/**
 * Provides a template for creating Tutorial screens
 */
public class TutorialScreen extends Screen {
    private final FontHandler fontHandler;
    private final String[] lines;
    private Texture image;

    /**
     * Initializes the tutorial screen without an image
     * @param title title at the top of the screen
     * @param defaultScreen associated ScreenState
     * @param previousScreen previous ScreenState navigated to with back button
     * @param nextScreen next ScreenState navigated to with next button
     * @param text informational text to display
     */
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

    /**
     * Initializes the tutorial screen with an image
     * @param title title at the top of the screen
     * @param defaultScreen associated ScreenState
     * @param previousScreen previous ScreenState navigated to with back button
     * @param nextScreen next ScreenState navigated to with next button
     * @param text informational text to display
     * @param image image to be displayed above the middle of the screen
     */
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


    /**
     * displays this screen
     * @param shapeRenderer displays button rectangles
     * @param spriteBatch displays text and images
     */
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
