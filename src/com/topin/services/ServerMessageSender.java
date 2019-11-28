package com.topin.services;

import com.topin.socket.Send;
import com.topin.utils.SocketProperties;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerMessageSender implements Runnable {
    private Socket socket;
    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;

    public ServerMessageSender(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
        this.bufferedInputStream = new BufferedInputStream(this.socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            this.startMessage(bufferedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
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
        }
    }

    private void startMessage(BufferedOutputStream bufferedOutputStream) throws IOException {
        new ServerDefaultMessageSender().getAllCommand().forEach((command) ->
        {
            try {
                String result = Send.message(bufferedOutputStream, command);
                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
