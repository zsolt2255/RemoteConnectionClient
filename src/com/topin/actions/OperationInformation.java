package com.topin.actions;

public class OperationInformation extends Base {
    private String prefix = "systeminfo | findstr /B /C:\"OS Name\" /C:\"OS Version\"";

    @Override
    public String toString() {
        return this.prefix;
    }
}