package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameHelpers.Boxes.Box;

public class MouseInteraction extends Box {
    public MouseInteraction(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public MouseInteraction(Vector2 position, float width, float height) {
        super(position, width, height);
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
