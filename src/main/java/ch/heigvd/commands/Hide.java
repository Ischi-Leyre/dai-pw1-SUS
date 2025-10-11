package ch.heigvd.commands;

import static ch.heigvd.ios.json.jsonTools.exportToJson;

import ch.heigvd.ios.img.BMP;
import ch.heigvd.ios.img.Pixel;
import java.io.*;
import java.util.Random;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "hide", description = "Hide an little SUS in a BMP file.")
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
      description = "indicate the side where he see")
  protected boolean left;

  @Option(
      names = {"-o", "--output"},
      paramLabel = "<output filename>",
      description = "specificy the output filename",
      defaultValue = "output.bmp")
  protected String outputFileName;


}
