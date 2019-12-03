package com.topin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.topin.helpers.Log;
import com.topin.services.ServerConnection;
import com.topin.socket.Ping;
import com.topin.utils.SocketProperties;

public class Main {
    private static Socket socket = null;

    public static void main(String[] args) {
        //Log initialize
        Log.configure();

        Log.write("Main").info("Started server....");

        //Started server
        new Main().init();

        do {
            //If socket closed then init loop
            if (socket == null || socket.isClosed()) {
                new Main().init();
            }
        } while (true);
    }

    private void init() {
        try {
            socket = new Socket();
            socket.connect(
                    new InetSocketAddress(SocketProperties.INSTANCE.getSocketAddress(),
                    SocketProperties.INSTANCE.getSocketPort()),
                    SocketProperties.INSTANCE.getSocketTimeout());

            Log.write("Main").info("Connect successfully to server: "+ socket.toString());

            //Start ServerConnection on new thread
            new Thread(new ServerConnection(socket)).start();

            //Start Ping on new thread
            new Thread(new Ping(socket)).start();
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Log.write("Main").info("Trying reconnect...");
            Log.write("Main").info("Active threads count: "+String.valueOf(Thread.activeCount()));
        }
    }
}
