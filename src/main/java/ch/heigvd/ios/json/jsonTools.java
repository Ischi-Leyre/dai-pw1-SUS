/**
 * @author : Arnaut Leyre, Marc Ischi
 * @description : This class contains tools to export data in a JSON file.
 */

package ch.heigvd.ios.json;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class jsonTools {

  /**
   * This function export the coordinates of the sus hidden in a json file
   *
   * @param coordinate the coordinates of the sus hidden
   * @param jsonFilename the name of the json file
   * @throws IOException if there is an error while writing the file
   */
  public static void exportToJson(int[][] coordinate, String jsonFilename) throws IOException {
    StringBuilder json = new StringBuilder();
    json.append("{\n");

    for (int i = 0; i < coordinate.length; ++i) {
      String key = (i + 1) + (i == 0 ? "st" : i == 1 ? "nd" : i == 2 ? "rd" : "th") + " sus : ";
      json.append("\t\"")
          .append(key)
          .append("\": [")
          .append(coordinate[i][0])
          .append(", ")
          .append(coordinate[i][1])
          .append("]");
      if (i < coordinate.length - 1) {
        json.append(",");
      }
      json.append("\n");
    }
    json.append("}");

    try (BufferedOutputStream bosJson =
        new BufferedOutputStream(new FileOutputStream(jsonFilename))) {
      bosJson.write(json.toString().getBytes());
    } catch (IOException ios) {
      throw ios;
    }
  }
}
