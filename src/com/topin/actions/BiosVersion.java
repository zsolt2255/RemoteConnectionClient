package com.topin.actions;

public class BiosVersion implements Base {
    @Override
    public String command() {
        return "wmic bios get smbiosbiosversion";
    }
}