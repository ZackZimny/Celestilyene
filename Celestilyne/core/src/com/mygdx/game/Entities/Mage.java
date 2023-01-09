package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;
import com.mygdx.game.Screens.AssetManagerHandler;

import java.util.ArrayList;

public class Mage extends Enemy {
    private Texture texture;
    private float stateTime = 0f;
    private boolean isAttacking = false;
    private TextureRegion currentFrame;
    private Texture magicTexture;
    private TextureRegion[] frames;
    public Mage(float x, float y, AssetManagerHandler assetManagerHandler) {
        super(x, y, 30, 32, 60, assetManagerHandler.getTexture("Mage.png"));
        texture = assetManagerHandler.getTexture("Mage.png");
        frames = TextureRegion.split(texture, 32, 32)[0];
        currentFrame = frames[0];
        magicTexture = assetManagerHandler.getTexture("Fireball.png");
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(stateTime < 20 / 60f && isAttacking){
            currentFrame = frames[1];
        } else if(isAttacking && stateTime > 10 / 60f){
            currentFrame = frames[0];
            isAttacking = false;
        }
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, getHurtBox().getX(), getHurtBox().getY());
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(magicTexture, hitBox.getX(), hitBox.getY());
        }
        spriteBatch.end();
    }

    @Override
    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities, int[][] unpassableMap) {
        stateTime += deltaTime;
        if(stateTime > 2){
            isAttacking = true;
            for(int i = 0; i < 15; i++){
                double angle = Math.PI * 2 / 15f * i;
                addHitBox(new DynamicBox(getHurtBox().getCenter(), 16, 16,
                        new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).nor().scl(3)));
            }
            stateTime = 0;
        }
        super.handlePhysics(deltaTime, player, unpassableEntities, unpassableMap);
    }
}
