package com.topin.services;

import java.awt.*;

public class KeyCodeMessage implements Runnable{
    private Integer keyCode;
    private Robot robot = new Robot();

    /**
     * @param keyCode
     * @throws AWTException
     */
    public KeyCodeMessage(Integer keyCode) throws AWTException {
        this.keyCode = keyCode;
    }

    @Override
    public void run() {
        this.robot.keyPress(this.keyCode);
        this.robot.keyRelease(this.keyCode);
    }
}

