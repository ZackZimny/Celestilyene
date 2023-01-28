package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Button that interacts with the mouse and can be clicked or hovered
 */
public class Button extends MouseInteraction {
    //used for text centering
    private final GlyphLayout layout = new GlyphLayout();
    private final String text;
    private final Color bgColor;
    //used to create text
    private final FontHandler fontHandler = new FontHandler();
    private final ScreenState screenState;
    /**
     * Creates a Button at the specified position
     * @param x x position of the bottom left corner of the button
     * @param y y position of the bottom left corner of the button
     * @param width width of the button's box
     * @param height height of the button's box
     * @param text text to be displayed in the center of the button
     * @param bgColor color of the box behind the button
     */
    public Button(float x, float y, float width, float height, String text, Color bgColor, ScreenState screenState) {
        super(x, y, width, height);
        this.text = text;
        this.bgColor = bgColor;
        layout.setText(fontHandler.getFont(), text);
        this.screenState = screenState;
    }

    /**
     * Displays the button to the screen
     * @param shapeRenderer shapeRenderer to draw the button's box
     * @param spriteBatch spriteBatch to draw the text of the button
     */
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch){
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(bgColor);
        shapeRenderer.rect(x, y ,width, height);
        shapeRenderer.end();
        spriteBatch.begin();
        fontHandler.getFont().draw(spriteBatch, text, x + width / 2 - layout.width / 2,
                y + height / 2 + layout.height / 2);
        spriteBatch.end();
    }

    /**
     * gets what screen this button should transition to when clicked
     * @return ScreenState of screen change
     */
    public ScreenState getScreenState() {
        return screenState;
    }
}
