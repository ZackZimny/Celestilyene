package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameHelpers.Boxes.DynamicBox;

public class BabyDragonBlue extends BabyDragon {
    public BabyDragonBlue(float x, float y) {
        super(x, y);
        setTexture(new Texture(Gdx.files.internal("BabyDragonBlue.png")));
        createAnimations();
    }

    @Override
    protected void generateFireballs(Player player) {
        for(int i = 0; i < 5; i++) {
            //-pi / 4 -> pi / 4
            double angle = -Math.PI / 4f + Math.PI * i / 10f + player.getHurtBox().getCenter()
                    .sub(getHurtBox().getCenter()).angleRad();
            Vector2 fireballVector = player.getHurtBox().getCenter().sub(getHurtBox().getCenter()).nor()
                    .sub((float) Math.cos(angle), (float) Math.sin(angle)).nor().scl(5);
            fireballVector = new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).scl(3);
            addHitBox(new DynamicBox(getHurtBox().getX(), getHurtBox().getY(), 16, 16, fireballVector));
        }
    }
}
