package com.topin.model.command;

import com.topin.model.Message;
import org.json.JSONObject;

import java.util.Arrays;

public class CPUMessage extends Message {
    private String[] name;
    private String[] value;

    public CPUMessage(String[] name, String[] value) {
        super("CPU");
        this.name = name;
        this.value = value;
    }

    @Override
    public String toJson() {
        JSONObject asd = new JSONObject();
        for (String names : name) {
            for (String values : value) {
                if (names != null && values != null) {
                    asd.put(names,values);
                }
            }
        }
        return asd.toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
