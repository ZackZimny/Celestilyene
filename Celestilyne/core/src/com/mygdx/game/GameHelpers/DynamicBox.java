package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.math.Vector2;

public class DynamicBox extends Box {
    private Vector2 movement;
    public DynamicBox(float x, float y, float width, float height, Vector2 movement) {
        super(x, y, width, height);
        this.movement = movement;
    }

    public DynamicBox(Vector2 position, float width, float height, Vector2 movement){
        super(position, width, height);
        this.movement = movement;
    }

    public Vector2 getMovement() {
        return movement;
    }
}
