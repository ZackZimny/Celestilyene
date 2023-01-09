package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenProjectionHandler {
    private static AspectRatioViewport aspectRatioViewport = new AspectRatioViewport(getWorldWidth(), getWorldHeight());
    public static float getWorldWidth() {
        return 32 * 4 * 7.5f;
    }
    public static float getWorldHeight() {
        return 32 * 3 * 7.5f;
    }
    public static Viewport getAspectRatioViewport() { return aspectRatioViewport; }
    public static Vector2 getMousePos() {
        Vector3 mousePos = ScreenProjectionHandler.getAspectRatioViewport().unproject(
                new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(mousePos.x, mousePos.y);
    }
}
