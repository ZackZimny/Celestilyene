package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Handles the resizing and recoloring of the "FFFFORWA.TTF" font
 */
public class FontHandler {
    private final BitmapFont font;
    private final FreeTypeFontGenerator fontGenerator;
    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private final GlyphLayout layout = new GlyphLayout();

    /**
     * Loads the font from the "FFFFORWA.TTF" FILE
     */
    public FontHandler(){
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("FFFFORWA.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.BLACK;
        font = fontGenerator.generateFont(parameter);
    }

    public FontHandler(int size, Color color){
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("FFFFORWA.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;
        font = fontGenerator.generateFont(parameter);
    }

    /**
     * gets the x to center the text from the screen width
     * @param text text to display on the screen
     * @param screenWidth width of the screen that the text is being centered on
     * @return x to center the text on the screen
     */
    public float centerX(String text, float screenWidth){
        layout.setText(font, text);
        return screenWidth / 2 - layout.width / 2;
    }

    public BitmapFont getFont() {
        return font;
    }
}
