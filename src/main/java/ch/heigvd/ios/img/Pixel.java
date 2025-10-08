package ch.heigvd.ios.img;

public class Pixel {
    private byte blue;
    private byte green;
    private byte red;

    public Pixel(byte blue, byte green, byte red) {
        this.blue = blue;
        this.green = green;
        this.red = red;
    }

    public byte getRed() {
        return red;
    }

    public byte getGreen() {
        return green;
    }

    public byte getBlue() {
        return blue;
    }

    public byte[] getPixel() {
        return new byte[]{red, green, blue};
    }

    public void setRed(byte red) {
        this.red = red;
    }

    public void setGreen(byte green) {
        this.green = green;
    }

    public void setBlue(byte blue) {
        this.blue = blue;
    }

    public void setPixel(byte blue, byte green, byte red) {
        setBlue(blue);
        setGreen(green);
        setRed(red);
    }
}
