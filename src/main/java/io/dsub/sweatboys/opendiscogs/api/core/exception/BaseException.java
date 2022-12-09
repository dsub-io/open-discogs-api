package io.dsub.sweatboys.opendiscogs.api.core.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseException extends ResponseStatusException {
    public BaseException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
    public BaseException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
