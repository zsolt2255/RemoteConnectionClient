package com.topin.services;

import org.json.JSONObject;

import java.io.IOException;

public class RunCommand {
    Process process;

    public RunCommand(String command) throws IOException {
        //System.out.println(new StaticCommand(command).toString());
        this.process = Runtime.getRuntime().exec((String) (new JSONObject(command)).get("command"));
        //this.process = Runtime.getRuntime().exec(command);
    }

    public static String execCmd(String cmd) throws java.io.IOException {
        Process proc = Runtime.getRuntime().exec(cmd);
        java.io.InputStream is = proc.getInputStream();
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        String val = "";
        if (s.hasNext()) {
            val = s.next();
        }
        else {
            val = "";
        }
        return val;
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
