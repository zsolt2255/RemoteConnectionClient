package com.topin.helpers;

import java.util.logging.Logger;

public class Log {
    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public static void info(String message) {
        LOGGER.info(message);
    }
}
