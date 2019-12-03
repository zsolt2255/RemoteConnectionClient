package com.topin.socket;

import com.topin.helpers.Log;
import com.topin.model.command.PingMessage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Ping implements Runnable {
    private Socket socket;
    private BufferedOutputStream bufferedOutputStream;

    /**
     * @param socket
     */
    public Ping(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            if (this.socket.isConnected()) {
                this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
            }

            Thread.sleep(10000);

            while (true) {
                Thread.sleep(10000);
                if (this.socket != null) {
                    if (! this.socket.isClosed() && this.socket.isConnected()) {
                        //Send ping message to Center
                        Send.message(this.bufferedOutputStream,new PingMessage().toJson());

                        //Write active thread count
                        Log.write("Ping").info("Active threads count: "+String.valueOf(Thread.activeCount()));
                    }
                    if (this.socket.isClosed() || ! this.socket.isConnected()) {
                        Log.write("Ping").info("Socket closed");
                        break;
                    }
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
