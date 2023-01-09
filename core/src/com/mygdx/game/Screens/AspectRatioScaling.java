package com.mygdx.game.Screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

/**
 * Determines scale based on a multiple of 2 to prevent pixel flickering
 */
public class AspectRatioScaling {
    /**
     * Determines the size at which the screen should be displayed at
     * @return Vector of the scaling of the width and height
     */
    public static Scaling getScaling() {
        return new Scaling() {
            @Override
            public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
                float scaleX = targetWidth / 4f;
                float scaleY = targetHeight / 3f;
                float scale = scaleX < scaleY ? targetWidth / sourceWidth : targetHeight / sourceHeight;
                float[] nearestScales = {1/8f, 1/4f, 1/2f, 1f, 2f, 4f, 8f};
                for(int i = 0; i < nearestScales.length - 1; i++){
                    if(scale < nearestScales[i + 1] && scale >= nearestScales[i]){
                        scale = nearestScales[i];
                    }
                }
                temp.x = sourceWidth * scale;
                temp.y = sourceHeight * scale;
                return temp;
            }
        };
    }
}
