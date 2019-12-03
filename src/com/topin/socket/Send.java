package com.topin.socket;

import com.topin.helpers.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Send {
    /**
     * @param input
     * @param bufferedOutputStream
     * @param command
     * @return
     * @throws IOException
     */
    public static String message(String input, BufferedOutputStream bufferedOutputStream, Object command) throws IOException {
        return getString(bufferedOutputStream, command, input + "\n");
    }

    /**
     * @param bufferedOutputStream
     * @param command
     * @param input
     * @return
     * @throws IOException
     */
    private static String getString(BufferedOutputStream bufferedOutputStream, Object command, String input) throws IOException {
        try {
            byte[] cmd = (input).getBytes();
            int startOffset = 0;
            bufferedOutputStream.write(cmd, startOffset, cmd.length);
            bufferedOutputStream.flush();

            Log.write("Send").info("Send message to Center: "+command);

            return Arrays.toString(cmd);
        } catch(Exception e) {
            Log.write("Send").error(e.getMessage());
            bufferedOutputStream.close();
        }

        return null;
    }

    /**
     * @param bufferedOutputStream
     * @param command
     * @return
     * @throws IOException
     */
    public static String message(BufferedOutputStream bufferedOutputStream, Object command) throws IOException {
        return getString(bufferedOutputStream, command, command+"\n");
    }
}
