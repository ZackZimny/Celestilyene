package com.mygdx.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;
import com.mygdx.game.GameHelpers.AssetManagerHandler;

/**
 * Enemy character that moves towards the player and shoots a quarter-arc of fireballs towards the player
 */
public class BabyDragonBlue extends BabyDragon {
    /**
     * Loads a Blue Baby Dragon that shoots a quart-arc of fireballs at the player
     * @param x left side x position in gameWorld
     * @param y bottom side y position in gameWorld
     * @param assetManager assetManagerHandler with all assets loaded
     */
    public BabyDragonBlue(float x, float y, AssetManagerHandler assetManager) {
        super(x, y, assetManager);
        //replaces typical red baby dragon spriteSheet with the blue version
        setTexture(assetManager.getTexture("BabyDragonBlue.png"));
        createAnimations();
    }

    /**
     * Generates a quarter-arc of fireballs instead of a single fireball
     * @param player current player instance in the gameLoop
     */
    @Override
    protected void generateFireballs(Player player) {
        int fireballNum = 5;
        for(int i = 0; i < fireballNum; i++) {
            //-pi / 4 -> pi / 4
            //calculates the fireball arc positions by getting all intermediate positions between a negative half
            //of the total arc and a positive half of the total arc, then aims this arc at the player by subtracting
            //position vectors
            double angle = -Math.PI / 4f + Math.PI * i / (fireballNum * 2f) + player.getHurtBox().getCenter()
                    .sub(getHurtBox().getCenter()).angleRad();
            Vector2 fireballVector = new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).scl(3);
            addHitBox(new DynamicBox(getHurtBox().getX(), getHurtBox().getY(), 16, 16, fireballVector));
        }
    }
}
