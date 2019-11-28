package com.topin.services;

import com.topin.model.builder.MessageBuilder;
import com.topin.model.contracts.MessageContract;
import com.topin.utils.SocketProperties;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ServerMessageSender implements Runnable {

    private Socket socket;
    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;
    private StringBuffer stringBuffer;

    public ServerMessageSender(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
        this.bufferedInputStream = new BufferedInputStream(this.socket.getInputStream());
        this.bufferedInputStream = new BufferedInputStream(this.socket.getInputStream());
        this.stringBuffer = new StringBuffer();
    }

    @Override
    public void run() {
        try {
            this.startMessage(bufferedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String input;

        try {
            do {
                input = scanner.nextLine() + "\n";
                this.sendMessage(input,this.bufferedOutputStream,this.bufferedInputStream);
            } while (! (input.equals(SocketProperties.INSTANCE.getSocketExitCommand()+"\n")));
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startMessage(BufferedOutputStream bufferedOutputStream) throws IOException {
        new ServerDefaultMessageSender().getAllCommand().forEach((command) ->
        {
            try {
                String result = sendMessage(bufferedOutputStream, command);
                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private MessageContract onMessage() {
        MessageBuilder builder = (MessageBuilder)
                new MessageBuilder("status")
                        .add("success", false);
        return builder.get();
    }

    private void sendMessage(String input, BufferedOutputStream out, BufferedInputStream in) throws IOException {
        byte[] cmd = (this.onMessage()+"\n").getBytes();
        out.write(cmd, 0, cmd.length);
        out.flush();
        //System.out.println(new String(sb));
        //PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
        //printWriter.println(Arrays.toString(cmd));
        System.out.println(Arrays.toString(cmd));
    }

    public static String sendMessage(BufferedOutputStream bufferedOutputStream, Object command) throws IOException {
        byte[] cmd = (command+"\n").getBytes();
        bufferedOutputStream.write(cmd, 0, cmd.length);
        bufferedOutputStream.flush();

        return Arrays.toString(cmd);
    }

    public static String sendMessage(BufferedOutputStream bufferedOutputStream, String command) throws IOException {
        byte[] cmd = (command+"\n").getBytes();
        bufferedOutputStream.write(cmd, 0, cmd.length);
        bufferedOutputStream.flush();

        return Arrays.toString(cmd);
    }
}
