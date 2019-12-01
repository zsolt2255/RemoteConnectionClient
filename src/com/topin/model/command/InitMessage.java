package com.topin.model.command;

import com.topin.model.Message;
import org.json.JSONObject;

import java.util.List;

public class InitMessage extends Message {
    private String hostname;
    private String cpuName;
    private String localIp;
    private String osName;
    private String osVersion;
    private String biosVersion;
    private Double cpuUsage;
    private String ramUsage;
    private String ramMax;
    private String driveUsage; // Json: C: 11GB/256GB  USED/ALL
    private String backgroundImage;
    private String target = "1";
    private String from;

    private String taskList; // JSON: {count: 121, [{name: 'Total Commander', status: 'idle', 'pid': 12331}]}

    public InitMessage(String hostname, String cpuName, String localIp, String osName, String osVersion, String biosVersion, Double cpuUsage, String ramMax, String ramUsage, String driveUsage, String taskList, String backgroundImage, String from) {
        super("init");
        this.hostname = hostname;
        this.cpuName = cpuName;
        this.localIp = localIp;
        this.osName = osName;
        this.osVersion = osVersion;
        this.biosVersion = biosVersion;
        this.cpuUsage = cpuUsage;
        this.ramMax = ramMax;
        this.ramUsage = ramUsage;
        this.driveUsage = driveUsage;
        this.taskList = taskList;
        this.backgroundImage = backgroundImage;
        this.from = from;
    }

    @Override
    public String toJson() {
        return new JSONObject()
                .put("type", "init")
                .put("hostname", this.hostname)
                .put("cpuName", this.cpuName)
                .put("localIp", this.localIp)
                .put("osName", this.osName)
                .put("osVersion", this.osVersion)
                .put("biosVersion", this.biosVersion)
                .put("cpuUsage", this.cpuUsage)
                .put("ramMax", this.ramMax)
                .put("ramUsage", this.ramUsage)
                .put("driveUsage", this.driveUsage)
                .put("taskList", this.taskList)
                .put("backgroundImage", this.backgroundImage)
                .put("target",this.target)
                .put("from",this.from)
                .toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}