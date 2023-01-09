package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;
import com.mygdx.game.GameHelpers.GameSounds;
import com.mygdx.game.GameHelpers.AssetManagerHandler;
import com.mygdx.game.Screens.ScreenProjectionHandler;

import java.util.ArrayList;

/**
 * Main character controller in the gameWorld
 */
public class Player extends Entity {
    //represents the player's input via a Vector with values -1, 0, or 1 in the x and y position
    private Vector2 moveVec = new Vector2();
    private final TextureRegion upSprite;
    private final TextureRegion rightSprite;
    private final TextureRegion leftSprite;
    private final TextureRegion downSprite;
    //current sprite to display to the screen
    private TextureRegion currSprite;
    private final Texture magicTexture;
    //wand and hand image that rotates around the player
    private final Texture wand;
    //sound level at which to play the playerShot sound effect
    private float soundLevel;
    //current health; starts at 3 and depletes every time the player is hit
    private int health;
    //library of all sounds within the game
    private final GameSounds gameSounds;

    /**
     * Creates a player at a position
     * @param x x position left side
     * @param y x position bottom side
     * @param health number of hits the player can take until game over
     * @param assetManagerHandler all loaded assets
     * @param gameSounds all loaded gameSounds at the correct volume level
     */
    public Player(int x, int y, int health, AssetManagerHandler assetManagerHandler, GameSounds gameSounds) {
        super(new Box(x, y, 20, 22), new ArrayList<Box>());
        //Player spriteSheet
        Texture texture = assetManagerHandler.getTexture("Player.png");
        TextureRegion[][] regions = TextureRegion.split(texture,
                texture.getWidth() / 4, texture.getHeight());
        magicTexture = assetManagerHandler.getTexture("Magic.png");
        wand = assetManagerHandler.getTexture("Wand.png");
        //translates sound level into a percentage
        soundLevel = soundLevel / 50f;
        this.health = health;
        this.gameSounds = gameSounds;
        //down right up left
        downSprite = regions[0][0];
        rightSprite = regions[0][1];
        upSprite = regions[0][2];
        leftSprite = regions[0][3];
        currSprite = upSprite;
        setTexture(texture);
    }

    /**
     * determines the direction the player is moving by which key is being pressed; represented by a Vector2 variable called moveVec
     */
    public void updateMoveVec(){
        int[] keys = {Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D};
        Vector2[] moves = {new Vector2(0,1), new Vector2(0, -1),
                new Vector2(-1, 0), new Vector2(1, 0)};
        moveVec = new Vector2(0, 0);
        for(int i = 0; i < keys.length; i++){
            if(Gdx.input.isKeyPressed(keys[i])){
                moveVec = moves[i];
            }
        }
    }

    /**
     * Updates the currSprite to the correct frame based on the mouse position
     */
    private void handleAnimation(){
        Vector2 mouse = getMousePos();
        if(Math.abs(mouse.x) >= Math.abs(mouse.y)){
            if(mouse.x < 0){
                currSprite = leftSprite;
            } else {
                currSprite = rightSprite;
            }
        } else {
            if(mouse.y < 0){
                currSprite = downSprite;
            } else {
                currSprite = upSprite;
            }
        }
    }

    /**
     * handles all player movement physics
     * @param deltaTime time between the current and last frame
     * @param unpassableEntities trees that the player cannot move past
     */
    public void move(float deltaTime, ArrayList<Entity> unpassableEntities){
        float playerSpeed = 150f;
        updateMoveVec();
        handleAnimation();

        //moves the player according to which button is pressed so long as he is moving
        if (!moveVec.equals(new Vector2(0, 0))){
            setMovement(moveVec.cpy().nor().scl(playerSpeed * deltaTime));
        } else {
            //else statement prevents the normalizing of the Vector (0, 0), which if normalized will cause the
            //player to move
            setMovement(new Vector2(0, 0));
        }

        //Stops player if he has run into a tree and destroys bullets that have run into a tree
        for(Entity e : unpassableEntities) {
            if(runInto(e)){
                setMovement(new Vector2(0, 0));
            }
            removeIntersectedHitBoxes(e);
        }

        changePos(getMovement());

        //creates a bullet that goes towards the position of the mouse by subtracting the vectors and adding the tip of the wand
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            DynamicBox box = new DynamicBox(getHurtBox().getCenter().add(getMousePos().nor().scl(wand.getWidth())).sub(getLeftHandOffset(), 0),
                    10, 10, getMousePos().nor().scl(7));
            addHitBox(box);
            gameSounds.playPlayerShot();
        }
        moveDynamicHitBoxes();
    }

    /**
     * Displays the player to the screen
     * @param spriteBatch current instance of the spriteBatch
     */
    @Override
    public void render(SpriteBatch spriteBatch){
        Vector2 center = getHurtBox().getCenter();
        float xPos = center.x - currSprite.getRegionWidth() / 2f;
        float yPos = center.y - currSprite.getRegionHeight() / 2f;
        spriteBatch.begin();
        spriteBatch.draw(currSprite, xPos, yPos);
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(magicTexture, hitBox.getX(), hitBox.getY(), 16, 16);
        }

        //rotates hand around player's body
        float leftHand = getLeftHandOffset();
        spriteBatch.draw(TextureRegion.split(wand, wand.getWidth(), wand.getHeight())[0][0],
                center.x - leftHand, center.y, wand.getWidth() / 2f,
                0, wand.getWidth(), wand.getHeight(), 1, 1, getMousePos().angleDeg() - 90);
        spriteBatch.end();
    }

    /**
     * decrements players health by 1
     */
    public void removeHealth() {
        health -= 1;
    }

    /**
     * allows player's hand to flip positions when the mouse is on a different side of the player
     * @return bottom left corner position of the proper hand placement
     */
    private float getLeftHandOffset(){
        return getMousePos().x < 0 ? currSprite.getRegionWidth() / 2f : 0;
    }

    /**
     * gets mouse placement relative to the player's center
     * @return normalized vector of mouse placement relative to the player's center
     */
    private Vector2 getMousePos(){
        Vector2 mousePos = ScreenProjectionHandler.getMousePos();
        return mousePos.sub(getHurtBox().getCenter()).nor();
    }

    /**
     * gets amount of hits to death
     * @return hits to death
     */
    public int getHealth() {
        return health;
    }
}
