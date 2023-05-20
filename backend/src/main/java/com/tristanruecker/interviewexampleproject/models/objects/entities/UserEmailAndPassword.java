package com.tristanruecker.interviewexampleproject.models.objects.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
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
