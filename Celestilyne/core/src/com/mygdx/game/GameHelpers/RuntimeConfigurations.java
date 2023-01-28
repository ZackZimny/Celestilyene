package com.mygdx.game.GameHelpers;

/**
 * Holds the state of the runtime configurations
 */
public class RuntimeConfigurations {
    private int musicVolume, sfxVolume;
    private String name;
    private boolean fullscreen;

    /**
     * initializes a new RuntimeConfigurations state
     * @param musicVolume volume at which the background music will be played
     * @param sfxVolume volume for the sound effects
     * @param name name of the current player for records
     * @param fullscreen boolean if the game is fullscreen
     */
    public RuntimeConfigurations(int musicVolume, int sfxVolume, String name, boolean fullscreen) {
        this.musicVolume = musicVolume;
        this.sfxVolume = sfxVolume;
        this.name = name;
        this.fullscreen = fullscreen;
    }

    /**
     * sets default values for configuration
     */
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

    @Override
    public String toString() {
        return "RuntimeConfigurations{" +
                "musicVolume=" + musicVolume +
                ", sfxVolume=" + sfxVolume +
                ", name='" + name + '\'' +
                ", fullscreen=" + fullscreen +
                '}';
    }
}