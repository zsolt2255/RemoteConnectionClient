package com.topin.helpers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.MemoryCacheImageOutputStream;


/**
 * This class will save {@link BufferedImage} into a byte array and compress it so the number of
 * compressed bytes is not higher than a given maximum level. It is not thread-safe.
 *
 * @author Bartosz Firyn (sarxos)
 */
public class AdaptiveSizeWriter {

    private final int max;
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private float q = 1f; // 1f = 100% quality, at the beginning
    private int w = 0;
    private int h = 0;

    public AdaptiveSizeWriter(int size) {
        this.max = size;
    }

    public byte[] write(BufferedImage bi) {

        final int iw = bi.getWidth();
        final int ih = bi.getHeight();
        if (w != iw || h != ih) {
            w = iw;
            h = ih;
            q = 1f;
        }

        // loop and try to compress until compressed image bytes array is not longer than a given
        // maximum value, reduce quality by 25% in every step

        int size = 0;
        do {
            size = compress(bi, q);
            if (size > max) {
                q *= 0.75;
            }
        } while (size > max);

        return baos.toByteArray();
    }

    /**
     * Compress {@link BufferedImage} with a given quality into byte array.
     *
     * @param bi the {@link BufferedImage} to compres into byte array
     * @param quality the compressed image quality (1 = 100%, 0.5 = 50%, 0.1 = 10%, etc)
     * @return The size of compressed data (number of bytes)
     */
    private int compress(BufferedImage bi, float quality) {

        baos.reset();

        final JPEGImageWriteParam params = new JPEGImageWriteParam(null);
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(quality);

        try (MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(baos)) {
            final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            writer.setOutput(mcios);
            writer.write(null, new IIOImage(bi, null, null), params);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        final int size = baos.size();

        //System.out.printf("Quality %.2f, resolution %dx%d, bytes count = %d\n", quality, bi.getWidth(), bi.getHeight(), size);

        return size;
    }
}