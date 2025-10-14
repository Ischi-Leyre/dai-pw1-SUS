/**
 * @author : Arnaut Leyre, Marc Ischi
 * @description : Command tha search for all occurence of a 4 by 5 pixel pattern in a bmp image
 */

package ch.heigvd.commands;

import ch.heigvd.ios.img.BMP;
import ch.heigvd.ios.img.Pixel;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "search",
    mixinStandardHelpOptions = true,
    version = "Search 1.0",
    description =
        "The program opens a BMP image and searches for occurrences of a predefined SUS motif.")
public class Search implements Callable<Integer> {
  @CommandLine.ParentCommand protected Root parent;

  @Option(
      names = {"-c", "--color"},
      paramLabel = "<color>",
      description =
          "change the color for all SUS to search\n"
              + "Format: B,G,R (Blue, Green, Red) in hexadecimal separate with ',' without space.\n"
              + "Exemple for purple: 0xff,0x00,0xff",
      defaultValue = "0x00,0x00,0xff")
  protected static String color;

  @Option(
      names = {"-f", "--fill"},
      description =
          "Fill a copy of the input file with all pixel not corresponding " + "to pattern in white")
  protected boolean fill = false;

  @CommandLine.Option(
      names = {"-l", "--left"},
      description = "indicate the side where he see")
  protected boolean left;

  @Override
  public Integer call() throws Exception {
    // Message to user
    System.out.println("Searching SUS in " + parent.getFilename());

    // declaration of variables
    Pixel white = new Pixel(0xff, 0xff, 0xff);
    Pixel red = new Pixel(0x00, 0x00, 0xff);
    Pixel newColor = new Pixel(color);
    int count = 0;

    // Checking users input
    try {
      if (!parent.getFilename().endsWith(".bmp")) {
        throw new IllegalArgumentException("Only BMP files are supported");
      }
      if (newColor.equals(white)) {
        throw new IllegalArgumentException("SUS can not be perfect white, there is not innocence.");
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    // Select the sus motif
    BMP sus = new BMP();
    if (left) {
      sus.read("sus_left.bmp");
    } else {
      sus.read("sus_right.bmp");
    }

    // Get dimensions of sus
    int susWidth = sus.getWidth();
    int susHeight = sus.getHeight();

    // Setup SUS pattern
    Pixel[][] susPixels = sus.getPixels();
    for (int i = 0; i < susHeight; i++)
      for (int j = 0; j < susWidth; j++) {
        susPixels[i][j] = (susPixels[i][j].equals(red)) ? newColor : white;
      }

    // Read the target BMP file
    BMP src = new BMP();
    src.read(parent.getFilename());
    Pixel[][] srcPixels = src.getPixels();

    // Get dimensions of source
    int srcWidth = src.getWidth();
    int srcHeight = src.getHeight();

    // initialise an output BMP file if needed
    BMP dst = new BMP();
    Pixel[][] dstPixels;
    dst.read(parent.getFilename());
    dstPixels = dst.getPixels();
    if (fill) {
      for (int y = 0; y < srcHeight; y++) {
        for (int x = 0; x < srcWidth; x++) {
          dstPixels[y][x].setPixel(white);
        }
      }
    }

    // searching
    for (int y = 0; y <= srcHeight - susHeight; y++) {
      for (int x = 0; x <= srcWidth - susWidth; x++) {
        // launch search at coord y,x
        boolean imposter =
            isImpostor(srcPixels, susPixels, susHeight, susWidth, y, x, white, newColor);

        // react to SUS found
        if (!imposter) {
          count++;
          if (fill) {
            popSUS(dstPixels, susPixels, susHeight, susWidth, y, x, white);
          }
        }
      }
    }

    // Save the modified image
    if (fill) {
      String filename_no_ext =
          parent.getFilename().substring(0, parent.getFilename().lastIndexOf('.'));
      String outputFileName = filename_no_ext + "_catch.bmp";
      dst.setImageBMP(dstPixels);
      dst.write(outputFileName);
      System.out.println("Output saved to " + outputFileName);
    }
    System.out.println("Count :" + count + "\n");
    return 0;
  }

  private boolean isImpostor(
      Pixel[][] srcPixels,
      Pixel[][] susPixels,
      int susHeight,
      int susWidth,
      int y,
      int x,
      Pixel white,
      Pixel newColor) {
    // check for white pixel as glass of helmet of SUS
    boolean imposter =
        (left && !srcPixels[y + 3][x].equals(white))
            || (!left && !srcPixels[y + 3][x + 3].equals(white));

    // check for SUS
    for (int z = 0; z < susHeight && !imposter; z++) {
      for (int k = 0; k < susWidth && !imposter; k++) {
        imposter =
            !(((susPixels[z][k].equals(white) && !srcPixels[y + z][x + k].equals(newColor))
                || (susPixels[z][k].equals(newColor) && srcPixels[y + z][x + k].equals(newColor))));
      }
    }
    return imposter;
  }

  private void popSUS(
      Pixel[][] dstPixels,
      Pixel[][] susPixels,
      int susHeight,
      int susWidth,
      int y,
      int x,
      Pixel white) {
    for (int z = 0; z < susHeight; z++) {
      for (int k = 0; k < susWidth; k++) {
        // Get the pixel from the sus image
        Pixel susPixel = susPixels[z][k];

        // Only hide non-white pixels exept the eye pixel
        if (!susPixel.equals(white) || (z == 3 && ((left && k == 0) || (!left && k == 3)))) {
          dstPixels[y + z][x + k].setPixel(susPixel);
        }
      }
    }
  }
}
