package com.topin.model.command.help;

import org.json.JSONObject;

public class ProcessSingle {
    private String pid;
    private String name;
    private String memoryUsage;

    /**
     * @param pid
     * @param name
     * @param memoryUsage
     */
    public ProcessSingle(String pid, String name, String memoryUsage) {
        this.pid = pid;
        this.name = name;
        this.memoryUsage = memoryUsage;
    }

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name",name);
        jsonObject.put("pid",pid);
        jsonObject.put("memUsage",memoryUsage);

        return String.valueOf(jsonObject);
    }
}