package com.topin.services;

import com.topin.actions.BackgroundImage;
import com.topin.actions.BiosVersion;
import com.topin.actions.PowerShell;
import com.topin.actions.StaticCommand;
import com.topin.model.command.InitMessage;
import com.topin.model.command.LoginMessage;
import com.topin.model.command.help.DriveUsageMessage;
import com.topin.utils.SystemInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.topin.utils.SystemInfo.getProcessCpuLoad;

public class InitMessageSender {
    InitMessage initMessage;

    public InitMessageSender() {
        this.initMessage = null;
    }

    public InitMessage init() throws IOException {
        String backgroundImage = RunCommand.execCmd(new PowerShell(new BackgroundImage().toString()).toString());
        String biosVersion = RunCommand.execCmd(new StaticCommand(new BiosVersion().toString()).toString()).split("[\\r\\n]+")[1];
        String driverMessage = driverUsageMessage();
        String taskList = taskList();

        byte[] fileContent = FileUtils.readFileToByteArray(new File(backgroundImage.trim()));
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
        return this.initMessage;
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
}
