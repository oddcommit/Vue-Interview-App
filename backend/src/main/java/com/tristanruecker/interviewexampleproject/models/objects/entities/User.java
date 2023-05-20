package com.tristanruecker.interviewexampleproject.models.objects.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tristanruecker.interviewexampleproject.models.objects.entities.types.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "user_data")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends UserEmailAndPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_id_seq")
    @SequenceGenerator(name = "user_data_id_seq", sequenceName = "user_data_id_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonProperty("id")
    @Schema(hidden = true)
    private Long id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "age")
    @JsonProperty("age")
    private int age;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @JsonProperty("gender")
    private Gender gender;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Set<UserRole> userRoles;

}
