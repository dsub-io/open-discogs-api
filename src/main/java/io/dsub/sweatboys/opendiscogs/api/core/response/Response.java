package io.dsub.sweatboys.opendiscogs.api.core.response;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
public class Response<T> {
  @Builder.Default
  private final List<T> items = Collections.emptyList();
  @Builder.Default
  private final List<Error> errors = Collections.emptyList();
  @Builder.Default
  private int dataSize = 1;
}
