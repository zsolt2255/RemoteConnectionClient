package com.topin.services;

import com.topin.helpers.Log;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private Socket socket;

    public ServerConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //Send message to the Center
        try {
            new Thread(new ServerMessageSender(socket)).start();
        } catch (IOException e) {
            Log.write(this).error("ServerMessageSender: error"+e.getMessage());
            e.printStackTrace();
        }

        //Listen message from the Center
        try {
            new Thread(new ServerListener(socket)).start();
        } catch (IOException e) {
            Log.write(this).error("ServerListener: error"+e.getMessage());
            e.printStackTrace();
        }
    }
}
