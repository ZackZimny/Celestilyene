package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HealthStat{
    private int health;
    private int x, y;
    private Texture texture;
    public HealthStat(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
        texture = new Texture(Gdx.files.internal("heart.png"));
    }

    public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for(int i = 0; i < health; i++){
            spriteBatch.draw(texture, x + 36 * i, y);
        }
        spriteBatch.end();
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
