package com.tristanruecker.interviewexampleproject.services;

import com.tristanruecker.interviewexampleproject.config.authentication.JWTParseResultObject;
import com.tristanruecker.interviewexampleproject.config.authentication.type.JWTParseResult;
import com.tristanruecker.interviewexampleproject.config.exception.CustomException;
import com.tristanruecker.interviewexampleproject.config.exception.ErrorField;
import com.tristanruecker.interviewexampleproject.config.exception.ErrorFieldList;
import com.tristanruecker.interviewexampleproject.models.objects.dto.UserRegistrationDTO;
import com.tristanruecker.interviewexampleproject.models.objects.entities.Captcha;
import com.tristanruecker.interviewexampleproject.models.objects.entities.User;
import com.tristanruecker.interviewexampleproject.models.objects.entities.UserEmailAndPassword;
import com.tristanruecker.interviewexampleproject.models.objects.entities.UserRole;
import com.tristanruecker.interviewexampleproject.models.objects.entities.types.Gender;
import com.tristanruecker.interviewexampleproject.models.objects.entities.types.Roles;
import com.tristanruecker.interviewexampleproject.models.repositores.CaptchaRepository;
import com.tristanruecker.interviewexampleproject.models.repositores.UserRepository;
import com.tristanruecker.interviewexampleproject.models.response.UserLoggedInResponse;
import com.tristanruecker.interviewexampleproject.utils.CertificateUtils;
import com.tristanruecker.interviewexampleproject.utils.JwtUtils;
import com.tristanruecker.interviewexampleproject.utils.TextConstants;
import net.logicsquad.nanocaptcha.image.ImageCaptcha;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CertificateUtils certificateUtils;
    private final UserRepository userRepository;
    private final CaptchaRepository captchaRepository;
    private final EmailValidator emailValidator;
    private final JwtUtils jwtUtils;

    @Autowired
    public LoginService(CertificateUtils certificateUtils, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, JwtUtils jwtUtils, CaptchaRepository captchaRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.certificateUtils = certificateUtils;
        this.emailValidator = EmailValidator.getInstance();
        this.jwtUtils = jwtUtils;
        this.captchaRepository = captchaRepository;
    }

    public Captcha getCaptcha() {
        try {
            ImageCaptcha imageCaptcha = new ImageCaptcha.Builder(150, 50).addContent().addBorder().build();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(imageCaptcha.getImage(), "png", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            Captcha captcha = new Captcha();
            captcha.setUuid(UUID.randomUUID());
            captcha.setCaptchaText(imageCaptcha.getContent());
            captcha.setCaptchaImageBase64Encoded(Base64.toBase64String(bytes));
            captchaRepository.save(captcha);
            return captcha;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    public void registerUser(UserRegistrationDTO userRegistrationDto) {
        ErrorFieldList errorFieldList = new ErrorFieldList();

        String email = userRegistrationDto.getEmail();

        if (!StringUtils.hasLength(email)) {
            errorFieldList.add(new ErrorField("email", TextConstants.EMAIL_IS_MANDATORY));
        } else if (!emailValidator.isValid(email)) {
            errorFieldList.add(new ErrorField("email", TextConstants.EMAIL_IS_INVALID));
        } else if (userRepository.existsByEmail(email)) {
            errorFieldList.add(new ErrorField("email", TextConstants.EMAIL_ALREADY_IN_USE));
        }

        String name = userRegistrationDto.getName();

        if (!StringUtils.hasLength(name)) {
            errorFieldList.add(new ErrorField("name", TextConstants.NAME_IS_MANDATORY));
        }

        String ageString = userRegistrationDto.getAge();
        int age = 0;
        if (!StringUtils.hasLength(ageString)) {
            errorFieldList.add(new ErrorField("age", TextConstants.AGE_IS_MANDATORY));
        } else if (!NumberUtils.isCreatable(ageString)) {
            errorFieldList.add(new ErrorField("age", TextConstants.AGE_NOT_APPROPRIATE));
        } else {
            age = Integer.parseInt(ageString);
            if (age < 0 || age > 130) {
                errorFieldList.add(new ErrorField("age", TextConstants.AGE_NOT_APPROPRIATE));
            }
        }

        String password = userRegistrationDto.getPassword();

        if (!StringUtils.hasLength(password)) {
            errorFieldList.add(new ErrorField("password", TextConstants.PASSWORD_IS_MANDATORY));
        }

        String genderString = userRegistrationDto.getGender();
        Optional<Gender> gender = Optional.empty();
        if (!StringUtils.hasLength(genderString)) {
            errorFieldList.add(new ErrorField("gender", TextConstants.GENDER_IS_MANDATORY));
        } else {
            gender = Gender.getGender(genderString);

            if (gender.isEmpty()) {
                errorFieldList.add(new ErrorField("gender", TextConstants.GENDER_CAN_NOT_BE_FOUND));
            }
        }

        String captchaUuid = userRegistrationDto.getCaptchaUuid();

        if (!StringUtils.hasLength(captchaUuid)) {
            throw new CustomException(HttpStatus.TOO_MANY_REQUESTS, TextConstants.CAPTCHA_NOT_SEND_CORRECTLY, errorFieldList);
        }

        try {
            Optional<Captcha> captcha = captchaRepository.findById(UUID.fromString(userRegistrationDto.getCaptchaUuid()));
            if (captcha.isEmpty()) {
                throw new CustomException(HttpStatus.TOO_MANY_REQUESTS, TextConstants.CAPTCHA_NOT_VALID_ANYMORE, errorFieldList);
            }

            if (!userRegistrationDto.getCaptchaText().equals(captcha.get().getCaptchaText())) {
                throw new CustomException(HttpStatus.TOO_MANY_REQUESTS, TextConstants.CAPTCHA_TEXT_INOUT_DOES_NOT_MATCH_CAPTCHA, errorFieldList);
            }

            if (CollectionUtils.isNotEmpty(errorFieldList)) {
                throw new CustomException(HttpStatus.BAD_REQUEST, errorFieldList);
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setAge(age);
            user.setGender(gender.orElse(Gender.OTHER));
            user.setName(name);

            UserRole userRole = new UserRole();
            userRole.setRoleName(Roles.USER);
            userRole.setUser(user);
            user.setUserRoles(Collections.singleton(userRole));
            userRepository.save(user);
            //TODO: Send E-Mail verification link
        } catch (IllegalArgumentException exception) {
            throw new CustomException(HttpStatus.TOO_MANY_REQUESTS, TextConstants.CAPTCHA_CAN_NOT_DESERIALIZE_UUID, errorFieldList);
        }
    }

    /**
     * https://stormpath.com/blog/jwt-java-create-verify
     *
     * @param userEmailAndPassword: email and password from user that wants to be logged in
     */
    public UserLoggedInResponse userLogin(UserEmailAndPassword userEmailAndPassword) {
        Optional<User> userFromDatabaseOptional = userRepository.findByEmail(userEmailAndPassword.getEmail());

        if (userFromDatabaseOptional.isEmpty()) {
            throwWrongEmailOrPasswordException();
        }

        User userFromDatabase = userFromDatabaseOptional.get();

        boolean isPasswordCorrect = bCryptPasswordEncoder.matches(userEmailAndPassword.getPassword(), userFromDatabase.getPassword());

        if (!isPasswordCorrect) {
            throwWrongEmailOrPasswordException();
        }

        Optional<String> jwtToken = certificateUtils.createJWTToken(userFromDatabase);

        if (jwtToken.isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, TextConstants.CANT_OBTAIN_JWT_TOKEN, null);
        }

        UserLoggedInResponse userLoggedInResponse = new UserLoggedInResponse();
        userLoggedInResponse.setJwtToken(jwtToken.get());
        return userLoggedInResponse;
    }


    public UserLoggedInResponse regenerateTokenOnExpire(String authorizationHeader, Principal principal) {
        JWTParseResultObject jwtParseResultObject = jwtUtils.parseAuthentication(authorizationHeader);

        if (jwtParseResultObject.getJwtParseResult() != JWTParseResult.FAILED) {
            String userEmail = jwtParseResultObject.getUsernamePasswordAuthenticationToken().getPrincipal().toString();
            if (StringUtils.isEmpty(userEmail)) {
                throw new CustomException(HttpStatus.UNAUTHORIZED, TextConstants.RENEW_TOKEN_USER_NOT_FOUND);
            }
            Optional<User> user = userRepository.findByEmail(userEmail);
            if (user.isEmpty()) {
                throw new CustomException(HttpStatus.UNAUTHORIZED, TextConstants.RENEW_TOKEN_USER_NOT_FOUND);
            }

            UserLoggedInResponse userLoggedInResponse = new UserLoggedInResponse();
            userLoggedInResponse.setJwtToken(certificateUtils.createJWTToken(user.get()).get());
            return userLoggedInResponse;
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED, TextConstants.CANT_REGENERATE_TOKEN);
        }
    }


    private void throwWrongEmailOrPasswordException() {
        throw new CustomException(HttpStatus.UNAUTHORIZED, TextConstants.WRONG_EMAIL_OR_PASSWORD, null);
    }

}
