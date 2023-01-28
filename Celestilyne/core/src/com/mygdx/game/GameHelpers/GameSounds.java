package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Loads in all GameSounds at the proper volume described in the OptionsScreen
 */
public class GameSounds {
    private final Music backgroundMusic;
    private final Sound playerShot;
    private float sfxVolume, musicVolume;

    /**
     * Loads in gameSounds
     * @param sfxVolume volume that the sound effects should be played at
     * @param musicVolume volume that the music should be played at
     * @param assetManager assetManager with all game assets loaded in
     */
    public GameSounds(float sfxVolume, float musicVolume, AssetManager assetManager){
        this.sfxVolume = sfxVolume;
        this.musicVolume = musicVolume;
        backgroundMusic = assetManager.get("MainLoop.wav", Music.class);
        backgroundMusic.setLooping(true);
        playerShot = assetManager.get("Fireball.ogg", Sound.class);
    }

    /**
     * Plays the background music loop at the volume set on the settings screen
     */
    public void playBackgroundMusic() {
        backgroundMusic.setVolume(0.25f * musicVolume / 50f);
        backgroundMusic.play();
    }

    /**
     * Plays the player shot sound effect at the volume set on the settings screen
     */
    public void playPlayerShot() {
        playerShot.play(0.6f * sfxVolume / 50f);
    }

    /**
     * updates the volume of the sound effects
     * @param sfxVolume volume at which the sound effects will be played ranging 0.0 to 2.0
     */
    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = sfxVolume;
    }

    /**
     * updates the volume of the background music
     * @param musicVolume volume at which the background music will be played ranging from 0.0 to 2.0
     */
    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

}
