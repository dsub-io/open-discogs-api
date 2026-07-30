package io.dsub.opendiscogs.api.master.domain;

import io.dsub.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.opendiscogs.api.master.dto.MasterReleaseDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MasterRepository {

  Mono<Page<Master>> findAllBy(Example<Master> example, Pageable pageable);

  Mono<MasterDetailDTO> findById(Long id);

  Flux<MasterReleaseDTO> findReleasesByMasterId(Long id, Pageable pageable);

  Mono<Long> countReleasesByMasterId(Long masterId);
}
