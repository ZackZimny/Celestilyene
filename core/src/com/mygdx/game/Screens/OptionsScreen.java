package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.GameHelpers.RuntimeConfigurations;

import java.util.LinkedHashMap;

public class OptionsScreen extends Screen {
    private Slider sfxSlider;
    private Slider musicSlider;
    private Checkbox fullscreenCheckbox;
    private String name;
    private RuntimeConfigurations runtimeConfigurations;
    public OptionsScreen(RuntimeConfigurations runtimeConfigurations){
        super("Options", ScreenState.OPTIONS);
        this.runtimeConfigurations = runtimeConfigurations;
        LinkedHashMap<String, ScreenState> stateScreenMap = new LinkedHashMap<>();
        stateScreenMap.put("Apply", ScreenState.OPTIONS);
        stateScreenMap.put("Return to Main Menu", ScreenState.MAIN_MENU);
        createButtonColumn(stateScreenMap);
        float screenHeight = ScreenProjectionHandler.getWorldHeight();
        sfxSlider = new Slider(20, screenHeight - 200, "Sound Effects Volume", getTitleFontHandler().getFont());
        sfxSlider.setPercentage(runtimeConfigurations.getSfxVolume());
        musicSlider = new Slider(20, screenHeight - 255, "Music Volume", getTitleFontHandler().getFont());
        musicSlider.setPercentage(runtimeConfigurations.getMusicVolume());
        fullscreenCheckbox = new Checkbox(20, screenHeight - 320, "Fullscreen", getTitleFontHandler().getFont());
        fullscreenCheckbox.setOn(runtimeConfigurations.isFullscreen());
        name = runtimeConfigurations.getName();
    }

    private void handleInput(){
        //Libgdx key bindings are 29 - 54
        //ASCII starts at 65
        for(int i = 29; i <= 54; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                name += (char) (65 + (i - 29));
                break;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && name.length() != 0){
            name = name.substring(0, name.length() - 1);
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        sfxSlider.handleInput();
        musicSlider.handleInput();
        sfxSlider.render(shapeRenderer, spriteBatch);
        musicSlider.render(shapeRenderer, spriteBatch);
        fullscreenCheckbox.render(spriteBatch);
        handleInput();
        String nameString = String.format("Name: %s", name);
        spriteBatch.begin();
        getTitleFontHandler().getFont().draw(spriteBatch, nameString, 20, ScreenProjectionHandler.getWorldHeight() - 130);
        spriteBatch.end();
        runtimeConfigurations.setMusicVolume(musicSlider.getPercentage());
        runtimeConfigurations.setSfxVolume(sfxSlider.getPercentage());
        runtimeConfigurations.setName(name);
        runtimeConfigurations.setFullscreen(fullscreenCheckbox.isOn());
        super.render(shapeRenderer, spriteBatch);
    }

    public RuntimeConfigurations getRuntimeConfigurations() {
        return runtimeConfigurations;
    }
}
