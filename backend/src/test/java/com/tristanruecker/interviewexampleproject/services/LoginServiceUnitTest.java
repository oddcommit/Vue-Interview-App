package com.tristanruecker.interviewexampleproject.services;

import com.tristanruecker.interviewexampleproject.base.UnitTestBaseClass;
import com.tristanruecker.interviewexampleproject.config.exception.CustomException;
import com.tristanruecker.interviewexampleproject.config.exception.ErrorField;
import com.tristanruecker.interviewexampleproject.models.objects.dto.UserRegistrationDTO;
import com.tristanruecker.interviewexampleproject.models.objects.entities.Captcha;
import com.tristanruecker.interviewexampleproject.models.objects.entities.types.Gender;
import com.tristanruecker.interviewexampleproject.models.repositores.CaptchaRepository;
import com.tristanruecker.interviewexampleproject.models.repositores.UserRepository;
import com.tristanruecker.interviewexampleproject.utils.CertificateUtils;
import com.tristanruecker.interviewexampleproject.utils.JwtUtils;
import com.tristanruecker.interviewexampleproject.utils.TextConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoginServiceUnitTest extends UnitTestBaseClass {
    private LoginService loginService;
    private final String CAPTCHA_UUID = "3b039a98-7c68-46a8-8dac-84d4c221042c";

    @BeforeEach
    void mockBeforeTest(@Mock UserRepository userRepository,
                        @Mock CaptchaRepository captchaRepository,
                        @Mock BCryptPasswordEncoder bCryptPasswordEncoder,
                        @Mock CertificateUtils certificateUtils,
                        @Mock JwtUtils jwtUtils) {
        Captcha captcha = new Captcha();
        captcha.setUuid(UUID.fromString(CAPTCHA_UUID));
        captcha.setCaptchaText("something");
        when(captchaRepository.findById(any())).thenReturn(Optional.of(captcha));

        loginService = new LoginService(certificateUtils, bCryptPasswordEncoder, userRepository, jwtUtils, captchaRepository);
    }


    @Test
    void registerUserWrongEmailUnitTest() {
        //before
        UserRegistrationDTO user = getBaseUserRegistrationDto();
        user.setEmail("wrongemail.de");

        //when
        CustomException thrown = assertThrows(CustomException.class,
                () -> loginService.registerUser(user)
        );

        //then
        ErrorField errorField = thrown.getErrorFieldList().get(0);
        //then
        assertEquals("email", errorField.getField());
        assertEquals(errorField.getErrorMessage(), TextConstants.EMAIL_IS_INVALID);
    }

    @Test
    void registerUserTooHighAge() {
        //before
        UserRegistrationDTO user = getBaseUserRegistrationDto();
        user.setAge("131");

        //when
        CustomException thrown = assertThrows(CustomException.class,
                () -> loginService.registerUser(user)
        );

        ErrorField errorField = thrown.getErrorFieldList().get(0);
        //then
        assertEquals("age", errorField.getField());
        assertEquals(TextConstants.AGE_NOT_APPROPRIATE, errorField.getErrorMessage());
    }

    @Test
    void registerUserTooLowAge() {
        //before
        UserRegistrationDTO user = getBaseUserRegistrationDto();
        user.setAge("-1");

        //when
        CustomException thrown = assertThrows(CustomException.class,
                () -> loginService.registerUser(user)
        );

        //then
        ErrorField errorField = thrown.getErrorFieldList().get(0);

        assertEquals("age", errorField.getField());
        assertEquals(TextConstants.AGE_NOT_APPROPRIATE, errorField.getErrorMessage());
    }


    private UserRegistrationDTO getBaseUserRegistrationDto() {
        UserRegistrationDTO user = new UserRegistrationDTO();
        user.setEmail("hello@test.de");
        user.setAge("30");
        user.setGender(Gender.FEMALE.toString());
        user.setCaptchaUuid(CAPTCHA_UUID);
        user.setCaptchaText("something");
        return user;
    }

}
