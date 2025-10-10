/**
 @author : Arnaut Leyre, Marc Ischi
 Description : Command tha search for all occurence of a 4 by 5 pixel pattern in a bmp image
 TODO : add features
 */
package ch.heigvd.commands;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "search",
    mixinStandardHelpOptions = true,
    version = "Search Demo",
    description =
        "The program opens a BMP image and searches for occurrences of a predefined motif.")
public class Search implements Callable<Integer> {
  @CommandLine.ParentCommand protected Root parent;

  @Option(
      names = {"-c", "--color"},
      description =
          "hex value for the color of the pattern to search, white pixel on"
              + "the original pattern will remain white")
  private static String color = "0xffffff";

  @Option(
      names = {"-f", "--fill"},
      description =
          "Fill a copy of the input file with all pixel not corresponding " + "to pattern in black")
  private static boolean fill = false;

  @Override
  public Integer call() throws Exception {
    System.out.println(color);
    //TODO features
    return 0;
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new Search()).execute(args);
    System.exit(exitCode);
  }
}
