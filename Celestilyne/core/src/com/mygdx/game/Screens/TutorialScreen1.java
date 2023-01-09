package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Entities.Entity;
import com.mygdx.game.Entities.Player;

import java.util.ArrayList;

public class TutorialScreen1 extends TutorialScreen {

    private Player player;
    public TutorialScreen1(Player player) {
        super("Moving The Player", ScreenState.TUTORIAL1, ScreenState.MAIN_MENU, ScreenState.TUTORIAL2,
                "Move the wizard using the WASD keys where W is up, A is left, S is down, and D is right. " +
                        "Click using left click to shoot magic to inflict damage on enemies. Try it out! " +
                        "The wizard will always point his wand at the mouse cursor. " +
                        "Defeat all of the enemies in a room to go to the next and rack up points!");
        this.player = player;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        player.move(Gdx.graphics.getDeltaTime(), new ArrayList<Entity>(), shapeRenderer);
        player.render(spriteBatch);
        super.render(shapeRenderer, spriteBatch);
    }
}
