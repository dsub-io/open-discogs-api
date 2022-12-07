package io.dsub.sweatboys.opendiscogs.api.core.response;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {
  @Builder.Default
  private final List<T> items = Collections.emptyList();
  @Builder.Default
  private final List<Error> errors = Collections.emptyList();
  @Builder.Default
  private int totalElements = 0;
  @Builder.Default
  private int totalPages = 0;
  @Builder.Default
  private int pageNumber = 0;
  @Builder.Default
  private int pageSize = 0;
  @Builder.Default
  private boolean last = false;
  @Builder.Default
  private boolean first = false;
  @Builder.Default
  private boolean sorted = false;
}
