package io.dsub.sweatboys.opendiscogs.api.core.util;

import java.util.Arrays;

public class StringUtility {
  private static final StringUtility INSTANCE =new StringUtility();

  public static StringUtility getInstance() {
    return INSTANCE;
  }
  private StringUtility(){}

  public String normalize(String in) {
    if (in == null) {
      return null;
    }
    var v = in.trim();
    return v.isEmpty() ? null : v;
  }

  public String getMostChildPath(String path) {
    return Arrays.stream(path.split("\\.")).reduce((prev, curr) -> curr).orElse("");
  }
}
