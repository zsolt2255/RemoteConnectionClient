package com.topin.services;

import com.topin.helpers.Log;

import java.io.IOException;

public class RunCommand implements Runnable{
    private String command = null;
    private Process process;

    /**
     * @param command
     */
    public RunCommand(String command) {
        this.command = command;
    }

    /**
     * @param cmd
     * @return String
     * @throws java.io.IOException
     */
    public static String execCmd(String cmd) throws java.io.IOException {
        Process process = Runtime.getRuntime().exec(cmd);
        java.io.InputStream inputStream = process.getInputStream();
        java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
        String input = "";

        if (scanner.hasNext()) {
            input = scanner.next();
        } else {
            input = "";
        }

        return input;
    }

    @Override
    public void run() {
        try {
            this.process = Runtime.getRuntime().exec(this.command);
            Log.write(this).info("Exec command: "+this.command);
        } catch (IOException e) {
            Log.write(this).error(e.getMessage());
            e.printStackTrace();
        }
    }
}
