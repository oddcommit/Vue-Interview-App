package com.tristanruecker.interviewexampleproject.models.response;

import lombok.Data;

@Data
public class UserLoggedInResponse {

    private String jwtToken;

}
