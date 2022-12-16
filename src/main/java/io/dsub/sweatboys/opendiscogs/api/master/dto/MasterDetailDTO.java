package io.dsub.sweatboys.opendiscogs.api.master.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.artist.dto.ArtistReferenceDTO;
import io.dsub.sweatboys.opendiscogs.api.master.domain.Master;
import java.util.List;
import lombok.Builder;
import lombok.With;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@With
@Builder
public record MasterDetailDTO(
    @JsonProperty("id")
    Long id,
    @JsonProperty("title")
    String title,
    @JsonProperty("data_quality")
    String dataQuality,
    @JsonProperty("main_release")
    Long mainRelease,
    @JsonProperty("year")
    Integer year,
    @JsonProperty("genres")
    List<String> genres,
    @JsonProperty("styles")
    List<String> styles,
    @JsonProperty("artists")
    List<ArtistReferenceDTO> artists,
    @JsonProperty("videos")
    List<MasterVideoDTO> videos
) {
    public static Mono<MasterDetailDTO> fromMaster(Master master) {
        return Mono.fromCallable(() -> MasterDetailDTO.builder()
                .id(master.getId())
                .title(master.getTitle())
                .dataQuality(master.getDataQuality())
                .year(master.getReleasedYear())
                .build())
            .subscribeOn(Schedulers.boundedElastic());
    }
}