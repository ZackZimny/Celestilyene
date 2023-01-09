package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Checkbox extends MouseInteraction {
    private Texture texture;
    private TextureRegion onTexture;
    private TextureRegion offTexture;
    private TextureRegion currTexture;
    private BitmapFont font;
    private boolean on = false;
    private String text;
    private GlyphLayout layout = new GlyphLayout();
    public Checkbox(float x, float y, String text, BitmapFont font) {
        super(x, y, 32, 32);
        this.text = text;
        this.font = font;
        texture = new Texture(Gdx.files.internal("Checkbox.png"));
        TextureRegion[][] textureRegions = TextureRegion.split(texture, 32, 32);
        offTexture = textureRegions[0][0];
        onTexture = textureRegions[0][1];
        currTexture = offTexture;
        layout.setText(font, text);
    }

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
