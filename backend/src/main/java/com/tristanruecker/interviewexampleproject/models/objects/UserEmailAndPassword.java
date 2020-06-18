package com.tristanruecker.interviewexampleproject.models.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public class UserEmailAndPassword extends AbstractDatabaseObject {

    @NonNull
    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @NonNull
    @Column(name = "password")
    @JsonProperty("password")
    private String password;
}
