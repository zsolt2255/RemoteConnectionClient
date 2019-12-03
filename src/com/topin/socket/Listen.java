package com.topin.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listen {
    private Socket socket;
    private BufferedReader bufferedReader;

    /**
     * @param bufferedReader
     * @param socket
     * @throws IOException
     */
    public Listen(BufferedReader bufferedReader, Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public String toString() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
