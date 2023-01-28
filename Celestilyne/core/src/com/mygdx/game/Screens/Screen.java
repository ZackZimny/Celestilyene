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
    private final FontHandler titleFontHandler;
    private final String title;
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final HashMap<String, Button> stringButtonHashMap = new HashMap<>();
    private final ScreenState defaultScreen;

    /**
     * Initializes screen without buttons
     * @param title title of the screen that will be displayed at the top
     * @param defaultScreen ScreenState that this screen is paired with
     */
    public Screen(String title, ScreenState defaultScreen){
        titleFontHandler = new FontHandler(30, Color.BLACK);
        this.title = title;
        this.defaultScreen = defaultScreen;
    }

    /**
     * Initializes buttons in correct positions
     * @param stringStateHashMap linkedHashMap of button names and their associated screen redirects
     */
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

    /**
     * Displays the title at the top of the screen
     * @param spriteBatch displays text
     */
    public void renderTitle(SpriteBatch spriteBatch){
        spriteBatch.begin();
        float screenWidth = ScreenProjectionHandler.getWorldWidth();
        float textY = ScreenProjectionHandler.getWorldHeight() - titleFontHandler.getFont().getLineHeight();
        float textX = titleFontHandler.centerX(title, screenWidth);
        titleFontHandler.getFont().draw(spriteBatch, title, textX, textY);
        spriteBatch.end();
    }

    /**
     * renders the buttons in their correct position and color
     * @param shapeRenderer displays rectangles
     * @param spriteBatch displays text
     */
    public void renderButtons(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        for(Button button : buttons){
            button.render(shapeRenderer, spriteBatch);
        }
    }

    /**
     * renders all screen components
     * @param shapeRenderer displays button rectangles
     * @param spriteBatch displays text
     */
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        renderTitle(spriteBatch);
        renderButtons(shapeRenderer, spriteBatch);
    }

    /**
     * Gets the correct x position to center a piece of text using the default font
     * @param text text to center
     * @return x position to start rendering the text
     */
    public float getCenterTextX(String text){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(getTitleFontHandler().getFont(), text);
        return ScreenProjectionHandler.getWorldWidth() / 2f - layout.width / 2f;
    }

    /**
     * Gets the correct x position to center a piece of text using the passed in font
     * @param text text to center
     * @param font
     * @return x position to start rendering the text
     */
    public float getCenterTextX(String text, BitmapFont font){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return ScreenProjectionHandler.getWorldWidth() / 2f - layout.width / 2f;
    }

    /**
     * gets the screen state depending on the state of the buttons
     * @return ScreenState redirect of the button pressed, or the default ScreenState if no buttons are pressed
     */
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
