package com.topin.actions;

public class StaticCommand {

    private String prefix = "cmd.exe /c";

    public StaticCommand(String command) {
        this.prefix += command;
    }

    @Override
    public String toString() {
        return this.prefix;
    }
}
