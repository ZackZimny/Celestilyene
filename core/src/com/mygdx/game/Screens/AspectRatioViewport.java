package com.mygdx.game.Screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class AspectRatioViewport extends ScalingViewport {
    public AspectRatioViewport(float worldWidth, float worldHeight) {
        super(AspectRatioScaling.getScaling(), worldWidth, worldHeight);
    }
}
