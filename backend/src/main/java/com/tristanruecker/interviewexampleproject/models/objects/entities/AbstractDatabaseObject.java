package com.tristanruecker.interviewexampleproject.models.objects.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@Data
public abstract class AbstractDatabaseObject {

    @CreationTimestamp
    @Column(name = "creation_date")
    @Schema(hidden = true)
    @JsonIgnore
    private Instant creationDate;

    @UpdateTimestamp
    @Column(name = "modification_date")
    @Schema(hidden = true)
    @JsonIgnore
    private Instant modificationDate;

}
