package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.Box;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;
import com.mygdx.game.Screens.AssetManagerHandler;

import java.util.ArrayList;

public class BabyDragon extends Enemy {
    private Animation<TextureRegion> flyingAnimation;
    private Animation<TextureRegion> fireSpittingAnimation;
    private Texture fireballTexture;
    private float stateTime = 0.0f;
    private boolean playerIsFacingLeft = false;
    private float fireTimer = 0;
    private boolean shootFireball = false;
    private Texture texture;
    public BabyDragon(float x, float y, AssetManagerHandler assetManager){
        super(x, y, 32, 30, 75, assetManager.getTexture("BabyDragon.png"));
        this.texture = assetManager.getTexture("BabyDragon.png");
        this.fireballTexture = assetManager.getTexture("Fireball.png");
        createAnimations();
    }

    protected void createAnimations(){
        TextureRegion[][] regions = TextureRegion.split(texture, 32, 32);
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
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, getHurtBox().getX(), getHurtBox().getY(), 16,16, 32, 32,
                (playerIsFacingLeft ? -1 : 1), 1, 0);
        for(Box hitBox : getHitBoxes()){
            spriteBatch.draw(fireballTexture, hitBox.getX(), hitBox.getY());
        }
        spriteBatch.end();
    }

    protected void generateFireballs(Player player){
        Vector2 fireballVector = player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor().scl(5);
        addHitBox(new DynamicBox(getHurtBox().getX(), getHurtBox().getY(), 16, 16, fireballVector));
    }

    @Override
    public void handlePhysics(float deltaTime, Player player, ArrayList<Entity> unpassableEntities, int[][] unpassableMap){
        stateTime += deltaTime;
        fireTimer += deltaTime;
        if(fireTimer > 2){
            shootFireball = true;
            fireTimer = 0;
            generateFireballs(player);
        }
        playerIsFacingLeft = player.getHurtBox().getX() < getHurtBox().getX();
        super.handlePhysics(deltaTime, player, unpassableEntities, unpassableMap);
    }

    @Override
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
