package io.dsub.sweatboys.opendiscogs.api.core.exception;

public class UnsupportedDatabaseException extends BaseCheckedException {

    public UnsupportedDatabaseException(String driverName) {
        super(createMessageFromDriverName(driverName));
    }

    public UnsupportedDatabaseException(String driverName, Throwable cause) {
        super(createMessageFromDriverName(driverName), cause);
    }

    private static String createMessageFromDriverName(String driverName) {
        return String.format("database %s is not supported", driverName);
    }
}
