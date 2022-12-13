package io.dsub.sweatboys.opendiscogs.api.label.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@With
@Getter
@Builder
@AllArgsConstructor
@Schema(description = "Detailed information of a label")
public class LabelDetailDTO {
    private final Long id;
    @JsonProperty("contact_info")
    private final String contactInfo;
    @JsonProperty("data_quality")
    private final String dataQuality;
    private final String name;
    private final String profile;
    private final List<LabelReferenceDTO> sublabels;
    private final List<String> urls;


    public String getUri() {
        return "https://www.discogs.com/label/" + this.id;
    }

    public String getReleasesUrl() {
        return "https://api.opendiscogs.com/labels/" + this.id + "/releases";
    }

    // Todo - point
    public static Mono<LabelDetailDTO> fromLabel(Label label) {
        return Mono.fromCallable(() -> LabelDetailDTO.builder()
                .id(label.getId())
                .contactInfo(label.getContactInfo())
                .dataQuality(label.getDataQuality())
                .name(label.getName())
                .profile(label.getProfile())
            .build())
            .subscribeOn(Schedulers.boundedElastic());
    }
}
