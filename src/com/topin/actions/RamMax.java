package com.topin.actions;

public class RamMax extends Base {
    private String prefix = "systeminfo | findstr /C:\"Total Physical Memory\"";

    @Override
    public String toString() {
        return this.prefix;
    }
}