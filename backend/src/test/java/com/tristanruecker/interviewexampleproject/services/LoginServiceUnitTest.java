package com.tristanruecker.interviewexampleproject.services;

import com.tristanruecker.interviewexampleproject.base.UnitTestBaseClass;
import com.tristanruecker.interviewexampleproject.config.exception.CustomException;
import com.tristanruecker.interviewexampleproject.models.objects.User;
import com.tristanruecker.interviewexampleproject.models.repositores.UserRepository;
import com.tristanruecker.interviewexampleproject.utils.CertificateUtils;
import com.tristanruecker.interviewexampleproject.utils.JwtUtils;
import com.tristanruecker.interviewexampleproject.utils.TextConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginServiceUnitTest extends UnitTestBaseClass {

    private LoginService loginService;

    @BeforeEach
    void mockBeforeTest(@Mock UserRepository userRepository,
                       @Mock BCryptPasswordEncoder bCryptPasswordEncoder,
                       @Mock CertificateUtils certificateUtils,
                       @Mock JwtUtils jwtUtils) {
        loginService = new LoginService(certificateUtils, bCryptPasswordEncoder, userRepository, jwtUtils);
    }


    @Test
    void registerUserWrongEmailUnitTest() {
        //before
        User user = new User();
        user.setEmail("wrongemail.de");

        //when
        Exception thrown = assertThrows(CustomException.class,
                () -> loginService.registerUser(user)
        );

        //then
        assertTrue(thrown.getMessage().contains(TextConstants.INVALID_EMAIL));
    }

    @Test
    void registerUserTooHighAge() {
        //before
        User user = new User();
        user.setEmail("hello@test.de");
        user.setAge(131);

        //when
        Exception thrown = assertThrows(CustomException.class,
                () -> loginService.registerUser(user)
        );

        //then
        assertTrue(thrown.getMessage().contains(TextConstants.AGE_NOT_APPROPRIATE));
    }

    @Test
    void registerUserTooLowAge() {
        //before
        User user = new User();
        user.setEmail("hello@test.de");
        user.setAge(-1);

        //when
        Exception thrown = assertThrows(CustomException.class,
                () -> loginService.registerUser(user)
        );

        //then
        assertTrue(thrown.getMessage().contains(TextConstants.AGE_NOT_APPROPRIATE));
    }

}
