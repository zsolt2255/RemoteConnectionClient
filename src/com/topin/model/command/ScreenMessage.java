package com.topin.model.command;

import com.topin.model.Message;
import org.json.JSONObject;

public class ScreenMessage extends Message {
    private String imageBase64;
    private String target = "1";
    private String from;

    /**
     * @param imageBase64
     * @param from
     */
    public ScreenMessage(String imageBase64,String from) {
        super("screenshot");
        this.imageBase64 = imageBase64;
        this.from = from;
    }

    @Override
    public String toJson() {
        return new JSONObject()
                .put("type","screenshot")
                .put("imageBase64",this.imageBase64)
                .put("from",this.from)
                .put("target",this.target)
                .toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
