package io.dsub.opendiscogs.api.release.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dsub.opendiscogs.api.core.entity.BaseEntity;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@With
@Builder
@Table(name = "release_item")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Release extends BaseEntity<Long> {

  @Id
  @Min(1)
  @Column("id")
  @JsonProperty("id")
  private final Long id;

  @Column("title")
  @JsonProperty("title")
  private final String title;

  @Column("country")
  @JsonProperty("country")
  private final String country;

  @Column("data_quality")
  @JsonProperty("data_quality")
  private final String dataQuality;

  @Column("release_date")
  @JsonProperty("release_date")
  private final LocalDate releaseDate;

  @Column("has_valid_year")
  private final Boolean hasValidYear;

  @Column("has_valid_month")
  private final Boolean hasValidMonth;

  @Column("has_valid_day")
  private final Boolean hasValidDay;

  @Column("listed_release_date")
  @JsonProperty("listed_release_date")
  private final String listedReleaseDate;

  @Column("is_master")
  @JsonProperty("is_master")
  private final Boolean isMaster;

  @Column("master_id")
  @JsonProperty("master_id")
  private final Long masterId;

  @Column("notes")
  @JsonProperty("notes")
  private final String notes;

  @Column("status")
  @JsonProperty("status")
  private final String status;

  @JsonProperty("released_year")
  public Integer getReleasedYear() {
    return Boolean.TRUE.equals(hasValidYear) && releaseDate != null ? releaseDate.getYear() : null;
  }

  @JsonProperty("released_month")
  public Integer getReleasedMonth() {
    return Boolean.TRUE.equals(hasValidMonth) && releaseDate != null
        ? releaseDate.getMonthValue()
        : null;
  }

  @JsonProperty("released_day")
  public Integer getReleasedDay() {
    return Boolean.TRUE.equals(hasValidDay) && releaseDate != null
        ? releaseDate.getDayOfMonth()
        : null;
  }
}
