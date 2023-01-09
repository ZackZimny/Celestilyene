package com.mygdx.game.Screens;

import com.mygdx.game.GameHelpers.AssetManagerHandler;

public class TutorialScreen4 extends TutorialScreen {

    /**
     * Initializes tutorial screen
     * @param assetManagerHandler used to load sprites for informational purposes
     */
    public TutorialScreen4(AssetManagerHandler assetManagerHandler){
        super("Navigating the Options Menu", ScreenState.TUTORIAL4, ScreenState.TUTORIAL3, ScreenState.MAIN_MENU,
                "Use the sliders in the menu to change the volume of the background music and sound effects." +
                        "Use the keyboard to type in a name for the leaderboard. " +
                        "Toggle fullscreen on or off with the checkbox next to the words fullscreen. " +
                        "Use the apply button to save these settings between play sessions.",
                assetManagerHandler.getTexture("Options Screenshot.png"));
    }
}
