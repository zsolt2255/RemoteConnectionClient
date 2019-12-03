package com.topin.services;

import com.topin.actions.BackgroundImage;
import com.topin.actions.BiosVersion;
import com.topin.actions.PowerShell;
import com.topin.actions.StaticCommand;
import com.topin.helpers.Log;
import com.topin.model.command.InitMessage;
import com.topin.socket.Send;
import com.topin.utils.SystemInfo;
import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Objects;

import static com.topin.utils.SystemInfo.getProcessCpuLoad;

public class InitMessageSender implements Runnable {
    private InitMessage initMessage;
    private BufferedOutputStream bufferedOutputStream;

    /**
     * @param bufferedOutputStream
     */
    public InitMessageSender(BufferedOutputStream bufferedOutputStream) {
        this.bufferedOutputStream = bufferedOutputStream;
    }

    /**
     * @return String
     */
    private String taskList() {
        return String.valueOf(new TaskListListener().run());
    }

    /**
     * @return String
     */
    public String driverUsageMessage() {
        return new DriverUsageListener().run();
    }

    @Override
    public void run() {
        String backgroundImage = null;
        String biosVersion = null;
        byte[] fileContent = new byte[0];

        try {
            backgroundImage = RunCommand.execCmd(new PowerShell(new BackgroundImage().command()).toString());
            biosVersion = RunCommand.execCmd(new StaticCommand(new BiosVersion().command()).toString()).split("[\\r\\n]+")[1];
            fileContent = FileUtils.readFileToByteArray(new File(Objects.requireNonNull(backgroundImage).trim()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driverMessage = driverUsageMessage();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        String biosVersionTrim = Objects.requireNonNull(biosVersion).trim();
        SystemInfo systemInfo = new SystemInfo();

        try {
            this.initMessage = new InitMessage(
                    InetAddress.getLocalHost().getHostName(),
                    //String.valueOf(new Random().nextInt()),
                    System.getenv("PROCESSOR_ARCHITECTURE"),
                    systemInfo.getHostAddress(),
                    System.getProperty("os.name"),
                    System.getProperty("os.version"),
                    biosVersionTrim,
                    getProcessCpuLoad(),
                    systemInfo.getMemoryMax(),
                    systemInfo.getMemoryUsed(),
                    driverMessage,
                    taskList(),
                    encodedString,
                    ServerListener.token
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Send.message(this.bufferedOutputStream, this.initMessage);

            Log.write(this).info("InitMessageSender successfully fetched");
        } catch (IOException e) {
            Log.write(this).error(e.getMessage());
            e.printStackTrace();
        }
    }
}
