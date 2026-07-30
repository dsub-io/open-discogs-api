package io.dsub.opendiscogs.api.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFoundException extends ResponseStatusException {

  private static final long serialVersionUID = 1L;

  public ItemNotFoundException(String resource, Object id) {
    super(HttpStatus.NOT_FOUND, String.format("%s with %s not found", resource, id));
  }
}
