package com.tristanruecker.interviewexampleproject.models.objects.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private String age;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("captcha_uuid")
    private String captchaUuid;

    @JsonProperty("captcha_text")
    private String captchaText;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}
