package com.topin.model.command;

import com.topin.model.Message;
import org.json.JSONObject;

public class LoginMessage extends Message {
    private String clientType;
    private String token;

    /**
     * @param clientType
     * @param token
     */
    public LoginMessage(String clientType, String token) {
        super("login");
        this.clientType = clientType;
        this.token = token;
    }

    @Override
    public String toJson() {
        return new JSONObject()
                .put("clientType", this.clientType)
                .put("token", this.token)
                .put("type", "login")
                .toString();
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public String getClientType() {
        return clientType;
    }

    public String getToken() {
        return token;
    }
}
