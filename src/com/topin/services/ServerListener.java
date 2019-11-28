package com.topin.services;

import com.topin.model.command.InitMessage;
import com.topin.model.command.LoginMessage;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ServerListener implements Runnable {
    private Socket socket;
    private BufferedInputStream bufferedInputStream;
    private BufferedReader bufferedReader;
    private BufferedOutputStream bufferedOutputStream;

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedInputStream = new BufferedInputStream(this.socket.getInputStream());
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            boolean stop = false;
            while (true) {
                String command = this.bufferedReader.readLine();
                System.out.println("command write:" + command);
                String commandType = (String) (new JSONObject(command)).get("type");
                if (! stop) {
                    String message = (String) (new JSONObject(command)).get("message");
                    if (!message.equals("Successfully logged in")) {
                        LoginMessage loginMessage = new LoginMessage("server", message);
                        ServerMessageSender.sendMessage(this.bufferedOutputStream, loginMessage);
                        InitMessage initMessage = new InitMessage("asd", "asd",
                                "asd", "asd", "asd", "asd", 1234.2, 1234, 1234, "asd",
                                "asd");
                        ServerMessageSender.sendMessage(this.bufferedOutputStream, initMessage);
                        stop = true;
                    }
                }
                if (commandType.equals("command")) {
                    new RunCommand(command);
                }
            }
        } catch (IOException e) {
            System.out.println("Socket closed");
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
