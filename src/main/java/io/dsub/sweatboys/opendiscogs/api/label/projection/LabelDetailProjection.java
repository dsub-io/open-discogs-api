package io.dsub.sweatboys.opendiscogs.api.label.projection;

import org.springframework.data.relational.core.mapping.Column;

public record LabelDetailProjection(
   Long id,
   String contactInfo,
   String dataQuality,
   String name,
   String profile,
   Long parentLabelId,
   String parentLabelName
) {}
