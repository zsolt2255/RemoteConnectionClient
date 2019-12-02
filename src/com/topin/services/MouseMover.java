package com.topin.services;

import java.awt.*;

public class MouseMover {
    private Integer x,y;

    public MouseMover(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public void run() throws AWTException {
        Robot robot = new Robot();
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        robot.mouseMove((int) (mouseX-this.x), (int) (mouseY-this.y));
    }
}