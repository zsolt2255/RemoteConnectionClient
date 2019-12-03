package com.topin.actions;

public class BackgroundImage implements Base {
    @Override
    public String command() {
        return "$TIC=(Get-ItemProperty 'HKCU:\\Control Panel\\Desktop' TranscodedImageCache -ErrorAction Stop).TranscodedImageCache;[System.Text.Encoding]::Unicode.GetString($TIC) -replace '(.+)([A-Z]:[0-9a-zA-Z\\\\])+','$2'";
    }
}