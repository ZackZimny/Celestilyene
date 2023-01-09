package com.mygdx.game.GameHelpers;

import javax.swing.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Creates an alert and adds it to the text file in the event that the game crashes
 */
public class CrashLogHandler {
    //ensures that the crash is logged only to the crash.txt file
    private static final Logger logger = Logger.getLogger("CrashLogger");
    private static final SimpleFormatter simpleFormatter = new SimpleFormatter();

    /**
     * Loads the CrashLogger
     */
    public static void start() {
        try {
            Handler handler = new FileHandler("./crash.txt", 9999999, 1, true);
            handler.setFormatter(simpleFormatter);
            logger.addHandler(handler);
        } catch (Exception e) {
            logger.severe("Can't start logger: \""+
                    e + "\"");
        }
        logger.info("Logger started.");
    }

    /**
     * Logs the crash to crash.txt and creates an error pane for the user
     * @param message message to be displayed on the error pane
     * @param report report to be added to the crash.txt file
     */
    public static void logSevere(String message, String report){
        logger.severe(message);
        logger.severe(report);
        logger.severe(EventLogHandler.getLogs());
        JOptionPane.showMessageDialog(null, message +
                "\nPlease restart the game. " +
                "\nIf this problem persists, please email us the crash.txt file located in the root folder. Email us at"
                + " developers@cognitiveThought.com.\nReport:\n" + report);
        System.exit(0);
    }

}