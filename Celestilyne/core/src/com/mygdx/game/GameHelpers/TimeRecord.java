package com.mygdx.game.GameHelpers;

public class TimeRecord {
    private float time;
    private String name;

    public TimeRecord(float time, String name) {
        this.time = time;
        this.name = name;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
