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

    public String getSocketAddress() {
        return properties.getProperty("socket.address");
    }

    public String getSocketPort() {
        return properties.getProperty("socket.port");
    }

    public String getSocketExitCommand() {
        return properties.getProperty("socket.exitCommand");
    }
}