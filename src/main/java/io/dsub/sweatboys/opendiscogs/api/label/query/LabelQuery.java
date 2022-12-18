package io.dsub.sweatboys.opendiscogs.api.label.query;

import io.dsub.sweatboys.opendiscogs.api.core.util.StringUtility;
import io.dsub.sweatboys.opendiscogs.api.label.domain.Label;
import lombok.Builder;

/**
 * Mapped query object for labels query.
 * @param contactInfo Contact Info to search for label
 * @param dataQuality Data quality to search for label
 * @param name Name to search for label
 * @param profile Profile to search for label
 */
@Builder
public record LabelQuery (
    String contactInfo,
    String dataQuality,
    String name,
    String profile) {
    public Label toLabel() {
        return Label.builder()
                .contactInfo(ofNormalized(contactInfo))
                .dataQuality(ofNormalized(dataQuality))
                .name(ofNormalized(name))
                .profile(ofNormalized(profile))
                .build();
    }

    private String ofNormalized(String in) {
        return StringUtility.normalize(in);
    }
}
