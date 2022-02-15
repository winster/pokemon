package com.example.pokemon.util;

import java.util.Scanner;

public class FileUtil {
  public static String getSchemaFromFileName(final String filename) {
    return new Scanner(FileUtil.class.getResourceAsStream("/graphql/" + filename + ".graphql"), "UTF-8")
            .useDelimiter("\\A").next();
  }
}
