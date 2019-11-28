package com.topin.actions;

public class BiosVersion extends Base {
    private String prefix = "wmic bios get smbiosbiosversion";

    @Override
    public String toString() {
        return this.prefix;
    }
}