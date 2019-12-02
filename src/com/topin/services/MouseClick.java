package com.topin.services;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClick implements MouseListener {
    private String button; //left or right
    private Integer mouseType; //0 = l click | 1 = bentt | 2 = felenged
    private Robot robot = null;

    public MouseClick(String button, Integer mouseType) throws AWTException {
        this.button = button;
        this.mouseType = mouseType;
        this.robot = new Robot();
    }

    public void run() throws AWTException {
        switch (this.mouseType) {
            case 0:
                oneClick(this.button);
                break;
            case 1:
                loopClick();
                break;
            case 2:
                down();
                break;
        }
    }

    private void down() {
        //
    }

    private void loopClick() {
        //
    }

    private void oneClick(String button) {
        if (this.button.equals("left")) {
            this.oneClickLeft();
        } else if (this.button.equals("right")) {
            this.oneClickRight();
        }
    }

    private void oneClickLeft() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void oneClickRight() {
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }
}

