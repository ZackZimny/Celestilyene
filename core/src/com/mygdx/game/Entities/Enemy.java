package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.Screens.AssetManagerHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Enemy extends Entity {
    private Texture texture;
    private float speed;
    private Vector2 backForce = new Vector2(0, 0);
    private boolean isHit = false;
    private int hitsToDeath = 5;
    private int hitsTaken = 0;
    private boolean isDead = false;
    private float hitTime = 0;
    public Enemy(float x, float y, int width, int height, float speed, Texture texture) {
        super(new Box(x, y, width, height), new ArrayList<Box>());
        this.speed = speed;
        this.texture = texture;
    }

    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities, int[][] unpassableMap){
        setMovement(player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor().scl(deltaTime).scl(speed));
        hitTime += deltaTime;
        if(hitTime >= 1){
            getHitBoxes().add(getHurtBox());
            hitTime = 0;
        }
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
                backForce.scl(-1);
                setMovement(new Vector2(0, 0));
            }
        }
        changePos(getMovement());
        inflictDamage(player);
        backForce.scl(0.95f - deltaTime);
        if(hitsTaken >= hitsToDeath){
            isDead = true;
        }
    }

    private void inflictDamage(Player player){
        for(Box box : getHitBoxes()){
            if(box.intersects(player.getHurtBox())){
                player.removeHealth();
            }
        }
        getHitBoxes().remove(getHurtBox());
        removeIntersectedHitBoxes(player);
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.begin();
        spriteBatch.draw(texture, getHurtBox().getX(), getHurtBox().getY());
        spriteBatch.end();
    }

    public boolean isDead() {
        return isDead;
    }
}
