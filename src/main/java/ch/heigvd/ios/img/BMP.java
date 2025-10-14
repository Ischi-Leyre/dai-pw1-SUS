/**
 * @authors : Arnaut Leyre, Marc Ischi
 * @description : This class is used to read and write BMP files, and to manipulate the pixels of the file (get and set).
 */

package ch.heigvd.ios.img;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BMP {
    byte[] header;
    byte[] imageBMP;

    public void read(String filename) {
        // Implementation for reading a BMP file
        try (InputStream fis = new FileInputStream(filename);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            header = new byte[54];

            if (bis.read(header) != 54)
                throw new IOException("Fichier trop petit pour être un BMP valide.");

            if (getBitsPerPixel() != 24)
                throw new IOException("Le fichier n'est pas un BMP 24 bits valide.");

            int width = getWidth();
            int height = getHeight();
            if (height < 5 || width < 4)
                throw new IOException("Dimension invalide : " + width + " x " + height);

            int dataOffset = getOffset();

            imageBMP = new byte[width * height * 3];
            bis.skip(dataOffset - header.length);

            if (bis.read(imageBMP) != imageBMP.length)
                throw new IOException("Impossible de lire les données d'image.");

        } catch (Exception e) {
            System.err.println("Error read: " + e.getMessage());
        }
    }

    public void write(String filename) {
        try (OutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            bos.write(header);
            if (getOffset() > header.length)
                bos.write(new byte[getOffset() - header.length]);

            bos.write(imageBMP);
        } catch (Exception e) {
            System.err.println("Error write: " + e.getMessage());
        }
    }

    public byte[] getHeader() {
        return header;
    }

    public byte[] getImageBMP() {
        return imageBMP;
    }

    public int getOffset() {
        return ByteBuffer.wrap(header, 10, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int getWidth() {
        return ByteBuffer.wrap(header, 18, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int getHeight() {
        return ByteBuffer.wrap(header, 22, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int getBitsPerPixel() {
        return ByteBuffer.wrap(header, 28, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public Pixel[][] getPixels() {
        int width = getWidth();
        int height = getHeight();
        Pixel[][] pixels = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = (i * width + j) * 3;
                pixels[i][j] = new Pixel(imageBMP[index] & 0xff,
                                        imageBMP[index + 1] & 0xff,
                                          imageBMP[index + 2] & 0xff);
            }
        }
        return pixels;
    }

    public void setImageBMP(Pixel[][] imagePixels) {
        int width = getWidth();
        int height = getHeight();
        if (imagePixels.length != height || imagePixels[0].length != width) {
            throw new IllegalArgumentException("Dimensions des pixels incorrectes.");
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = (i * width + j) * 3;
                byte[] pixelData = imagePixels[i][j].getPixel();
                imageBMP[index] = pixelData[0];
                imageBMP[index + 1] = pixelData[1];
                imageBMP[index + 2] = pixelData[2];
            }
        }
    }

    public void changeColor(Pixel targetColor, Pixel newColor) {
        int width = getWidth();
        int height = getHeight();
        Pixel[][] pixels = getPixels();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (pixels[i][j].equals(targetColor))
                    pixels[i][j].setPixel(newColor);
            }
        }
        setImageBMP(pixels);
    }
}
