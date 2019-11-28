package com.topin.services;

import com.topin.actions.CPUInformation;
import com.topin.actions.StaticCommand;
import com.topin.model.command.CPUMessage;
import com.topin.model.command.InitMessage;
import com.topin.model.command.LoginConnectMessage;
import com.topin.model.command.LoginMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerDefaultMessageSender {
    private List<Object> messages = new ArrayList<Object>();

    public ServerDefaultMessageSender() throws IOException {
        this.messages = this.addMessages();
    }

    private List<Object> addMessages() throws IOException {
        LoginConnectMessage loginConnectMessage = new LoginConnectMessage("admin","admin");
        messages.add(loginConnectMessage.toJson());
        return this.messages;
    }

    public static boolean indexInBound(String[] data, int index){
        return data != null && index >= 0 && index < data.length;
    }

    public List<Object> getAllCommand()
    {
        return this.messages;
    }
}
