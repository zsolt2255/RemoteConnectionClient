package com.topin.services;

import org.json.JSONObject;

import java.io.IOException;

public class RunCommand {
    Process process;
    //TODO itt initeljük majd a commandot constructba és azzal dolguzunk

    public RunCommand(String command) throws IOException {
        this.process = Runtime.getRuntime().exec(command);
    }

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

    //command = "shutdown /r /t 180";
    //command = "cmd.exe /c powershell (New-Object -ComObject Wscript.Shell).Popup(\"\"\"geci jó vagyok\"\"\",0,\"\"\"Feri a hegyrő\"\"\",0x0)";

    /*public String output() throws InterruptedException {
        StringWriter output = new StringWriter();
        StreamGobbler streamGobbler = new StreamGobbler(this.process.getInputStream(), output);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = this.process.waitFor();
    }*/
}
