package io.dsub.sweatboys.opendiscogs.api.util;

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
}
