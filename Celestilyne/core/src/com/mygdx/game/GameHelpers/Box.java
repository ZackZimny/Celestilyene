package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Creates a box with an x, y, width, and height and deals with collisions between other Box objects, vectors, and x-y pairs.
 */
public class Box {
    protected float x, y;
    protected float width;
    protected float height;

    /**
     * Creates a Box starting at the bottom left
     * @param x x position of the bottom left corner in pixels
     * @param y y position of the bottom left corner in pixels
     * @param width in pixels
     * @param height in pixels
     */
    public Box(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a Box from a vector
     * @param position position of the bottom left corner in pixels
     * @param width in pixels
     * @param height in pixels
     */
    public Box(Vector2 position, float width, float height){
        this.x = position.x;
        this.y = position.y;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns weather the Box object contains the x and y position of a vector
     * @param v position vector
     * @return boolean that states if the Box object contains the x and y position of the vector passed as a parameter
     */
    public boolean intersects(Vector2 v){
        return v.x > x //left bounds
                && v.x < x + width //right edge bounds
                && v.y > y //top bounds
                && v.y < y + height; //bottom bounds
    }

    /**
     * Returns weather the Box object contains the x and y position of the parameters
     * @param px point x to be checked
     * @param py point y to be checked
     * @return boolean that states if the Box object contains the x and y position of the parameters
     */
    public boolean intersects(float px, float py){
        return px > x && px < x + width && py > y && py < y + height;
    }

    /**
     * Checks weather the four corners of a box are within this Box object
     * @param box box to be checked
     * @return if the corners of the parameter Box object are within this Box object
     */
    public boolean intersects(Box box){
        return box.getX() + box.getWidth() > x &&
                box.getY() + box.getHeight() > y &&
                x + width > box.x &&
                y + height > box.y;
    }

    /**
     * adds position vector to the position of the Box object
     * @param v2 vector to be added
     */
    public void changePos(Vector2 v2){
        x += v2.x;
        y += v2.y;
    }

    /**
     * Finds the center of the Box object and returns it as a vector
     * @return x and y position of the center of the box as a vector
     */
    public Vector2 getCenter(){
        return new Vector2(x + width / 2, y + height / 2);
    }


    /**
     * finds the position of the box as a vector
     * @return position of the box as a vector
     */
    public Vector2 getPositionVector(){
        return new Vector2(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
