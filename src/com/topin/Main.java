package com.topin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import com.topin.services.ServerConnection;
import com.topin.utils.SocketProperties;

public class Main {
    private static Socket socket = null;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting client...");

        new Main().init();

        do {
            if (socket == null || socket.isClosed()) {
                new Main().init();
            }
        } while (true);
    }

    private void init() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(SocketProperties.INSTANCE.getSocketAddress(),
                    Integer.parseInt(SocketProperties.INSTANCE.getSocketPort())),5000);
            System.out.println("Connect successfully to server: " + socket.toString());

            //Start ServerConnection
            new Thread(new ServerConnection(socket)).start();
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.out.println( Thread.activeCount());
            System.out.println("Trying reconnect...");
        }
    }
}
