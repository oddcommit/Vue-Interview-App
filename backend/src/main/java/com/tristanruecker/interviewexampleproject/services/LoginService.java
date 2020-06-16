package com.tristanruecker.interviewexampleproject.services;

import com.tristanruecker.interviewexampleproject.config.exception.CustomException;
import com.tristanruecker.interviewexampleproject.models.objects.User;
import com.tristanruecker.interviewexampleproject.models.objects.UserEmailAndPassword;
import com.tristanruecker.interviewexampleproject.models.objects.UserRole;
import com.tristanruecker.interviewexampleproject.models.objects.types.Roles;
import com.tristanruecker.interviewexampleproject.models.repositores.UserRepository;
import com.tristanruecker.interviewexampleproject.models.response.UserLoggedInResponse;
import com.tristanruecker.interviewexampleproject.utils.CertificateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CertificateUtils certificateUtils;

    private final UserRepository userRepository;

    @Autowired
    public LoginService(CertificateUtils certificateUtils,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.certificateUtils = certificateUtils;
    }

    public void registerUser(User user) {
        boolean isUserExisting = userRepository.existsByEmail(user.getEmail());
        if (!isUserExisting) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            UserRole userRole = new UserRole();
            userRole.setRoleName(Roles.USER);
            userRole.setUser(user);
            user.setUserRoles(Collections.singleton(userRole));

            userRepository.save(user);
            //TODO: Send E-Mail verification link
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

        boolean isPasswordCorrect = bCryptPasswordEncoder
                .matches(userEmailAndPassword.getPassword(), userFromDatabase.getPassword());

        if (!isPasswordCorrect) {
            throwWrongEmailOrPasswordException();
        }

        Optional<String> jwtToken = certificateUtils.createJWTToken(userFromDatabase);
        if (jwtToken.isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,
                    "Can not login. Please contact us at tristan.ruecker@gmail.com");
        }

        UserLoggedInResponse userLoggedInResponse = new UserLoggedInResponse();
        userLoggedInResponse.setJwtToken(jwtToken.get());
        return userLoggedInResponse;
    }


    private void throwWrongEmailOrPasswordException() {
        throw new CustomException(HttpStatus.UNAUTHORIZED, "Sorry, wrong email or wrong password");
    }

}
