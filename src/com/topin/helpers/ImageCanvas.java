package com.topin.helpers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageCanvas extends Canvas {
    private BufferedImage bufferedImage;

    /**
     * @param stringBuffer
     */
    public ImageCanvas(StringBuffer stringBuffer) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String base64 = new String(stringBuffer);
            InputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));

            bufferedImage = ImageIO.read(inputStream);

            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            byteArrayOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return bufferedImage == null
                ? new Dimension(200, 200)
                : new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (bufferedImage != null) {
            int x = (getWidth() - bufferedImage.getWidth()) / 2;
            int y = (getHeight() - bufferedImage.getHeight()) / 2;
            g.drawImage(bufferedImage, x, y, this);
        }
    }
}