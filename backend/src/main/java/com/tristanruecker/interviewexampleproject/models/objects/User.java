package com.tristanruecker.interviewexampleproject.models.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tristanruecker.interviewexampleproject.models.objects.types.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
    @ApiModelProperty(hidden = true)
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
