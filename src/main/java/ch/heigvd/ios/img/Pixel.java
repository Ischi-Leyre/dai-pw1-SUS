/**
 * @athors : Arnaut Leyre, Marc Ischi
 * @description : This class is used to represent a pixel in a BMP file, with 3 color components (B, G, R).
 */

package ch.heigvd.ios.img;

public class Pixel {
  private byte[] data;

  public Pixel(int blue, int green, int red) {
    try {
      if (blue < 0 || green < 0 || red < 0 || blue > 0xff || green > 0xff || red > 0xff) {
        throw new NumberFormatException("Error: color components must be in range [0x00-0xFF]");
      }

      this.data = new byte[] {(byte) blue, (byte) green, (byte) red};
    } catch (NumberFormatException e) {
      System.err.println(
          e.getMessage() + "\twith hexadecimal values [0x00-0xFF]\n" + "\tdecimal values [0-255]");
      System.err.println("Program aborted.");
      System.exit(1);
    }
  }

  public Pixel(String color) {
    String[] colors = color.split(",");
    try {
      // Check if we have exactly 3 components
      if (colors.length != 3) {
        throw new Error("Error: color must be 3 components in format B,G,R");
      }

      // Decode hexadecimal values and clamp them to [0x00, 0xFF]
      int blue = Integer.decode(colors[0]);
      if (blue < 0 || blue > 0xff) {
        throw new NumberFormatException("Error blue : ");
      }

      int green = Integer.decode(colors[1]);
      if (green < 0 || green > 0xff) {
        throw new NumberFormatException("Error green : ");
      }

      int red = Integer.decode(colors[2]);
      if (red < 0 || red > 0xff) {
        throw new NumberFormatException("Error red: ");
      }

      // Create pixel
      this.data = new byte[] {(byte) blue, (byte) green, (byte) red};
    } catch (NumberFormatException e) {
      System.err.println(
          e.getMessage()
              + "color must be in format B,G,R.\n"
              + "\twith hexadecimal values [0x00-0xFF]\n"
              + "\tdecimal values [0-255]");
      System.err.println("Program aborted.");
      System.exit(1);
    } catch (Error e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  public int getRed() {
    return data[2] & 0xff;
  }

  public int getGreen() {
    return data[1] & 0xff;
  }

  public int getBlue() {
    return data[0] & 0xff;
  }

  public byte[] getPixel() {
    return data;
  }

  public void setRed(int red) {
    if (red >= 0 && red <= 0xff) {
      this.data[2] = (byte) red;
    } else {
      System.err.println("Error: red component must be in range [0x00-0xFF], not set");
    }
  }

  public void setGreen(int green) {
    if (green >= 0 && green <= 0xff) {
      this.data[1] = (byte) green;
    } else {
      System.err.println("Error: green component must be in range [0x00-0xFF], not set");
    }
  }

  public void setBlue(int blue) {
    if (blue >= 0 && blue <= 0xff) {
      this.data[0] = (byte) blue;
    } else {
      System.err.println("Error: blue component must be in range [0x00-0xFF], not set");
    }
  }

  public void setPixel(int blue, int green, int red) {
    this.setBlue(blue);
    this.setGreen(green);
    this.setRed(red);
  }

  public void setPixel(Pixel pixel) {
    this.data = pixel.getPixel();
  }

  public boolean equals(Pixel pixel) {
    return pixel.getRed() == this.getRed()
        && pixel.getGreen() == this.getGreen()
        && pixel.getBlue() == this.getBlue();
  }
}
