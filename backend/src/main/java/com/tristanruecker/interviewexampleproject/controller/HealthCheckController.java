package com.tristanruecker.interviewexampleproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/ping")
    public String getAllUsers() {
        return "OK";
    }


}
