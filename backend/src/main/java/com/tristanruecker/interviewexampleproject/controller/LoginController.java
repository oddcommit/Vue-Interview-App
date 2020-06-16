package com.tristanruecker.interviewexampleproject.controller;

import com.tristanruecker.interviewexampleproject.models.objects.UserEmailAndPassword;
import com.tristanruecker.interviewexampleproject.models.objects.User;
import com.tristanruecker.interviewexampleproject.models.response.UserLoggedInResponse;
import com.tristanruecker.interviewexampleproject.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMappingApi
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login")
    public UserLoggedInResponse login(@RequestBody UserEmailAndPassword user) {
        return loginService.userLogin(user);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User user) {
        loginService.registerUser(user);
    }

    /**
     * https://stackoverflow.com/questions/35791465/is-there-a-way-to-parse-claims-from-an-expired-jwt-token
     * TODO: Get new authentication token BUT NOT HERE old ist "just" expired
     */
    public void regenerateTokenOnExpire() {

    }

}