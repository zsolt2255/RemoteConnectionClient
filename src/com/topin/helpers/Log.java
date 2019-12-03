package com.topin.helpers;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Log {
    static private HashMap<String, Logger> loggerList = new HashMap<>();

    /**
     * @return void
     */
    public static void configure() {
        PropertyConfigurator.configure("resources/log4j.properties");
        //BasicConfigurator.configure();
    }

    /**
     * @param type
     * @return Logger
     */
    public static Logger write(Object type) {
        return write(type.getClass().toString().replace(' ', '_'));
    }

    /**
     * @param type
     * @return Logger
     */
    public static Logger write(String type) {
        if (! loggerList.containsKey(type)) {
            loggerList.put(type, LoggerFactory.getLogger(type));
        }
        return loggerList.get(type);
    }
}