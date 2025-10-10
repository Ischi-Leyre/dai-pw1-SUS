package ch.heigvd.ios.img;

public class Pixel {
    private byte[] data;

    public Pixel(int blue, int green, int red) {
        this.data = new byte[] {(byte) blue, (byte) green, (byte) red};
    }

    public Pixel(String color) {
        String[] colors = color.split(",");
        try {
            // Check if we have exactly 3 components
            if(colors.length != 3) {
                throw new Error("Error: color must be 3 components in format B,G,R");
            }

            // Decode hexadecimal values and clamp them to [0x00, 0xFF]
            int blue = Integer.decode(colors[0]);
            blue = blue > 0xff ? 0xff : Math.max(blue, 0x00);

            int green = Integer.decode(colors[1]);
            green = green > 0xff ? 0xff : Math.max(green, 0x00);

            int red = Integer.decode(colors[2]);
            red = red > 0xff ? 0xff : Math.max(red, 0x00);

            // Create pixel
            this.data = new byte[] {(byte) blue, (byte) green, (byte) red};
        } catch (NumberFormatException e) {
            System.err.println("Error: color must be in format B,G,R with hexadecimal values [0x00-0xFF]");
            System.exit(1);
        } catch (Error e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
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
