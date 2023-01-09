package com.mygdx.game.Screens;

public class TutorialScreen3 extends TutorialScreen {
    public TutorialScreen3(AssetManagerHandler assetManagerHandler) {
        super("Navigating the Main Menu", ScreenState.TUTORIAL3, ScreenState.TUTORIAL2, ScreenState.TUTORIAL4,
                "The Start button begins the game. The Tutorial button brings you to the menu you are currently in. " +
                        "The Options button allows you to change Celestilyne's runtime configurations and settings. " +
                        "The Records screen allows you to view a leaderboard of past scores. " +
                        "At any time, the game can be exited through the escape key.",
                assetManagerHandler.getTexture("Main Menu Screenshot.png"));
    }
}
