package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;
import com.mygdx.game.GameHelpers.AssetManagerHandler;

import java.util.ArrayList;

/**
 * Enemy character that shoots a single fireball at the player and moves towards the player
 */
public class BabyDragon extends Enemy {
    //plays by default unless a fireball is shot
    private Animation<TextureRegion> flyingAnimation;
    //plays on repeated time; depends on stateTime
    private Animation<TextureRegion> fireSpittingAnimation;
    //image attached to hitboxes
    private final Texture fireballTexture;
    //works as an animation timer, continuously adds deltaTime every render frame
    private float stateTime = 0.0f;
    //used to flip the sprite when the player switches sides
    private boolean playerIsFacingLeft = false;
    //determines how long it has been since last fireball; resets to zero when fireball is shot
    private float fireTimer = 0;
    //turns true then back to false when fireTimer is past threshold defined in the handlePhysics function
    private boolean shootFireball = false;
    //texture that contains all images in the spriteSheet
    private Texture texture;

    /**
     * Creates a Baby Dragon enemy in the gameLoop
     * @param x left side x position in gameWorld
     * @param y bottom side y position in gameWorld
     * @param assetManager assetManagerHandler with all assets loaded
     */
    public BabyDragon(float x, float y, AssetManagerHandler assetManager){
        super(x, y, 32, 30, 75, assetManager.getTexture("BabyDragon.png"));
        this.texture = assetManager.getTexture("BabyDragon.png");
        this.fireballTexture = assetManager.getTexture("Fireball.png");
        createAnimations();
    }

    /**
     * Splits the spriteSheet up into its respective flying and fire-spitting animations
     */
    protected void createAnimations(){
        TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
        flyingAnimation = new Animation<>(1 / 3f, regions[0]);
        fireSpittingAnimation = new Animation<>(1 / 3f, regions[1]);
    }

    /**
     * Displays the BabyDragon to the screen during the gameLoop
     * @param spriteBatch displays the current BabyDragon animation frame to the screen
     */
    @Override
    public void render(SpriteBatch spriteBatch){
        //handling animation state
        TextureRegion currentFrame = flyingAnimation.getKeyFrame(stateTime, true);
        if(shootFireball){
            currentFrame = fireSpittingAnimation.getKeyFrame(fireTimer, false);
        }
        if(fireSpittingAnimation.isAnimationFinished(fireTimer)){
            shootFireball = false;
        }

        //handles drawing current animation to screen
        //sprite faces right by default
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, getHurtBox().getX(), getHurtBox().getY(), 16,16, 32, 32,
                (playerIsFacingLeft ? -1 : 1), 1, 0);
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(fireballTexture, hitBox.getX(), hitBox.getY());
        }
        spriteBatch.end();
    }

    /**
     * Creates a fireball hitBox that moves at the current player position
     * @param player current player instance in the gameLoop
     */
    protected void generateFireballs(Player player){
        //gets the direction to shoot the fireball at by subtracting this dragon's position vector and the player's
        //position vector then normalizing it
        int fireballSpeed = 5;
        Vector2 fireballVector = player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor().scl(fireballSpeed);
        addHitBox(new DynamicBox(getHurtBox().getX(), getHurtBox().getY(), 16, 16, fireballVector));
    }

    /**
     * Handles all position changes and physics
     * @param deltaTime time between the current and last frame rendered
     * @param player current player instance in the gameLoop
     * @param unpassableEntities trees that are unable to be run through
     */
    @Override
    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities){
        stateTime += deltaTime;
        fireTimer += deltaTime;
        if(fireTimer > 2){
            shootFireball = true;
            fireTimer = 0;
            generateFireballs(player);
        }
        playerIsFacingLeft = player.getHurtBox().getX() < getHurtBox().getX();
        super.handlePhysics(deltaTime, player, unpassableEntities);
    }

    /**
     * Overrides the current spriteSheet and replaces it with another
     * @param texture spriteSheet to replace the current one with
     */
    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
