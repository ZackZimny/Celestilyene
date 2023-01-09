package com.mygdx.game.Screens;

import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * Viewport that scales with pixel perfection
 */
public class AspectRatioViewport extends ScalingViewport {
    /**
     * Creates a viewport that scales in powers of two to prevent pixel flickering
     * @param worldWidth width of the original game
     * @param worldHeight height of the original game
     */
    public AspectRatioViewport(float worldWidth, float worldHeight) {
        super(AspectRatioScaling.getScaling(), worldWidth, worldHeight);
    }
}
