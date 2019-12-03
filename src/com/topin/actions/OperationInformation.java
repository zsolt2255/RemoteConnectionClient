package com.topin.actions;

public class OperationInformation implements Base {
    @Override
    public String command() {
        return "systeminfo | findstr /B /C:\"OS Name\" /C:\"OS Version\"";
    }
}