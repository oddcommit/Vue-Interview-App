package com.tristanruecker.interviewexampleproject.controller;


import com.tristanruecker.interviewexampleproject.base.IntegrationTestBaseClass;
import com.tristanruecker.interviewexampleproject.base.api.LoginControllerApi;
import com.tristanruecker.interviewexampleproject.models.objects.entities.UserEmailAndPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginControllerITTest extends IntegrationTestBaseClass {

    LoginControllerApi loginControllerApi;

    @BeforeEach
    public void createLoginControllerApi() {
        this.loginControllerApi = createRetrofitClient(LoginControllerApi.class);
    }

    @Test
    void obtainJWTToken() throws IOException {
        UserEmailAndPassword userEmailAndPassword = new UserEmailAndPassword();
        userEmailAndPassword.setEmail("test.registration@gmail.com");
        userEmailAndPassword.setPassword("test1234");

        String jwtToken = loginControllerApi
                .userLogin(userEmailAndPassword)
                .execute()
                .body()
                .getJwtToken();

        assertTrue(jwtToken.length() > 0);
    }


}
