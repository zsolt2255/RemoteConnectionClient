package com.topin.services;

import com.topin.model.command.help.DriveUsageMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DriverUsageListener {
    public String run() {
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
