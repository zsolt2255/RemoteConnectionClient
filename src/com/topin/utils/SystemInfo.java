package com.topin.utils;

import com.sun.management.OperatingSystemMXBean;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemInfo {
    /**
     * @return Double
     * @throws Exception
     */
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

    /**
     * @return String
     */
    public String getMemoryMax() {
        return String.valueOf(((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize());
    }

    /**
     * @return String
     */
    public String getMemoryUsed() {
        return String.valueOf(((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() -
                ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize());
    }

    /**
     * @return String
     * @throws UnknownHostException
     */
    public String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
