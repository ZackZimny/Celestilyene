package com.mygdx.game.GameHelpers;

import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Handles the screen shake effect
 */
public class Rumble {
    private static float time = 0;
    private static float currentTime = 0;
    private static float power = 0;
    private static Random random;
    private static final Vector3 pos = new Vector3();

    /**
     * initializes the screen shake
     * @param rumblePower how violent the screen shake should be
     * @param rumbleLength how many seconds does it last
     */
    public static void rumble(float rumblePower, float rumbleLength) {
        random = new Random();
        power = rumblePower;
        time = rumbleLength;
        currentTime = 0;
    }

    /**
     * Updates the camera based on the amount of time that has passed
     * @param delta time between the current and previous frames
     */
    public static void tick(float delta) {
        if (currentTime <= time) {
            float currentPower = power * ((time - currentTime) / time);

            pos.x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            pos.y = (random.nextFloat() - 0.5f) * 2 * currentPower;

            currentTime += delta;
        } else {
            time = 0;
        }
    }

    /**
     * gets the time left for the rumble effect to play
     * @return time left for the rumble effect to play as a float
     */
    public static float getRumbleTimeLeft() {
        return time;
    }

    /**
     * Gets the current camera position
     * @return Vector3 with the camera's position
     */
    public static Vector3 getPos() {
        return pos;
    }
}