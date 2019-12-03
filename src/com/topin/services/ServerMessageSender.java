package com.topin.services;

import com.topin.helpers.Log;
import com.topin.socket.Send;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerMessageSender implements Runnable {
    private Socket socket;
    private BufferedOutputStream bufferedOutputStream;

    /**
     * @param socket
     * @throws IOException
     */
    public ServerMessageSender(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            //Send start messages to the Center
            this.startMessages(this.bufferedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Send command with scanner to Center

        /*Scanner scanner = new Scanner(System.in);
        String input;

        try {
            do {
                input = scanner.nextLine()+"\n";
                Send.message(input,this.bufferedOutputStream,this.bufferedInputStream);
            } while (! (input.equals(SocketProperties.INSTANCE.getSocketExitCommand()+"\n")));
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * @param bufferedOutputStream
     * @throws IOException
     */
    private void startMessages(BufferedOutputStream bufferedOutputStream) throws IOException {
        new ServerDefaultMessageSender().getAllCommand().forEach((command) ->
        {
            try {
                Send.message(bufferedOutputStream, command);

                Log.write(this).info("ServerMessageSender successfully fetched"+command);
            } catch (IOException e) {
                Log.write(this).error(e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
