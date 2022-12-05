package io.dsub.sweatboys.opendiscogs.api.exception;

public class InvalidEnvironmentVariableException extends EnvironmentVariableException {
    public InvalidEnvironmentVariableException(String key) {
        super(createMessageFromEnvironmentKey(key));
    }

    public InvalidEnvironmentVariableException(String key, Throwable cause) {
        super(createMessageFromEnvironmentKey(key), cause);
    }

    private static String createMessageFromEnvironmentKey(String key) {
        return String.format("environment variable %s has invalid value", key);
    }
}
