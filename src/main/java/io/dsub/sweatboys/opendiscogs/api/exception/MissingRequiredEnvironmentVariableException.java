package io.dsub.sweatboys.opendiscogs.api.exception;

public class MissingRequiredEnvironmentVariableException extends EnvironmentVariableException {

    public MissingRequiredEnvironmentVariableException(String key) {
        super(createMessageFromEnvironmentKey(key));
    }

    public MissingRequiredEnvironmentVariableException(String key, Throwable cause) {
        super(createMessageFromEnvironmentKey(key), cause);
    }

    private static String createMessageFromEnvironmentKey(String key) {
        return String.format("missing required environment variable: %s", key);
    }
}
