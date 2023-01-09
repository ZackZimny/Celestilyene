package com.mygdx.game.GameHelpers;

public class RuntimeConfigurations {
    private int musicVolume, sfxVolume;
    private String name;
    private boolean fullscreen;

    public RuntimeConfigurations(int musicVolume, int sfxVolume, String name, boolean fullscreen) {
        this.musicVolume = musicVolume;
        this.sfxVolume = sfxVolume;
        this.name = name;
        this.fullscreen = fullscreen;
    }

    public RuntimeConfigurations(){
        musicVolume = 50;
        sfxVolume = 50;
        name = "DEFAULT";
        fullscreen = false;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public int getSfxVolume() {
        return sfxVolume;
    }

    public String getName() {
        return name;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void setSfxVolume(int sfxVolume) {
        this.sfxVolume = sfxVolume;
    }

    public void setName(String name) {
        this.name = name;
    }

}