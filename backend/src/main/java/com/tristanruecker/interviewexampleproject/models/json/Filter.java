package com.tristanruecker.interviewexampleproject.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tristanruecker.interviewexampleproject.models.objects.entities.types.Gender;
import lombok.Data;

@Data
public class Filter {

    @JsonProperty("minAge")
    private int minAge;

    @JsonProperty("maxAge")
    private int maxAge;

    @JsonProperty("gender")
    private Gender gender;

}
