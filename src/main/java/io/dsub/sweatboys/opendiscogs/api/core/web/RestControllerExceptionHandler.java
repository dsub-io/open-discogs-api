package io.dsub.sweatboys.opendiscogs.api.core.web;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.ErrorDTO;
import io.dsub.sweatboys.opendiscogs.api.core.response.ResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected Mono<ResponseEntity<Object>> handleServerWebInputException(ServerWebInputException ex,
      HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
    return Mono.fromCallable(() -> ResponseEntity.status(status)
            .<Object>body(ResponseDTO.builder()
                .first(true)
                .last(true)
                .errors(parseServerWebInputException(ex))
                .resourceURI(exchange.getRequest().getPath().value())
                .build()))
        .subscribeOn(Schedulers.boundedElastic());
  }

  @Order(1)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleException(ConstraintViolationException e,
      ServerHttpRequest request) {
    return ResponseEntity.badRequest().body(ResponseDTO.builder()
        .first(true)
        .last(true)
        .errors(parseConstraintViolationException(e))
        .resourceURI(request.getPath().value())
        .build());
  }

  @Order(3)
  @ExceptionHandler({IllegalArgumentException.class, ItemNotFoundException.class,
      ValidationException.class})
  public ResponseEntity<?> handleException(Exception e) {
    return ResponseEntity.badRequest().body("yo..." + e.getMessage());
  }

  @Override
  protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex,
      HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
    return Mono.fromCallable(() -> ResponseEntity
            .status(status)
            .<Object>body(ResponseDTO.builder()
                .first(true)
                .last(true)
                .errors(parseWebExchangeBindException(ex))
                .build()))
        .subscribeOn(
            Schedulers.boundedElastic());
  }

  private List<ErrorDTO> parseWebExchangeBindException(WebExchangeBindException ex) {
    return ex.getFieldErrors().stream()
        .map(err -> ErrorDTO.builder()
            .param(err.getField())
            .reason(err.getDefaultMessage())
            .rejected(err.getRejectedValue())
            .build())
        .toList();
  }

  private List<ErrorDTO> parseConstraintViolationException(ConstraintViolationException exception) {
    return exception.getConstraintViolations().parallelStream()
        .map(cv -> ErrorDTO.builder()
            .param(getMostChildPath(cv))
            .reason(cv.getMessage())
            .rejected(cv.getInvalidValue())
            .build())
        .collect(Collectors.toList());
  }

  private static String getMostChildPath(ConstraintViolation<?> cv) {
    return StringUtility.getInstance().getMostChildPath(cv.getPropertyPath().toString());
  }

  private List<ErrorDTO> parseServerWebInputException(ServerWebInputException ex) {
    String param = getParameterName(ex);
    String reason;
    Object rejected = null;
    if (ex.getCause() instanceof TypeMismatchException err) {
      reason = err.getMessage();
      rejected = err.getValue();
    } else {
      reason = ex.getReason();
    }
    return Collections.singletonList(ErrorDTO.builder()
        .param(param)
        .reason(reason)
        .rejected(rejected)
        .build());
  }

  private static String getParameterName(ServerWebInputException ex) {
    return ex.getMethodParameter() == null ? null : ex.getMethodParameter().getParameterName();
  }

}
