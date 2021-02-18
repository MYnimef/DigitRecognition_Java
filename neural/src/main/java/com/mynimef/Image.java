package com.mynimef;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image {
    public static boolean toArray(double[] array, String fileName) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(fileName));
            final byte[] pixels = ((DataBufferByte) inputImage.getRaster().getDataBuffer()).getData(); // get pixel value as single array from buffered com.mynimef.Image

            for (int pixel = 0; pixel < pixels.length; pixel++) { //this loop allocates pixels value to two dimensional array
                int argb = 0;
                argb = (int) pixels[pixel];
                if (argb != 0) {
                    argb = 1;
                }
                array[pixel] = argb;
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public static void print(int size, double[] num) {
        for (int i = 0; i < size; i++) {
            if (num[i] == 1) {
                System.out.print("1 ");
            }
            else {
                System.out.print("  ");
            }
            if ((i + 1) % 28 == 0) {
                System.out.println();
            }
        }
    }
}