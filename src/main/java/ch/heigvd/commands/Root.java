/**
 * @author : Arnaut Leyre, Marc Ischi Description : Command made with picoli that launch the others
 *     command of the project
 */
package ch.heigvd.commands;

import picocli.CommandLine;

@CommandLine.Command(
    description = "A small CLI to experiment with Java IOs.",
    version = "1.0.0",
    subcommands = {
      Hide.class,
      Search.class,
    },
    scope = CommandLine.ScopeType.INHERIT,
    mixinStandardHelpOptions = true)
public class Root {

  @CommandLine.Parameters(index = "0", description = "The name of the file.")
  protected String filename;

  public String getFilename() {
    return filename;
  }
}
