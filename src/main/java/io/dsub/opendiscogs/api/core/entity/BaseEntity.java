package io.dsub.opendiscogs.api.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

public abstract class BaseEntity<T> implements Persistable<T> {

  @Column("created_at")
  @JsonIgnore
  protected LocalDateTime createdAt = LocalDateTime.now(Clock.systemUTC());

  @Column("last_modified_at")
  @JsonIgnore
  protected LocalDateTime lastModifiedAt = LocalDateTime.now(Clock.systemUTC());

  @Transient
  @JsonIgnore
  protected boolean isNew = false;

  @Override
  @Transient
  @JsonIgnore
  public boolean isNew() {
    return this.isNew;
  }

  public void setAsNew() {
    this.isNew = true;
  }
}
