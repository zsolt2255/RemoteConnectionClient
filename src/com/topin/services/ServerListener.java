package com.topin.services;

import com.topin.actions.*;
import com.topin.model.command.InitMessage;
import com.topin.model.command.LoginMessage;
import com.topin.socket.Send;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ServerListener implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedOutputStream bufferedOutputStream;

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
        Double cpuUsage = Double.valueOf(RunCommand.execCmd(new StaticCommand(new CPUInformation().toString()).toString()).split("[\\r\\n]+")[1]);
        String hostName = RunCommand.execCmd(new StaticCommand(new HostName().toString()).toString()).split("[\\r\\n]+")[0];
        String[] osName = RunCommand.execCmd(new StaticCommand(new OperationInformation().toString()).toString()).split(":")[1].split("\\r?\\n");
        String[] osVersion = RunCommand.execCmd(new StaticCommand(new OperationInformation().toString()).toString()).split(":");
        String biosVersion = RunCommand.execCmd(new StaticCommand(new BiosVersion().toString()).toString()).split("[\\r\\n]+")[1];
        String ramMaxString = RunCommand.execCmd(new StaticCommand(new RamMax().toString()).toString()).split(":")[1];
        Integer ramMax = Integer.valueOf(ramMaxString.trim().substring(0, ramMaxString.trim().length() - 2));
        InitMessage initMessage = new InitMessage(hostName, "asd",
                "asd", osName[0].trim(), osVersion[2].trim(), biosVersion.trim(), cpuUsage, ramMax, 1234, "asd",
                "asd");
        Send.message(this.bufferedOutputStream, initMessage);
    }

    private void loginMessage(String bufferedReader) throws IOException {
        LoginMessage loginMessage = new LoginMessage("server",(String)(new JSONObject(this.bufferedReader.readLine())).get("message"));
        Send.message(this.bufferedOutputStream, loginMessage);
    }
}
