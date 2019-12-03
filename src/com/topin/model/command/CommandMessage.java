package com.topin.model.command;

import com.topin.model.Message;
import org.json.JSONObject;

public class CommandMessage extends Message {
    private Integer id;
    private String command;
    private String commandType;

    /**
     * @param id
     * @param command
     * @param commandType
     */
    public CommandMessage(Integer id, String command, String commandType) {
        super("command");
        this.id = id;
        this.command = command;
        this.commandType = commandType;
    }

    @Override
    public String toJson() {
        return new JSONObject()
                .put("id", this.id)
                .put("command", this.command)
                .put("commandType", this.commandType)
                .toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
