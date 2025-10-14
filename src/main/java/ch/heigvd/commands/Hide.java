package ch.heigvd.commands;

import static ch.heigvd.ios.json.jsonTools.exportToJson;

import ch.heigvd.ios.img.BMP;
import ch.heigvd.ios.img.Pixel;
import java.io.*;
import java.util.Random;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(
    name = "hide",
    description = "Hide an little SUS in a BMP file.",
    version = "hide 1.0")
public class Hide implements Callable<Integer> {

  @CommandLine.ParentCommand protected Root parent;

  // There are the options for the hide command
  @Option(
      names = {"-c", "--color"},
      paramLabel = "<color>",
      description =
          "change the color for all SUS to hide\n"
              + "Format: B,G,R (Blue, Green, Red) in hexadecimal separate with ',' without space.\n"
              + "Exemple for purple: 0xff,0x00,0xff",
      defaultValue = "0x00,0x00,0xff")
  protected String color;

  @Option(
      names = {"-n", "--number"},
      paramLabel = "<number of SUS>",
      description = "indicate how many SUS to hide",
      defaultValue = "1")
  protected int number;

  @Option(
      names = {"-l", "--left"},
      description = "indicate sus head is left, default the sus head is right")
  protected boolean left;

  @Option(
      names = {"-o", "--output"},
      paramLabel = "<output filename>",
      description = "specificy the output filename",
      defaultValue = "output.bmp")
  protected String outputFileName;

  @Option(
      names = {"-j", "--json"},
      description = "second output, with the coordinates of each sus hidden")
  protected boolean json;

  protected String jsonFilename;

  @Override
  public Integer call() {
    // declaration of variables
    Pixel red = new Pixel(0x0, 0x0, 0xff);

    // Convert the color string to a Color object
    Pixel newColor = new Pixel(color);

    // Check user input
    checkUserInput(newColor);

    // Message to user
    System.out.println("Hiding " + number + " SUS in " + parent.getFilename());
    System.out.println("Color: " + color);

    // Select the sus motif
    BMP sus = new BMP();
    if (left) {
      sus.read("sus_left.bmp");
    } else {
      sus.read("sus_right.bmp");
    }

    // Change color of sus if needed
    if (!newColor.equals(red)) {
      sus.changeColor(red, newColor);
    }

    // Read the target BMP file
    BMP src = new BMP();
    src.read(parent.getFilename());

    // Check if the number of SUS to hide is possible
    checkMaxNumber(src, sus);

    // Calculate the positions to hide the SUS
    int[][] positions = generatePositions(src, sus);

    // Hiding sus in the image
    Pixel[][] srcPixels = hideSUS(src, sus, positions);

    // Save the modified image
    saveOutput(src, srcPixels, positions);

    return 0;
  }

  private void checkUserInput(Pixel newColor) {
    Pixel white = new Pixel(0xff, 0xff, 0xff);

    // Checking users input
    try {
      if (!parent.getFilename().endsWith(".bmp")) {
        throw new IllegalArgumentException("Only BMP files are supported");
      }

      if (number < 1) {
        throw new IllegalArgumentException("Number of SUS must be greater than zero");
      }

      if (newColor.equals(white)) {
        throw new IllegalArgumentException("SUS can not be perfect white, there is not innocence.");
      }

      if (!outputFileName.endsWith(".bmp")) {
        System.err.println("For output file, the wrong extension has been given by the users");
        System.err.println("Start procedure to change extension name");
        outputFileName = outputFileName.split("\\.")[0] + ".bmp";
        System.err.println(
            "End procedure to change extension name, the new name is " + outputFileName);
      }

      // Check if the option
      if (json) {
        jsonFilename = outputFileName.split("\\.bmp")[0] + ".json";
        System.out.println("The coordinate file has the name : " + jsonFilename);
      }

    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  private void checkMaxNumber(BMP src, BMP sus) {
    // Calculate the max number of SUS that can fit in the source image
    int maxCountWidth = src.getWidth() / sus.getWidth();
    int maxCountHeight = src.getHeight() / sus.getHeight();
    int maxCount =
        (int)
            (maxCountWidth * maxCountHeight / 1.5); // divide by 1,5 for enough place for no overlap

    if (number > maxCount) {
      throw new IllegalArgumentException(
          "Error: cannot hide " + number + " SUS in the image, max is " + maxCount);
    }
  }

  private int[][] generatePositions(BMP src, BMP sus) {
    int[][] coordinates = new int[number][2];

    int susWidth = sus.getWidth();
    int susHeight = sus.getHeight();
    int srcWidth = src.getWidth();
    int srcHeight = src.getHeight();

    // Start random position generator
    Random rand = new Random();

    for (int i = 0; i < number; i++) {
      int x = rand.nextInt(0, srcWidth - susWidth);
      int y = rand.nextInt(0, srcHeight - susHeight);

      // Check for overlap with previous positions
      for (int j = 0; j < i; j++) {
        int prevX = coordinates[j][0];
        int prevY = coordinates[j][1];
        if (Math.abs(x - prevX) < susWidth && Math.abs(y - prevY) < susHeight) {
          // Overlap detected, generate a new position
          x = rand.nextInt(0, srcWidth - susWidth);
          y = rand.nextInt(0, srcHeight - susHeight);
          j = -1; // Restart checking from the first position
        }
      }

      // Save the position
      coordinates[i][0] = x;
      coordinates[i][1] = y;
    }
    return coordinates;
  }

  private Pixel[][] hideSUS(BMP src, BMP sus, int[][] positions) {
    // Get the pixels of the source image
    Pixel[][] srcPixels = src.getPixels();

    // Get the pixels of the sus image
    Pixel[][] susPixels = sus.getPixels();

    // Get dimensions of sus
    int susWidth = sus.getWidth();
    int susHeight = sus.getHeight();

    // Hide the SUS in the source image at the calculated positions
    for (int i = 0; i < number; i++) {
      int posX = positions[i][0];
      int posY = positions[i][1];

      for (int y = 0; y < susHeight; y++) {
        for (int x = 0; x < susWidth; x++) {
          // Get the pixel from the sus image
          Pixel susPixel = susPixels[y][x];

          // Only hide non-white pixels exept the eye pixel
          if (!susPixel.equals(new Pixel(0xff, 0xff, 0xff))
              || (y == 3 && ((left && x == 0) || (!left && x == 3)))) {
            srcPixels[posY + y][posX + x].setPixel(susPixel);
          }
        }
      }
    }
    return srcPixels;
  }

  private void saveOutput(BMP src, Pixel[][] srcPixels, int[][] positions) {
    src.setImageBMP(srcPixels);
    src.write(outputFileName);
    System.out.println("Output saved to " + outputFileName);

    // Generate the file json
    if (json) {
      try {
        exportToJson(positions, jsonFilename);
      } catch (IOException ios) {
        System.err.println(ios.getMessage() + "\nNo json file generated");
      }
      System.out.println("Coordinate saved to " + jsonFilename);
    }
  }
}
