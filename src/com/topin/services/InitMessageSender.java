package com.topin.services;

import com.topin.actions.BackgroundImage;
import com.topin.actions.BiosVersion;
import com.topin.actions.PowerShell;
import com.topin.actions.StaticCommand;
import com.topin.model.command.InitMessage;
import com.topin.model.command.help.DriveUsageMessage;
import com.topin.socket.Send;
import com.topin.utils.SystemInfo;
import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.topin.utils.SystemInfo.getProcessCpuLoad;

public class InitMessageSender implements Runnable {
    InitMessage initMessage;
    private BufferedOutputStream bufferedOutputStream;

    public InitMessageSender() {
        this.initMessage = null;
    }

    public InitMessageSender(BufferedOutputStream bufferedOutputStream) {
        this.bufferedOutputStream = bufferedOutputStream;
    }

    private String taskList() {
        return String.valueOf(new TaskListListener().run());
    }

    public String driverUsageMessage() {
        List<String> driveUsageMessages = new ArrayList<>();

        File[] drives = File.listRoots();
        if (drives != null && drives.length > 0) {
            for (File aDrive : drives) {
                System.out.println(aDrive.getName());
                Long used = aDrive.getTotalSpace()-aDrive.getFreeSpace();
                driveUsageMessages.add(new DriveUsageMessage(aDrive,used,aDrive.getTotalSpace()).toJson());
            }
        }

        return String.valueOf(driveUsageMessages);
    }

    @Override
    public void run() {
        String backgroundImage = null;
        String biosVersion = null;
        try {
            backgroundImage = RunCommand.execCmd(new PowerShell(new BackgroundImage().toString()).toString());
            biosVersion = RunCommand.execCmd(new StaticCommand(new BiosVersion().toString()).toString()).split("[\\r\\n]+")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driverMessage = driverUsageMessage();

        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(new File(backgroundImage.trim()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        SystemInfo systemInfo = new SystemInfo();
        try {
            this.initMessage = new InitMessage(
                    InetAddress.getLocalHost().getHostName(),
                    System.getenv("PROCESSOR_ARCHITECTURE"),
                    systemInfo.getHostAddress(),
                    System.getProperty("os.name"),
                    System.getProperty("os.version"),
                    biosVersion.trim(),
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
