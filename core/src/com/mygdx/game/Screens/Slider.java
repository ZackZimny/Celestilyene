package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;

public class Slider extends MouseInteraction {
    private int percentage = 50;
    private final float x;
    private final float y;
    private final String text;
    private final BitmapFont font;
    private float circleX;
    private GlyphLayout layout;

    /**
     * displays a 100 pixel width slider at the specified position
     * @param x x position of the bottom left corner
     * @param y y position of the bottom left corner
     * @param text text to display by the slider
     * @param font font to display text
     */
    public Slider(float x, float y, String text, BitmapFont font){
        super(x, y - 20f, 100, 40f);
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        circleX = percentage + x;
        layout = new GlyphLayout();
        layout.setText(font, text);
    }

    /**
     * Displays the slider to the screen
     * @param shapeRenderer shapeRenderer to render the slider
     * @param spriteBatch SpriteBatch to render the text
     */
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        handleInput();
        float rectHeight = 4;
        spriteBatch.begin();
        font.draw(spriteBatch, text, x + width + 20, y + layout.height / 2f);
        spriteBatch.end();
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, rectHeight);
        float radius = 10;
        shapeRenderer.circle(circleX, y, radius);
        shapeRenderer.end();
    }

    /**
     * keeps the specified value within a certain range
     * @param value value to keep in the range
     * @param min lowest possible number the value can be
     * @param max highest number the value can be
     * @return value within the clamp range
     */
    private float clamp(float value, float min, float max){
        if(value < min)
            return min;
        else if(value > max)
            return max;
        return value;
    }

    /**
     * changes the percentage if the slider is clicked
     */
    public void handleInput(){
        Vector2 mousePos = ScreenProjectionHandler.getMousePos();
        if(isHovered() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            circleX = clamp(mousePos.x, x, x + width);
            percentage =  Math.round(circleX - x);
        }
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        circleX = percentage + x;
    }
}