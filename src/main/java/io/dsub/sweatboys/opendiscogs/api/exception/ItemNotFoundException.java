package io.dsub.sweatboys.opendiscogs.api.exception;

public class ItemNotFoundException extends BaseCheckedException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}