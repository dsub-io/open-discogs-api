package io.dsub.sweatboys.opendiscogs.api.core.util;

import java.util.Arrays;

public class StringUtility {

  private static final StringUtility INSTANCE = new StringUtility();

  private StringUtility() {
  }

  public static String normalize(String in) {
    return INSTANCE.doNormalize(in);
  }

  public static String getMostChildPath(String path) {
    return INSTANCE.doGetMostChildPath(path);
  }

  private String doNormalize(String in) {
    if (in == null) {
      return null;
    }
    var v = in.trim();
    return v.isEmpty() ? null : v;
  }

  private String doGetMostChildPath(String path) {
    return Arrays.stream(path.split("\\."))
        .reduce((prev, curr) -> {
          if (prev.isEmpty()) {
            return curr;
          }
          return curr.isEmpty() ? prev : curr;
        })
        .orElse("");
  }
}
