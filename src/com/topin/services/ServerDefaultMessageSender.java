package com.topin.services;

import com.topin.model.command.LoginConnectMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerDefaultMessageSender {
    private List<Object> messages = new ArrayList<Object>();

    public ServerDefaultMessageSender() throws IOException {
        this.messages = this.addMessages();
    }

    private List<Object> addMessages() throws IOException {
        //LoginConnectMessage
        LoginConnectMessage loginConnectMessage = new LoginConnectMessage("admin","admin");
        messages.add(loginConnectMessage.toJson());

        return this.messages;
    }

    public List<Object> getAllCommand()
    {
        return this.messages;
    }
}
