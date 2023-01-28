package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Has a hurtBox, which is an instance of the ForceBox class and an ArrayList of hitBoxes, which represent the collision
 * of how the entity interacts with its environment. The hit boxes are instances of the HitBox class.
 * @see Box
 */
public class Entity {
    //the Entity's position on the screen. Can be acted upon with physics
    private final Box hurtBox;
    //a list of the entities hit boxes, which are the extra collision boxes that help the entity interact with its environment
    private final ArrayList<Box> hitBoxes;
    private Vector2 movement;
    private Texture texture;
    private float xOffset = 0f;
    private float yOffset = 0f;

    /**
     * Creates an Entity using a ForceBox and an ArrayList of HitBoxes
     * @param hurtBox Box that holds the Entity's position on the screen. Interacts with HitBoxes
     * @param hitBoxes ArrayList of boxes that can interact with hurtBoxes through intersection
     */
    public Entity (Box hurtBox, ArrayList<Box> hitBoxes){
        this.hurtBox = hurtBox;
        this.hitBoxes = hitBoxes;
    }

    /**
     * Creates an entity based on position in the gameWorld
     * @param x x position in the gameWorld
     * @param y y position in the gameWorld
     * @param width width of the hurtBox
     * @param height height of the hurtBox
     * @param xOffset number of pixels the sprite should be offset from the hurtBox in the x direction
     * @param yOffset number of pixels the sprite should be offset from the hurtBox in the y direction
     */
    public Entity (float x, float y, float width, float height, float xOffset, float yOffset){
        hurtBox = new Box(x - width / 2, y - height / 2, width, height);
        hitBoxes = new ArrayList<>();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Displays the Entity to the gameWorld
     * @param spriteBatch current instance of the spriteBatch
     */
    public void render(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(texture, getHurtBox().getX() + xOffset, getHurtBox().getY() + yOffset);
        spriteBatch.end();
    }

    /**
     * displaces the hurtBox by the vector passed in through the parameters
     * @param v displacement to add
     */
    protected void changePos(Vector2 v){
        hurtBox.changePos(v);
    }

    /**
     * Determines if the entity would run into an entity on the next frame if the current movement were to continue. Prevents the entity from getting stuck within walls.
     * @param entity other entity to check if this entity will run into
     * @return will the entity run into the other one on the next frame
     */
    public boolean runInto(Entity entity){
        //Vectors must be copied to not alter their state during this calculation
        Box ghostBox = new Box(getHurtBox().getPositionVector().cpy().add(movement.cpy().scl(2)),
                getHurtBox().getWidth(), getHurtBox().getHeight());
        return entity.getHurtBox().intersects(ghostBox);
    }

    /**
     * Removes all hitBoxes that intersect with another entity
     * @param other other entity to check collisions with
     */
    public void removeIntersectedHitBoxes(Entity other){
        Iterator<Box> boxIterator = hitBoxes.iterator();
        while(boxIterator.hasNext()){
            Box hitBox = boxIterator.next();
            if(hitBox.intersects(other.getHurtBox())){
                boxIterator.remove();
            }
        }
    }

    /**
     * Moves moving hitBoxes every frame
     */
    public void moveDynamicHitBoxes(){
        for(Box box : getHitBoxes()){
            if(box.getClass().equals(DynamicBox.class)) {
                box.changePos(((DynamicBox) box).getMovement());
            }
        }
    }

    /**
     * gets a list of hitBoxes that can be used to interact with other entities
     * @return ArrayList of boxes
     */
    public ArrayList<Box> getHitBoxes() {
        return hitBoxes;
    }

    /**
     * Adds a hitBox to the list of hitBoxes
     * @param box hitBox to add
     */
    protected void addHitBox(Box box) { hitBoxes.add(box); }

    /**
     * gets a box of the entity's position, width, and height
     * @return entity's box
     */
    public Box getHurtBox() {
        return hurtBox;
    }

    /**
     * gets the current movement direction and speed
     * @return movement Vector2
     */
    public Vector2 getMovement() {
        return movement;
    }

    /**
     * replaces the movement with the vector passed through the parameters
     * @param movement movement to replace the current movement
     */
    public void setMovement(Vector2 movement) {
        this.movement = movement;
    }

    /**
     * replaces the current texture with another one
     * @param texture texture to replace the old one with
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * gets the current Texture
     * @return current Texture
     */
    public Texture getTexture() {
        return texture;
    }

}
