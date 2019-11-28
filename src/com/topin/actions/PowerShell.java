package com.topin.actions;

public class PowerShell {

    private String prefix = "powershell -command ";

    public PowerShell(String command) {
        this.prefix += '"'+command+'"';
    }

    @Override
    public String toString() {
        return this.prefix;
    }
}