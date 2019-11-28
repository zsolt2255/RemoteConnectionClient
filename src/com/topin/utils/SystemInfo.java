package com.topin.utils;

import com.sun.management.OperatingSystemMXBean;
import com.topin.model.command.help.DriveUsageMessage;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SystemInfo {
    private InetAddress inetAddress;

    public SystemInfo() {
        this.inetAddress = null;
    }

    public static double getProcessCpuLoad() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList attributes = mBeanServer.getAttributes(objectName, new String[]{ "ProcessCpuLoad" });

        if (attributes.isEmpty()) {
            return Double.NaN;
        }

        Attribute att = (Attribute)attributes.get(0);
        Double value  = (Double)att.getValue();

        if (value == -1.0) {
            return Double.NaN;
        }

        return ((int)(value * 1000) / 10.0);
    }

    public Long getMemoryMax() {
        return ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    }

    public Long getMemoryUsed() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    public String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
