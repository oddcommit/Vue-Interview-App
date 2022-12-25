package com.tristanruecker.interviewexampleproject.models.objects.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "captcha")
public class Captcha {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="pg-uuid")
    private UUID uuid;

    @Column(name = "captcha_text")
    @JsonIgnore
    private String captchaText;

    @CreationTimestamp
    @Column(name = "creation_date")
    @JsonIgnore
    private Instant creationDate;

    @Transient
    @JsonProperty("captcha_image")
    private String captchaImageBase64Encoded;

}
