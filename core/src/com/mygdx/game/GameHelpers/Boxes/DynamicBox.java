package com.mygdx.game.GameHelpers.Boxes;

import com.badlogic.gdx.math.Vector2;

/**
 * Box with an extra movement variable
 * @see com.mygdx.game.GameHelpers.Boxes.Box
 */
public class DynamicBox extends Box {
    private final Vector2 movement;

    /**
     * Creates a DynamicBox at a position in the gameWorld with defined movement
     * @param x left corner x position
     * @param y bottom corner y position
     * @param width width of the box in pixels
     * @param height height of the box in pixels
     * @param movement movement variable to be calculated
     */
    public DynamicBox(float x, float y, float width, float height, Vector2 movement) {
        super(x, y, width, height);
        this.movement = movement;
    }

    /**
     * Creates a DynamicBox at a position in the gameWorld with defined movement
     * @param position position Vector2 of the bottom left-hand corner of the box
     * @param width width of the box in pixels
     * @param height height of the box in pixels
     * @param movement movement variable to be calculated
     */
    public DynamicBox(Vector2 position, float width, float height, Vector2 movement){
        super(position, width, height);
        this.movement = movement;
    }

    /**
     * gets the vector to be applied to the box's position
     * @return Vector2 position change
     */
    public Vector2 getMovement() {
        return movement;
    }
}
