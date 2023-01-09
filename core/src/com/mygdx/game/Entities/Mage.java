package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;
import com.mygdx.game.GameHelpers.AssetManagerHandler;

import java.util.ArrayList;

/**
 * Mage enemy that shoots a circle of fireballs around its position during a set interval
 */
public class Mage extends Enemy {
    //times animation and fireball intervals
    private float stateTime = 0f;
    private boolean isAttacking = false;
    //animation frame to be displayed
    private TextureRegion currentFrame;
    private final Texture magicTexture;
    //texture regions with default and cast spell positions
    private final TextureRegion[] frames;
    public Mage(float x, float y, AssetManagerHandler assetManagerHandler) {
        super(x, y, 30, 32, 60, assetManagerHandler.getTexture("Mage.png"));
        //Mage spriteSheet as a texture
        Texture texture = assetManagerHandler.getTexture("Mage.png");
        frames = TextureRegion.split(texture, 32, 32)[0];
        currentFrame = frames[0];
        magicTexture = assetManagerHandler.getTexture("Fireball.png");
    }

    /**
     * Displays the Mage to the screen
     * @param spriteBatch current spriteBatch instance
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        //handles animation intervals
        if(stateTime < 20 / 60f && isAttacking){
            currentFrame = frames[1];
        } else if(isAttacking && stateTime > 10 / 60f){
            currentFrame = frames[0];
            isAttacking = false;
        }

        //displays the sprite to the screen
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, getHurtBox().getX(), getHurtBox().getY());
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(magicTexture, hitBox.getX(), hitBox.getY());
        }
        spriteBatch.end();
    }

    /**
     * Creates a ring of fireballs around the Mage Entity
     * @param deltaTime time between the current frame and the last frame
     * @param player player instance in the gameWorld
     * @param unpassableEntities trees that the enemy cannot pass through
     */
    @Override
    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities) {
        stateTime += deltaTime;
        //attacks every two seconds
        if(stateTime > 2){
            isAttacking = true;
            int fireballNum = 15;
            //creates ring of fireballs by getting interval positions within a circle
            for(int i = 0; i < fireballNum; i++){
                double angle = Math.PI * 2f / fireballNum * i;
                addHitBox(new DynamicBox(getHurtBox().getCenter(), 16, 16,
                        new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).nor().scl(3)));
            }
            stateTime = 0;
        }
        super.handlePhysics(deltaTime, player, unpassableEntities);
    }
}
