package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameSounds {
    private Music backgroundMusic;
    private Sound playerShot;
    private float sfxVolume, musicVolume;
    public GameSounds(float sfxVolume, float musicVolume, AssetManager assetManager){
        this.sfxVolume = sfxVolume;
        this.musicVolume = musicVolume;
        backgroundMusic = assetManager.get("MainLoop.wav", Music.class);
        backgroundMusic.setLooping(true);
        playerShot = assetManager.get("Fireball.ogg", Sound.class);
    }

    public void playBackgroundMusic() {
        backgroundMusic.setVolume(0.25f * musicVolume / 50f);
        backgroundMusic.play();
    }

    public void playPlayerShot() {
        playerShot.play(0.6f * sfxVolume / 50f);
    }

    public void setSfxVolume(float sfxVolume) {
        this.sfxVolume = sfxVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void dispose() {
        backgroundMusic.dispose();
        playerShot.dispose();
    }
}
