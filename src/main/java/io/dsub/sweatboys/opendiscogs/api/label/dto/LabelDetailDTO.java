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
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("contact_info")
    private final String contactInfo;
    @JsonProperty("data_quality")
    private final String dataQuality;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("profile")
    private final String profile;
    @JsonProperty("parent_label")
    private final LabelReferenceDTO parentLabel;
    @JsonProperty("sublabels")
    private final List<LabelReferenceDTO> sublabels;
    @JsonProperty("urls")
    private final List<String> urls;

    @JsonProperty("uri")
    public String getUri() {
        return "https://www.discogs.com/label/" + this.id;
    }
    @JsonProperty("release_url")
    public String getReleasesUrl() {
        return "https://api.opendiscogs.com/labels/" + this.id + "/releases";
    }

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
