package com.topin.services;

import com.topin.model.command.LoginMessage;
import com.topin.socket.Send;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ServerListener implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedOutputStream bufferedOutputStream;
    public static String token;

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            //Waiting for the LoginMessage
            this.loginMessage(this.bufferedReader.readLine());

            //Send init message
            this.initMessage(this.bufferedReader.readLine());

            while (true) {
                String command = this.bufferedReader.readLine();
                System.out.println("Listen Command: " + command);

                this.checkCommand(command);
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

    private void checkCommand(String command) throws IOException {
        String commandType = (String) (new JSONObject(command)).get("type");
        if (commandType.equals("command")) {
            new RunCommand((String) (new JSONObject(command)).get("command"));
        }
    }

    private void initMessage(String bufferedReader) throws IOException {
        InitMessageSender initMessage = new InitMessageSender();
        Send.message(this.bufferedOutputStream, initMessage.init());
    }

    private void loginMessage(String bufferedReader) throws IOException {
        token = (String)(new JSONObject(this.bufferedReader.readLine())).get("message");
        LoginMessage loginMessage = new LoginMessage("server",token);
        Send.message(this.bufferedOutputStream, loginMessage);
    }
}
