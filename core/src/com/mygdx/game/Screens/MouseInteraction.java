package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameHelpers.Boxes.Box;

/**
 * Handles mouse interaction with UI elements
 */
public class MouseInteraction extends Box {

    /**
     * Initializes mouseInteraction
     * @param x left-hand side x position
     * @param y bottom side y position
     * @param width width of the collision box
     * @param height height of the collision box
     */
    public MouseInteraction(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    /**
     * @return if the mouse is within the bounds of the button
     */
    public boolean isHovered(){
        return intersects(ScreenProjectionHandler.getMousePos());
    }

    /**
     * @return if the mouse is within the bounds of the button and is the mouse is clicked
     */
    public boolean isClicked() {
        return isHovered() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }
}
