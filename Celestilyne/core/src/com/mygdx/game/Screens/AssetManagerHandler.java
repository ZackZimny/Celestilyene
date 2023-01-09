package com.mygdx.game.Screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class AssetManagerHandler {
    private AssetManager assetManager;
    public AssetManagerHandler(){
        assetManager = new AssetManager();
        loadAssets();
    }

    public void loadAssets(){
        assetManager.load("BabyDragon.png", Texture.class);
        assetManager.load("BabyDragonBlue.png", Texture.class);
        assetManager.load("Checkbox.png", Texture.class);
        assetManager.load("Fireball.ogg", Sound.class);
        assetManager.load("Fireball.png", Texture.class);
        assetManager.load("Heart.png", Texture.class);
        assetManager.load("Mage.png", Texture.class);
        assetManager.load("Magic.png", Texture.class);
        assetManager.load("MainLoop.wav", Music.class);
        assetManager.load("Player.png", Texture.class);
        assetManager.load("Slime.png", Texture.class);
        assetManager.load("Tree.png", Texture.class);
        assetManager.load("Wand.png", Texture.class);
        assetManager.load("Evil Mage-1.png", Texture.class);
        assetManager.load("Baby Dragon-1.png", Texture.class);
        assetManager.load("BabyDragonBlue-1.png", Texture.class);
        assetManager.load("Slime.png", Texture.class);
        assetManager.load("Main Menu Screenshot.png", Texture.class);
        assetManager.load("Options Screenshot.png", Texture.class);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Texture getTexture(String fileName) {
        return assetManager.get(fileName, Texture.class);
    }

    public Sound getSound(String fileName) {
        return assetManager.get(fileName, Sound.class);
    }
}
