package com.topin.actions;

public class RamMax implements Base {
    @Override
    public String command() {
        return "systeminfo | findstr /C:\"Total Physical Memory\"";
    }
}