package io.dsub.sweatboys.opendiscogs.api.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

public abstract class BaseEntity<T> implements Persistable<T> {
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