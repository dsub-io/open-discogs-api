package io.dsub.sweatboys.opendiscogs.api.label.projection;

import org.springframework.data.relational.core.mapping.Column;

public record LabelDetailProjection(
        @Column("id")
        Long id,
        @Column("contact_info")
        String contactInfo,
        @Column("data_quality")
        String dataQuality,
        @Column("name")
        String name,
        @Column("profile")
        String profile,
        @Column("parent_label_id")
        Long parentLabelId,
        @Column("parent_label_name")
        String parentLabelName
) {
}
