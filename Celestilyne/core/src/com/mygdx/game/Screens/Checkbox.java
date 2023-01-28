package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Displays a checkBox that can be toggled on and off with the mouse with text next to it
 */
public class Checkbox extends MouseInteraction {
    private final TextureRegion onTexture;
    private final TextureRegion offTexture;
    private TextureRegion currTexture;
    private final BitmapFont font;
    private boolean on = false;
    private final String text;
    private final GlyphLayout layout = new GlyphLayout();

    /**
     * Initializes the checkBox to the screen
     * @param x x position of the left side of the box
     * @param y y position of the bottom of the box
     * @param text text to display next to the box
     * @param font font to render the text in
     */
    public Checkbox(float x, float y, String text, BitmapFont font) {
        super(x, y, 32, 32);
        this.text = text;
        this.font = font;
        Texture texture = new Texture(Gdx.files.internal("Checkbox.png"));
        TextureRegion[][] textureRegions = TextureRegion.split(texture, 32, 32);
        offTexture = textureRegions[0][0];
        onTexture = textureRegions[0][1];
        currTexture = offTexture;
        layout.setText(font, text);
    }

    /**
     * Displays the checkbox to the screen
     * @param spriteBatch spriteBatch to render the checkbox with
     */
    public void render(SpriteBatch spriteBatch){
        if(isClicked()){
            on = !on;
        }
        currTexture = on ? onTexture : offTexture;
        spriteBatch.begin();
        spriteBatch.draw(currTexture, getX(), getY(), 32, 32);
        spriteBatch.end();
        spriteBatch.begin();
        font.draw(spriteBatch,  text, getX() + 52, getY() + layout.height / 2f + 16);
        spriteBatch.end();
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
