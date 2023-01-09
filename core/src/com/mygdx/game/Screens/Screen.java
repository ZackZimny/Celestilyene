package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameHelpers.GameSounds;

import java.util.*;

/**
 * holds the screen data, handles the fonts, and renders the background for navigation
 */
public class Screen {
    private FontHandler titleFontHandler;
    private String title;
    private ArrayList<Button> buttons = new ArrayList<>();
    private HashMap<String, Button> stringButtonHashMap = new HashMap<>();
    private ScreenState defaultScreen;

    public Screen(String title, ScreenState defaultScreen){
        titleFontHandler = new FontHandler(30, Color.BLACK);
        this.title = title;
        this.defaultScreen = defaultScreen;
    }

    protected void createButtonColumn(LinkedHashMap<String, ScreenState> stringStateHashMap){
        float screenWidth = ScreenProjectionHandler.getWorldWidth();
        float screenHeight = ScreenProjectionHandler.getWorldHeight();
        float buttonX = 20;
        float buttonHeight = screenHeight * 0.1f;
        Color[] colors = {Color.BLUE, new Color(1, 0.5f, 1, 1), Color.LIGHT_GRAY, Color.PURPLE};
        float totalHeight = 40 + (stringStateHashMap.size() - 1) * (10f + buttonHeight);
        int i = 0;
        for(String text : stringStateHashMap.keySet()){
            Button newButton = new Button(buttonX,  totalHeight - ((10f + buttonHeight) * i), screenWidth - 40,
                    buttonHeight, text, colors[i % colors.length], stringStateHashMap.get(text));
            buttons.add(newButton);
            stringButtonHashMap.put(text, newButton);
            i++;
        }
    }

    public void renderTitle(SpriteBatch spriteBatch){
        spriteBatch.begin();
        float screenWidth = ScreenProjectionHandler.getWorldWidth();
        float textY = ScreenProjectionHandler.getWorldHeight() - titleFontHandler.getFont().getLineHeight();
        float textX = titleFontHandler.centerX(title, screenWidth);
        titleFontHandler.getFont().draw(spriteBatch, title, textX, textY);
        spriteBatch.end();
    }

    public void renderButtons(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        for(Button button : buttons){
            button.render(shapeRenderer, spriteBatch);
        }
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        renderTitle(spriteBatch);
        renderButtons(shapeRenderer, spriteBatch);
    }

    public float getCenterTextX(String text){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(getTitleFontHandler().getFont(), text);
        return ScreenProjectionHandler.getWorldWidth() / 2f - layout.width / 2f;
    }

    public float getCenterTextX(String text, BitmapFont font){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return ScreenProjectionHandler.getWorldWidth() / 2f - layout.width / 2f;
    }

    public ScreenState getScreenState() {
        for(Button button : buttons){
            if(button.isClicked()){
                return button.getScreenState();
            }
        }
        return defaultScreen;
    }

    public FontHandler getTitleFontHandler() {
        return titleFontHandler;
    }

    public HashMap<String, Button> getStringButtonHashMap() {
        return stringButtonHashMap;
    }

}
