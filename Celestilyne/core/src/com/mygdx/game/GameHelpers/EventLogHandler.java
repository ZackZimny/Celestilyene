package com.mygdx.game.GameHelpers;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class EventLogHandler {
    private static final Logger logger = Logger.getLogger("EventLogger");
    private static final SimpleFormatter simpleFormatter = new SimpleFormatter();

    /**
     * Loads the EventLog and deals with errors if it cannot load
     */
    public static void start()
    {
        try {
            Handler handler = new FileHandler("./EventLog.log");
            handler.setFormatter(simpleFormatter);
            logger.addHandler(handler);
        } catch (Exception e) {
            logger.severe("Can't start logger: \""+
                    e + "\"");
        }
        logger.info("Logger started.");
    }

    public static void log(String message){
        logger.info(message);
    }

}