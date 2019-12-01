package com.topin.services;

import com.topin.model.command.ScreenMessage;
import com.topin.socket.Send;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ScreenCapture implements Runnable {
    private final BufferedOutputStream bufferedOutputStream;
    private String quality;

    ScreenCapture(BufferedOutputStream bufferedOutputStream, String quality) {
        this.bufferedOutputStream = bufferedOutputStream;
        this.quality = quality;
    }

    private void screenShotCreate() {
        ScreenMessage screenMessage = new ScreenMessage(this.captureToBase64(), ServerListener.token);

        try {
            Send.message(this.bufferedOutputStream, screenMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        if(! ServerListener.screenStatus) {
            this.screenShotCreate();
        }

       while(ServerListener.screenStatus) {
           this.screenShotCreate();
        }
    }


    private String captureToBase64() {
        Rectangle screenSize = new
                Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenCapture = null;
        String base64Encoded = "";

        try {
            screenCapture = new Robot().createScreenCapture(screenSize);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(getQuality(screenCapture,this.quality), "jpg", baos);
            baos.flush();
            byte[] encodeBase64 = Base64.getEncoder().encode(baos.toByteArray());
            base64Encoded = new String(encodeBase64);
            baos.close();

        } catch (AWTException | IOException e) {
            e.getMessage();
        }

        return base64Encoded;
    }

    private BufferedImage resize(BufferedImage src, int targetSize) {
        if (targetSize <= 0) {
            return src;
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) src.getHeight() / (float) src.getWidth());
        if (ratio <= 1) {
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else {
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return bi;
    }

    public BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    private BufferedImage getQuality(BufferedImage screenCapture, String quality) {
        //high
        //low
        BufferedImage temp = screenCapture;
        switch (this.quality) {
            case "high":
                temp = screenCapture;
                break;
            case "low":
                temp = Scalr.resize(screenCapture,
                        Scalr.Method.SPEED,
                        Scalr.Mode.FIT_TO_WIDTH,
                        640,
                        480,
                        Scalr.OP_ANTIALIAS);
                break;
        }

        return temp;
    }
}