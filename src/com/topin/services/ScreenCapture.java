package com.topin.services;

import com.topin.helpers.Log;
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
    private BufferedOutputStream bufferedOutputStream;
    private String quality = null;

    /**
     * @param bufferedOutputStream
     * @param quality
     */
    ScreenCapture(BufferedOutputStream bufferedOutputStream, String quality) {
        this.bufferedOutputStream = bufferedOutputStream;
        this.quality = quality;
    }

    /**
     * @return void
     */
    private void screenShotCreate() {
        ScreenMessage screenMessage = new ScreenMessage(this.captureToBase64(), ServerListener.token);

        try {
            Send.message(this.bufferedOutputStream, screenMessage);

            Log.write(this).info("ScreenCapture successfully fetched");
        } catch (IOException e) {
            Log.write(this).error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        //One picture
        if(! ServerListener.screenStatus) {
            this.screenShotCreate();
        }

       while(ServerListener.screenStatus) {
           //Loop picture
           this.screenShotCreate();
        }
    }

    /**
     * @return String
     */
    private String captureToBase64() {
        Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenCapture;
        String base64Encoded = null;

        try {
            screenCapture = new Robot().createScreenCapture(screenSize);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(getQuality(screenCapture,this.quality), "jpg", byteArrayOutputStream);
            byteArrayOutputStream.flush();

            byte[] encodeBase64 = Base64.getEncoder().encode(byteArrayOutputStream.toByteArray());
            base64Encoded = new String(encodeBase64);
            byteArrayOutputStream.close();
        } catch (AWTException | IOException e) {
            Log.write(this).error(e.getMessage());
            e.getMessage();
        }

        return base64Encoded;
    }

    /**
     * @param src
     * @param targetSize
     * @return BufferedImage
     */
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

    /**
     * @param img
     * @param targetWidth
     * @param targetHeight
     * @param hint
     * @param higherQuality
     * @return BufferedImage
     */
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
            w = img.getWidth();
            h = img.getHeight();
        } else {
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

    /**
     * @param screenCapture
     * @param quality
     * @return BufferedImage
     */
    private BufferedImage getQuality(BufferedImage screenCapture, String quality) {
        BufferedImage bufferedImage = screenCapture;
        int targetWidth = 640;
        int targetHeight = 480;
        switch (this.quality) {
            case "high":
                bufferedImage = screenCapture;
                break;
            case "low":
                bufferedImage = Scalr.resize(screenCapture,
                        Scalr.Method.SPEED,
                        Scalr.Mode.FIT_TO_WIDTH,
                        targetWidth,
                        targetHeight,
                        Scalr.OP_ANTIALIAS);
                break;
        }

        return bufferedImage;
    }
}