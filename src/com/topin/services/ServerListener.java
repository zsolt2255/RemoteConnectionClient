package com.topin.services;

import com.topin.helpers.Log;
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
    public static String token = null;
    public static Boolean screenStatus = true;

    /**
     * @param socket
     * @throws IOException
     */
    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            //Waiting for the LoginMessage
            String loginCommand = null;
            do {
                loginCommand = this.bufferedReader.readLine();
                this.loginMessage(loginCommand);
            } while(loginCommand == null);

            //Send init message
            new Thread(new InitMessageSender(this.bufferedOutputStream)).start();

            while (true) {
                String command = this.bufferedReader.readLine();

                //Listen command from Center
                Log.write("Listen").info("Listen command from Center: "+command);

                if (command == null) {
                    socket.close();
                    break;
                }

                this.listenCommand(command);
            }
        } catch (IOException | AWTException e) {
            Log.write("Listen").info("Socket closed");
            Log.write("Listen").error(e.getMessage());
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param command
     * @throws AWTException
     */
    private void listenCommand(String command) throws AWTException {
        String commandType = (String) (new JSONObject(command)).get("type");

        if (commandType.equals("request")) {
            String commandRequest = (String) (new JSONObject(command)).get("request");

            switch (commandRequest) {
                case "init":
                    new Thread(new InitMessageSender(this.bufferedOutputStream)).start();
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
        switch (commandType) {
            case "command":
                new Thread(new RunCommand((String) (new JSONObject(command)).get("command"))).start();
                break;
            case "mouseMove":
                new Thread(new MouseMover((Integer) (new JSONObject(command)).get("x"),(Integer) (new JSONObject(command)).get("y"))).start();
                break;
            case "mouseClick":
                new Thread(new MouseClick((String) (new JSONObject(command)).get("button"),(Integer) (new JSONObject(command)).get("mouseType"))).start();
                break;
            case "keyCode":
                new Thread(new KeyCodeMessage((Integer) (new JSONObject(command)).get("keyCode"))).start();
                break;
        }
    }

    /**
     * @return void
     */
    private void screenShotLoop() {
        screenStatus = true;
        new Thread(new ScreenCapture(this.bufferedOutputStream,"low")).start();
    }

    /**
     * @param bufferedReader
     * @throws IOException
     */
    private void loginMessage(String bufferedReader) throws IOException {
        token = (String) (new JSONObject(this.bufferedReader.readLine())).get("message");
        LoginMessage loginMessage = new LoginMessage("server",token);

        Send.message(this.bufferedOutputStream, loginMessage);

        Log.write("Listen").info("ServerListener LoginMessage successfully fetched");
    }
}
