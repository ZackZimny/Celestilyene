package com.mygdx.game.GameHelpers;

/**
 * Creates a timer that plays when start method is used and updates by accumulating deltaTime
 */
public class Timer {
    //time elapsed since the Timer has started
    private float time = 0;
    private final float LENGTH;
    private boolean isActive = false;

    /**
     * Creates a timer that stops being active when the time exceeds the length
     * @param length number of milliseconds the timer will run for
     */
    public Timer(float length){
        LENGTH = length;
    }

    /**
     * adds the deltaTime to the timer and makes it inActive if the time exceeds the length of the timer
     * @param deltaTime time between the last render call and the current one
     */
    public void play(float deltaTime){
        if(isActive)
            time += deltaTime;
        if(time >= LENGTH) {
            isActive = false;
            time = 0;
        }
    }

    /**
     * begins the timer by allowing it to accumulate time
     */
    public void start(){
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public float getTime() {
        return time;
    }
}