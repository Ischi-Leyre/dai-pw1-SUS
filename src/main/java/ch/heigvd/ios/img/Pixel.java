package ch.heigvd.ios.img;

public class Pixel {
    private byte[] data;

    public Pixel(int blue, int green, int red) {
        this.data = new byte[] {(byte) blue, (byte) green, (byte) red};
    }

    public Pixel(byte[] pixelData) {
        if (pixelData.length == 3) this.data = pixelData;
        else System.err.println("Pixel data length incorrect.");
    }

    public int getRed() {
        return (data[2] >> 16) & 0xff;
    }

    public int getGreen() {
        return (data[1] >> 8) & 0xff;
    }

    public int getBlue() {
        return data[0] & 0xff;
    }

    public byte[] getPixel() {
        return data;
    }

    public void setRed(int red) {
        this.data[2] = (byte) red;
    }

    public void setGreen(int green) {
        this.data[1] = (byte) green;
    }

    public void setBlue(int blue) {
        this.data[0] = (byte) blue;
    }

    public void setPixel(int blue, int green, int red) {
        this.data = new byte[] {(byte) blue, (byte) green, (byte) red};
    }
}
