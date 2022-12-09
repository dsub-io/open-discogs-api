package io.dsub.sweatboys.opendiscogs.api.core.exception;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends BaseException {
    public ItemNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public ItemNotFoundException(String resource, Object id) {
        super(HttpStatus.NOT_FOUND, String.format("%s with id %s not found", resource, id));
    }
}