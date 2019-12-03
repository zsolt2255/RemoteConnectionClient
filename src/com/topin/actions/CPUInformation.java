package com.topin.actions;

public class CPUInformation implements Base {
    //private String prefix = "wmic cpu get loadpercentage";
    //private String prefix = "wmic cpu get caption, deviceid, name, numberofcores, maxclockspeed, status";
    //private String prefix = "wmic cpu list full";
    //private String prefix = "wmic cpu get loadpercentage";

    @Override
    public String command() {
        return "wmic cpu get loadpercentage";
    }
}
