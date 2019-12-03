package com.topin.services;

import com.topin.model.command.LoginConnectMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerDefaultMessageSender {
    private List<Object> messages = new ArrayList<Object>();

    /**
     * @throws IOException
     */
    public ServerDefaultMessageSender() throws IOException {
        this.messages = this.addMessages();
    }

    /**
     * @return List
     * @throws IOException
     */
    private List<Object> addMessages() throws IOException {
        //LoginConnectMessage initialize
        LoginConnectMessage loginConnectMessage = new LoginConnectMessage("admin","admin");
        messages.add(loginConnectMessage.toJson());

        return this.messages;
    }

    /**
     * @return List
     */
    public List<Object> getAllCommand()
    {
        return this.messages;
    }
}
