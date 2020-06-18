package com.tristanruecker.interviewexampleproject.models.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@Data
public abstract class AbstractDatabaseObject {

    @CreationTimestamp
    @Column(name = "creation_date")
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Instant creationDate;

    @UpdateTimestamp
    @Column(name = "modification_date")
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Instant modificationDate;

}
