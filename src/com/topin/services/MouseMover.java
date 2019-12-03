package com.topin.services;

import java.awt.*;
import java.util.Objects;

public class MouseMover implements Runnable {
    private Integer x,y;
    private Robot robot = new Robot();

    /**
     * @param x
     * @param y
     * @throws AWTException
     */
    public MouseMover(Integer x, Integer y) throws AWTException {
        this.x = x;
        this.y = y;
    }

    public void run() {
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();

        Objects.requireNonNull(this.robot).mouseMove((int) (mouseX-this.x), (int) (mouseY-this.y));
    }
}