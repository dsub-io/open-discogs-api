package io.dsub.sweatboys.opendiscogs.api.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@With
@Getter
@Builder
@AllArgsConstructor
public class PagedResponseDTO<T> {

  @Builder.Default
  private final List<T> items = Collections.emptyList();
  @Builder.Default
  @JsonProperty("total_elements")
  private long totalElements = 0;
  @Builder.Default
  @JsonProperty("total_pages")
  private int totalPages = 0;
  @Builder.Default
  @JsonProperty("page_number")
  private int pageNumber = 0;
  @Builder.Default
  @JsonProperty("page_size")
  private int pageSize = 0;
  @Builder.Default
  private boolean last = false;
  @Builder.Default
  private boolean first = false;
  @Builder.Default
  private boolean sorted = false;

  @JsonProperty("resource_uri")
  @Builder.Default
  String resourceURI = "";

  public static <T> Mono<PagedResponseDTO<T>> fromPage(Page<T> page) {
    return Mono.fromCallable(() -> PagedResponseDTO.<T>builder()
            .items(page.getContent())
            .first(page.isFirst())
            .last(page.isLast())
            .pageNumber(page.getTotalPages() == 0 ? 0 : page.getNumber() + 1)
            .pageSize(page.getNumberOfElements())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .sorted(!page.getSort().isEmpty())
            .build())
        .subscribeOn(Schedulers.boundedElastic());
  }
}
