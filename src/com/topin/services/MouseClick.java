package com.topin.services;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;

public class MouseClick implements MouseListener,Runnable {
    private String button; //left or right
    private Integer mouseType; //0 = l click | 1 = loop | 2 = release
    private Robot robot = new Robot();

    /**
     * @param button
     * @param mouseType
     * @throws AWTException
     */
    public MouseClick(String button, Integer mouseType) throws AWTException {
        this.button = button;
        this.mouseType = mouseType;
    }

    public void run() {
        switch (this.mouseType) {
            case 0:
                oneClick();
                break;
            case 1:
                loopClick();
                break;
            case 2:
                releaseClick();
                break;
        }
    }

    /**
     * @return void
     */
    private void releaseClick() {
        if (this.button.equals("left")) {
            this.releaseClickRun(BUTTON1_DOWN_MASK);
        } else if (this.button.equals("right")) {
            this.releaseClickRun(BUTTON3_DOWN_MASK);
        }
    }

    /**
     * @param button1DownMask
     */
    private void releaseClickRun(int button1DownMask) {
        robot.mouseRelease(button1DownMask);
    }

    /**
     * @return void
     */
    private void loopClick() {
        if (this.button.equals("left")) {
            this.loopClickRun(BUTTON1_DOWN_MASK);
        } else if (this.button.equals("right")) {
            this.loopClickRun(BUTTON3_DOWN_MASK);
        }
    }

    /**
     * @param button1DownMask
     */
    private void loopClickRun(int button1DownMask) {
        robot.mousePress(button1DownMask);
    }

    /**
     * @return void
     */
    private void oneClick() {
        if (this.button.equals("left")) {
            this.oneClickRun(BUTTON1_DOWN_MASK);
        } else if (this.button.equals("right")) {
            this.oneClickRun(BUTTON3_DOWN_MASK);
        }
    }

    /**
     * @param button1DownMask
     */
    private void oneClickRun(int button1DownMask) {
        robot.mousePress(button1DownMask);
        robot.mouseRelease(button1DownMask);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}

