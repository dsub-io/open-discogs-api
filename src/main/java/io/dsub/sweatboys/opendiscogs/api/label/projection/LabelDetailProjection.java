package io.dsub.sweatboys.opendiscogs.api.label.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.relational.core.mapping.Column;

public record LabelDetailProjection(
   @JsonProperty("id")
   @Column("id")
   Long id,
   @JsonProperty("contact_info")
   @Column("contact_info")
   String contactInfo,
   @JsonProperty("data_quality")
   @Column("data_quality")
   String dataQuality,
   @JsonProperty("name")
   @Column("name")
   String name,
   @JsonProperty("profile")
   @Column("profile")
   String profile,
   @JsonProperty("parent_label_id")
   @Column("parent_label_id")
   Long parentLabelId,
   @JsonProperty("parent_label_name")
   @Column("parent_label_name")
   String parentLabelName
) {

}
