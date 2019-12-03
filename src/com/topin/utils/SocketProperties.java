package com.topin.utils;

import com.topin.Main;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum SocketProperties {
    INSTANCE;

    private final Properties properties;

    SocketProperties() {
        properties = new Properties();
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("resources/socket.properties");
            properties.load(input);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * @return String
     */
    public String getSocketAddress() {
        return properties.getProperty("socket.address");
    }

    /**
     * @return Integer
     */
    public Integer getSocketPort() {
        return Integer.valueOf(properties.getProperty("socket.port"));
    }

    /**
     * @return String
     */
    public String getSocketExitCommand() {
        return properties.getProperty("socket.exitCommand");
    }

    /**
     * @return String
     */
    public Integer getSocketTimeout() {
        return Integer.valueOf(properties.getProperty("socket.timeout"));
    }
}