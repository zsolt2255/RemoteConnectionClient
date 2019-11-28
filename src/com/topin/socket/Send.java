package com.topin.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Send {

    //System.out.println(new String(sb));
    //PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
    //printWriter.println(Arrays.toString(cmd));

    public static String message(String input, BufferedOutputStream bufferedOutputStream, Object command) throws IOException {
        return getString(bufferedOutputStream, command, input + "\n");
    }

    private static String getString(BufferedOutputStream bufferedOutputStream, Object command, String input) throws IOException {
        byte[] cmd = (input).getBytes();
        bufferedOutputStream.write(cmd, 0, cmd.length);
        bufferedOutputStream.flush();

        System.out.println("Run Command: "+command);

        return Arrays.toString(cmd);
    }

    public static String message(BufferedOutputStream bufferedOutputStream, Object command) throws IOException {
        return getString(bufferedOutputStream, command, command+"\n");
    }
}
