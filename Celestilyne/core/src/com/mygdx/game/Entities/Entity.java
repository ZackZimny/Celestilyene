package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Box;
import com.mygdx.game.GameHelpers.DynamicBox;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Has a hurtBox, which is an instance of the ForceBox class and an ArrayList of hitBoxes, which represent the collision
 * of how the entity interacts with its environment. The hit boxes are instances of the HitBox class.
 * @see Box
 */
public class Entity {
    //the Entity's position on the screen. Can be acted upon with physics
    private Box hurtBox;
    //a list of the entities hit boxes, which are the extra collision boxes that help the entity interact with its environment
    private ArrayList<Box> hitBoxes;
    private Vector2 movement;
    private boolean isPassable = false;
    private Texture texture;

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
     * Creates an Entity using an x, y, width, and height to represent the hurtBox
     * @param x x position of the hurtBox in pixels
     * @param y y position of the hurtBox in pixels
     * @param width width of the hurtBox in pixels
     * @param height height of the hurtBox in pixels
     */
    public Entity (float x, float y, float width, float height){
        hurtBox = new Box(x - width / 2, y - height / 2, width, height);
        hitBoxes = new ArrayList<>();
    }

    /**
     * Displays the Entity's hurt boxes and hit boxes on screen (for DEBUG mode only)
     * @param sr ShapeRenderer that renders the shapes that are drawn in the function
     */
    public void showBoxes(ShapeRenderer sr) {
        sr.setAutoShapeType(true);
        sr.begin();
        sr.set(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLUE);
        sr.rect(hurtBox.getX(), hurtBox.getY(), hurtBox.getWidth(), hurtBox.getHeight());
        sr.setColor(Color.RED);
        for(Box i : hitBoxes){
            sr.rect(i.getX(), i.getY(), i.getWidth(), i.getHeight());
        }
        sr.end();
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(texture, getHurtBox().getX(), getHurtBox().getY());
    }

    protected void changePos(Vector2 v){
        hurtBox.changePos(v);
    }

    public boolean runInto(Entity entity){
        Box ghostBox = new Box(getHurtBox().getPositionVector().add(movement.scl(2)),
                getHurtBox().getWidth(), getHurtBox().getHeight());
        return !entity.isPassable() && entity.getHurtBox().intersects(ghostBox);
    }

    public void removeIntersectedHitBoxes(Entity other){
        Iterator<Box> boxIterator = hitBoxes.iterator();
        while(boxIterator.hasNext()){
            Box hitBox = boxIterator.next();
            if(hitBox.intersects(other.getHurtBox())){
                boxIterator.remove();
            }
        }
    }

    public void moveDynamicHitBoxes(){
        for(Box box : getHitBoxes()){
            if(box.getClass().equals(DynamicBox.class)) {
                box.changePos(((DynamicBox) box).getMovement());
            }
        }
    }

    public ArrayList<Box> getHitBoxes() {
        return hitBoxes;
    }


    protected void removeHitBox(int i){
        hitBoxes.remove(i);
    }

    protected void addHitBox(Box box) { hitBoxes.add(box); }

    public Box getHurtBox() {
        return hurtBox;
    }

    public Vector2 getMovement() {
        return movement;
    }
    public void setMovement(Vector2 movement) {
        this.movement = movement;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

}
