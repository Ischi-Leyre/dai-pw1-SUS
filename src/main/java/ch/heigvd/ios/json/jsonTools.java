package ch.heigvd.ios.json;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class jsonTools {
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
