package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Manager class that is used to load in all game assets like Textures and Music using an AssetManager
 */
public class AssetManagerHandler {
    private final AssetManager assetManager;

    /**
     * Creates an AssetManager and loads in assets
     */
    public AssetManagerHandler(){
        assetManager = new AssetManager();
        loadAssets();
    }

    /**
     * Loads in all game assets
     * @throws GdxRuntimeException when an incorrect file handle or class is used
     */
    public void loadAssets() throws GdxRuntimeException {
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

    /**
     * get the asset manager with all assets loaded in
     * @return assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * gets texture outlined in parameters
     * @param fileName name of the Texture file to be loaded in
     * @return loaded Texture
     */
    public Texture getTexture(String fileName) {
        return assetManager.get(fileName, Texture.class);
    }
}
