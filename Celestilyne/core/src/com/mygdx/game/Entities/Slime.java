package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Slime enemy that moves towards the player and attacks
 */
public class Slime extends Enemy {
    /**
     * Creates a slime enemy at the given position and texture
     * @param x x position of the left side
     * @param y y position of the bottom
     * @param texture image to display as the slime
     */
    public Slime(int x, int y, Texture texture){
        super(x, y, 32, 32, 100, texture);
    }
}
