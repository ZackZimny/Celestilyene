package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TutorialScreen2 extends TutorialScreen {
    private Texture[] textures;
    public TutorialScreen2(AssetManagerHandler assetManagerHandler) {
        super("Enemies", ScreenState.TUTORIAL2, ScreenState.TUTORIAL1, ScreenState.TUTORIAL3,
                "Don't run into enemies or get hit by their fireballs. " +
                        "Otherwise, you'll lose health, and it takes three hits until game over, so watch out!. " +
                        "Also, enemies can shoot through trees that you can't shoot through.");
        textures = new Texture[]{
                assetManagerHandler.getTexture("Baby Dragon-1.png"),
                assetManagerHandler.getTexture("BabyDragonBlue-1.png"),
                assetManagerHandler.getTexture("Evil Mage-1.png"),
                assetManagerHandler.getTexture("Slime.png")
        };
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        float padding = ScreenProjectionHandler.getWorldWidth() / 8f;
        spriteBatch.begin();
        for(int i = 0; i < textures.length; i++){
            spriteBatch.draw(textures[i], padding + padding * i * 2, 400);
        }
        spriteBatch.end();
        super.render(shapeRenderer, spriteBatch);
    }
}
