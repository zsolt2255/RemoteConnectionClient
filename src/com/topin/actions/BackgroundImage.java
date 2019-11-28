package com.topin.actions;

public class BackgroundImage extends Base {
    private String prefix = "$TIC=(Get-ItemProperty 'HKCU:\\Control Panel\\Desktop' TranscodedImageCache -ErrorAction Stop).TranscodedImageCache;[System.Text.Encoding]::Unicode.GetString($TIC) -replace '(.+)([A-Z]:[0-9a-zA-Z\\\\])+','$2'";

    @Override
    public String toString() {
        return this.prefix;
    }
}