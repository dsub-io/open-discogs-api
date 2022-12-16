package io.dsub.sweatboys.opendiscogs.api.core.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.core.exception.BaseException;
import io.dsub.sweatboys.opendiscogs.api.core.response.ErrorDTO;
import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// TODO: need to standardize error with simple message and status code via BaseException.
@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected Mono<ResponseEntity<Object>> handleServerWebInputException(ServerWebInputException ex,
      HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
    return Mono.fromCallable(() -> ResponseEntity.status(status)
            .<Object>body(parseServerWebInputException(ex)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public Mono<ResponseEntity<Object>> handleException(ConstraintViolationException e,
      ServerHttpRequest request) {
    return Mono.fromCallable(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .<Object>body(parseConstraintViolationException(e)))
        .subscribeOn(Schedulers.boundedElastic());
  }

  @ExceptionHandler(DataAccessException.class)
  public Mono<ResponseEntity<Object>> handleException(DataAccessException e) {
    return Mono.fromCallable(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .<Object>body(new SimpleError(e.getMostSpecificCause().getMessage())))
        .subscribeOn(Schedulers.boundedElastic());
  }


  @ExceptionHandler(BaseException.class)
  public Mono<ResponseEntity<Object>> handleException(BaseException e) {
    return Mono.fromCallable(() -> ResponseEntity.status(e.getStatusCode())
            .<Object>body(new SimpleError(e.getReason())))
        .subscribeOn(Schedulers.boundedElastic());
  }

  @Override
  protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex,
      HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
    return Mono.fromCallable(() ->
            ResponseEntity.status(status).<Object>body(parseWebExchangeBindException(ex)))
        .subscribeOn(Schedulers.boundedElastic());
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
    return exception.getConstraintViolations()
        .parallelStream()
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
      reason = err.getLocalizedMessage().split(";")[0];
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

  private record SimpleError(@JsonProperty("error") String message) {}
}
