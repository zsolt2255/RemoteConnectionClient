package com.topin.services;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private Socket socket;

    public ServerConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //Send message to the server
        try {
            new Thread(new ServerMessageSender(socket)).start();
        } catch (IOException e) {
            System.out.println("ServerMessageSender error");
            e.printStackTrace();
        }

        //Listen message from the server
        try {
            new Thread(new ServerListener(socket)).start();
        } catch (IOException e) {
            System.out.println("ServerListener error");
            e.printStackTrace();
        }
    }
}
