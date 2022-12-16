package io.dsub.sweatboys.opendiscogs.api.master.application;

import io.dsub.sweatboys.opendiscogs.api.core.exception.ItemNotFoundException;
import io.dsub.sweatboys.opendiscogs.api.core.response.PagedResponseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import io.dsub.sweatboys.opendiscogs.api.master.domain.MasterRepository;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterDetailDTO;
import io.dsub.sweatboys.opendiscogs.api.master.dto.MasterReleaseDTO;
import io.dsub.sweatboys.opendiscogs.api.master.query.MasterQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final MasterRepository repository;

    public Mono<PagedResponseDTO<Master>> findMasters(MasterQuery query, Pageable pageable) {
        return repository.findAllBy(Example.of(Master.builder()
                        .title(query.getTitle())
                        .releasedYear(query.getYear())
                        .build()), pageable)
                .flatMap(PagedResponseDTO::fromPage);
    }

    public Mono<MasterDetailDTO> getById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("master", id)));
    }

    public Mono<PagedResponseDTO<MasterReleaseDTO>> getMasterSubReleases(Long id, Pageable pageable) {
        return repository.findReleasesByMasterId(id, pageable)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("master", id)))
                .collectList()
                .zipWith(repository.countReleasesByMasterId(id))
                .flatMap(tuple -> PagedResponseDTO.fromPage(new PageImpl<>(tuple.getT1(), pageable, tuple.getT2())));
    }
}
