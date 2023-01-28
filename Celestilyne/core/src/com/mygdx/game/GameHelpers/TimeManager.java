package com.mygdx.game.GameHelpers;

public class TimeManager {
    public static String secondsToTime(float seconds){
        int minutes = (int) (seconds / 60);
        seconds -= minutes * 60;
        String time = String.format("%d:%.2f", minutes, seconds);
        if(seconds <= 9){
            time = time.substring(0, time.indexOf(":") + 1) + "0" + time.substring(time.indexOf(":") + 1);
        }
        return time;
    }
}
