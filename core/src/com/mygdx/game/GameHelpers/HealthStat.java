package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * Handles the HealthStat display during the GameLoop
 */
public class HealthStat {
    private int health;
    private final int x, y;
    private final Texture texture;

    /**
     * Creates a new HealthStat display
     * @param x left hand x position
     * @param y bottom corner y position
     * @param health amount of hearts to display
     */
    public HealthStat(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
        texture = new Texture(Gdx.files.internal("Heart.png"));
    }

    /**
     * Displays the HealthStat to the screen
     * @param camera fixed position camera anchored at the default position
     * @param spriteBatch draws hearts to the screen
     */
    public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for(int i = 0; i < health; i++){
            spriteBatch.draw(texture, x + 36 * i, y);
        }
        spriteBatch.end();
    }

    /**
     * Sets the number of hearts to be displayed
     * @param health number of hearts to be displayed
     */
    public void setHealth(int health) {
        this.health = health;
    }
}
