package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;

import java.util.ArrayList;

public class Enemy extends Entity {
    private Texture texture;
    private float speed;
    private Vector2 backForce = new Vector2(0, 0);
    private boolean isHit = false;
    private int hitsToDeath = 5;
    private int hitsTaken = 0;
    private boolean isDead = false;
    public Enemy(float x, float y, int width, int height, float speed, String textureFile) {
        super(new Box(x, y, width, height), new ArrayList<Box>());
        this.speed = speed;
        texture = new Texture(textureFile);
        setTexture(texture);
    }

    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities){
        setMovement(player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor().scl(deltaTime).scl(speed));
        for(int i = 0; i < player.getHitBoxes().size(); i++){
            if(player.getHitBoxes().get(i).intersects(getHurtBox())){
                hitsTaken++;
                player.removeIntersectedHitBoxes(this);
                backForce = getMovement().nor().scl(-5f);
            }
        }
        isHit = !backForce.isZero(0.01f);
        if(isHit){
            setMovement(new Vector2(0, 0));
        }
        moveDynamicHitBoxes();
        setMovement(getMovement().add(backForce));
        for(Entity e : unpassableEntities) {
            if(runInto(e)){
                if(isHit){
                    backForce.scl(-1);
                } else {
                    setMovement(new Vector2(0, 0));
                }
            }
            removeIntersectedHitBoxes(e);
        }
        changePos(getMovement());
        removeIntersectedHitBoxes(player);
        backForce.scl(0.95f - deltaTime);
        if(hitsTaken >= hitsToDeath){
            isDead = true;
        }
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(texture, getHurtBox().getX(), getHurtBox().getY());
    }

    protected boolean isHit(){
        return isHit;
    }

    public boolean isDead() {
        return isDead;
    }
}
