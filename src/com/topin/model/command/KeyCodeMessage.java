package com.topin.model.command;

import com.topin.model.Message;
import org.json.JSONObject;

public class KeyCodeMessage extends Message {
    private Integer keyCode;

    /**
     * @param keyCode
     */
    public KeyCodeMessage(Integer keyCode) {
        super("keyCode");
        this.keyCode = keyCode;
    }

    @Override
    public String toJson() {
        return new JSONObject()
                .put("type", "keyCode")
                .put("keyCode", this.keyCode)
                .toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}