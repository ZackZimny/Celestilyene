package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Box;
import com.mygdx.game.GameHelpers.DynamicBox;

import java.util.ArrayList;

public class BabyDragon extends Enemy {
    private Animation<TextureRegion> flyingAnimation;
    private Animation<TextureRegion> fireSpittingAnimation;
    private Texture fireballTexture;
    private float stateTime = 0.0f;
    private boolean playerIsFacingLeft = false;
    private float fireTimer = 0;
    private boolean shootFireball = false;
    public BabyDragon(float x, float y){
        super(x, y, 32, 30, 50, "BabyDragon.png");
        setTexture(new Texture(Gdx.files.internal("BabyDragon.png")));
        fireballTexture = new Texture(Gdx.files.internal("fireball.png"));
        createAnimations();
    }

    protected void createAnimations(){
        TextureRegion[][] regions = TextureRegion.split(getTexture(), 32, 32);
        flyingAnimation = new Animation<>(1 / 3f, regions[0]);
        fireSpittingAnimation = new Animation<>(1 / 3f, regions[1]);
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        TextureRegion currentFrame = flyingAnimation.getKeyFrame(stateTime, true);
        if(shootFireball){
            currentFrame = fireSpittingAnimation.getKeyFrame(fireTimer, false);
        }
        if(fireSpittingAnimation.isAnimationFinished(fireTimer)){
            shootFireball = false;
        }
        //sprite faces right by default
        spriteBatch.draw(currentFrame, getHurtBox().getX(), getHurtBox().getY(), 16,16, 32, 32,
                (playerIsFacingLeft ? -1 : 1), 1, 0);
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(fireballTexture, hitBox.getX(), hitBox.getY());
        }
    }

    protected void generateFireballs(Player player){
        Vector2 fireballVector = player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor().scl(5);
        addHitBox(new DynamicBox(getHurtBox().getX(), getHurtBox().getY(), 16, 16, fireballVector));
    }

    @Override
    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities){
        stateTime += deltaTime;
        fireTimer += deltaTime;
        if(fireTimer > 3){
            shootFireball = true;
            fireTimer = 0;
            generateFireballs(player);
        }
        playerIsFacingLeft = player.getHurtBox().getX() < getHurtBox().getX();
        super.handlePhysics(deltaTime, player, unpassableEntities);
    }

}
