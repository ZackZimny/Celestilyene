package com.mygdx.game.GameHelpers;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Manager class that writes game events to the "EventLog.log" file
 */
public class EventLogHandler {
    private static final Logger logger = Logger.getLogger("EventLogger");
    private static final SimpleFormatter simpleFormatter = new SimpleFormatter();
    private static String logs = "";

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

    /**
     * Logs message to the "EventLog.log: file
     * @param message message to log
     */
    public static void log(String message){
        logs += message;
        logger.info(message);
    }

    /**
     * gets all logs created so far
     * @return logs as a string
     */
    public static String getLogs() {
        return logs;
    }
}