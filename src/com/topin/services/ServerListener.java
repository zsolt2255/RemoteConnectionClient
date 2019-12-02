package com.topin.services;

import com.topin.model.command.LoginMessage;
import com.topin.socket.Send;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ServerListener implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedOutputStream bufferedOutputStream;
    static String token;
    static Boolean screenStatus = true;
    Thread t1;

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
            new Thread(new InitMessageSender(this.bufferedOutputStream)).start();
            while (true) {
                String command = this.bufferedReader.readLine();
                System.out.println("Listen Command: " + command);

                this.checkCommand(command);
            }
        } catch (IOException | AWTException e) {
            System.out.println("Socket closed");
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void checkCommand(String command) throws IOException, AWTException {
        String commandType = (String) (new JSONObject(command)).get("type");
        if (commandType.equals("request")) {
            String commandRequest = (String) (new JSONObject(command)).get("request");
            switch (commandRequest) {
                case "init":
                    System.out.println("run");
                    new Thread(new InitMessageSender(this.bufferedOutputStream));
                    break;
                case "screenshot":
                    screenStatus = false;
                    new Thread(new ScreenCapture(this.bufferedOutputStream,"high")).start();
                    break;
                case "screenStart":
                    screenShotLoop();
                   break;
                case "screenStop":
                    screenStatus = false;
                    break;
            }
        }
        if (commandType.equals("command")) {
            new RunCommand((String) (new JSONObject(command)).get("command"));
        }
        if(commandType.equals("mouseMove")) {
            new MouseMover((Integer) (new JSONObject(command)).get("x"),(Integer) (new JSONObject(command)).get("y")).run();
        }
        if(commandType.equals("mouseClick")) {
            new MouseClick((String) (new JSONObject(command)).get("button"),(Integer) (new JSONObject(command)).get("mouseType")).run();
        }
    }

    private void screenShotLoop() {
        System.out.println("megy");
        screenStatus = true;
        this.t1 = new Thread(new ScreenCapture(this.bufferedOutputStream,"low"));
        t1.start();
    }

    private void loginMessage(String bufferedReader) throws IOException {
        token = (String)(new JSONObject(this.bufferedReader.readLine())).get("message");
        LoginMessage loginMessage = new LoginMessage("server",token);
        Send.message(this.bufferedOutputStream, loginMessage);
    }
}
