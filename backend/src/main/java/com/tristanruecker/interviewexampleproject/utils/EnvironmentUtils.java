package com.tristanruecker.interviewexampleproject.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtils {


    @Value("${server.servlet.context-path}")
    private String securityBaseUrl;

    public String getSecurityBaseUrl() {
        return this.securityBaseUrl;
    }
}
