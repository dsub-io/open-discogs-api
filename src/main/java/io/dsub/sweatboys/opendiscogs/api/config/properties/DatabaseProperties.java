package io.dsub.sweatboys.opendiscogs.api.config.properties;

import io.dsub.sweatboys.opendiscogs.api.exception.EnvironmentVariableException;
import io.dsub.sweatboys.opendiscogs.api.exception.MissingRequiredEnvironmentVariableException;
import io.dsub.sweatboys.opendiscogs.api.Constants;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;

@Getter
public class DatabaseProperties implements InitializingBean {

  private final String username;
  private final String password;
  private final String url;

  public DatabaseProperties(Environment env) {
    username = readEncodedEnvironmentVariable(env, Constants.DB_USER_KEY);
    password = readEncodedEnvironmentVariable(env, Constants.DB_PASS_KEY);
    url = env.getProperty(Constants.DB_URL_KEY);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    validateUsername();
    validateUrl();
  }

  private void validateUsername() throws EnvironmentVariableException {
    if (this.username == null || this.username.isBlank()) {
      throw new MissingRequiredEnvironmentVariableException(Constants.DB_USER_KEY);
    }
  }

  private void validateUrl() throws EnvironmentVariableException {
    if (this.url == null || this.url.isBlank()) {
      throw new MissingRequiredEnvironmentVariableException(Constants.DB_URL_KEY);
    }
  }

  private String readEncodedEnvironmentVariable(Environment env, String key) {
    String value = env.getProperty(key);
    return encodeToUTF8(value);
  }

  private String encodeToUTF8(String in) {
    if (in == null || in.length() == 0) {
      return in;
    }
    return URLEncoder.encode(in, StandardCharsets.UTF_8);
  }
}
