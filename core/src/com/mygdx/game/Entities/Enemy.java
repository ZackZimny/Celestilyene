package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Rumble;

import java.util.ArrayList;

/**
 * Parent class of all enemy types
 */
public class Enemy extends Entity {
    //texture to render at hurtBox position if render is not overwritten
    private final Texture texture;
    //speed in pixels per second that the enemy moves towards the player
    private final float speed;
    //handles the backwards propulsion that the enemy faces when hit with the player's magic
    private Vector2 backForce = new Vector2(0, 0);
    private int hitsTaken = 0;
    private boolean isDead = false;
    //creates a hitBox that can damage the player every second
    private float hitTime = 0;

    /**
     * Creates an enemy in the gameWorld
     * @param x x position in the gameWorld
     * @param y y position in the gameWorld
     * @param width hurtBox width
     * @param height hurtBox height
     * @param speed speed at which the enemy moves towards the player in pixels per second
     * @param texture default texture that displays if render method is not overwritten
     */
    public Enemy(float x, float y, int width, int height, float speed, Texture texture) {
        super(new Box(x, y, width, height), new ArrayList<Box>());
        this.speed = speed;
        this.texture = texture;
    }

    /**
     * handles enemy position and moves toward the player
     * @param deltaTime time between the current frame and the last frame
     * @param player player instance in the gameWorld
     * @param unpassableEntities trees that the enemy cannot pass through
     */
    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities){
        //moves towards the player by subtracting his position with the enemy's
        setMovement(player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor().scl(deltaTime).scl(speed));

        //determines when to create a hitBox that can hurt the player if overlapping enemy
        hitTime += deltaTime;
        if(hitTime >= 1){
            getHitBoxes().add(getHurtBox());
            hitTime = 0;
        }

        //handles logic if hit by the player's magic and starts the screen shake
        for(int i = 0; i < player.getHitBoxes().size(); i++){
            if(player.getHitBoxes().get(i).intersects(getHurtBox())){
                hitsTaken++;
                player.removeIntersectedHitBoxes(this);
                backForce = getMovement().nor().scl(-5f);
                Rumble.rumble(0.5f, .1f);
            }
        }

        //stops movement toward the player if hit
        //determines if the enemy is hit if the backForce is within a certain distance of zero
        boolean isHit = !backForce.isZero(0.01f);
        if(isHit){
            setMovement(new Vector2(0, 0));
        }

        //handles moving towards the player and other movement logic like damaging the player
        moveDynamicHitBoxes();
        setMovement(getMovement().add(backForce));
        for(Entity e : unpassableEntities) {
            if(runInto(e)){
                backForce.scl(-1);
                setMovement(new Vector2(0, 0));
            }
        }
        changePos(getMovement());
        inflictDamage(player);

        //applies friction to the backForce
        backForce.scl(0.95f - deltaTime);

        int hitsToDeath = 5;
        if(hitsTaken >= hitsToDeath){
            isDead = true;
        }
    }

    /**
     * Lowers the player's health by the number of hitBoxes he overlaps with from this Enemy
     * @param player current player instance in the gameWorld
     */
    private void inflictDamage(Player player){
        for(Box box : getHitBoxes()){
            if(box.intersects(player.getHurtBox())){
                player.removeHealth();
            }
        }
        getHitBoxes().remove(getHurtBox());
        removeIntersectedHitBoxes(player);
    }

    /**
     * displays the Enemy character to the gameWorld
     * @param spriteBatch current spriteBatch instance
     */
    public void render(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(texture, getHurtBox().getX(), getHurtBox().getY());
        spriteBatch.end();
    }

    /**
     * determines if this enemy has taken more than 5 hits and should disappear
     * @return if this enemy is past its hit threshold
     */
    public boolean isDead() {
        return isDead;
    }
}
