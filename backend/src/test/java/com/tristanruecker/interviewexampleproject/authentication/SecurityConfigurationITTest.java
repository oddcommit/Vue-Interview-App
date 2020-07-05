package com.tristanruecker.interviewexampleproject.authentication;

import com.tristanruecker.interviewexampleproject.base.IntegrationTestBaseClass;
import com.tristanruecker.interviewexampleproject.base.api.UserControllerApi;
import com.tristanruecker.interviewexampleproject.models.objects.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import retrofit2.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityConfigurationITTest extends IntegrationTestBaseClass {

    @Test
    void getUsersWithoutJWTFail() throws IOException {
        UserControllerApi userControllerApi = createRetrofitClient(UserControllerApi.class);
        Response<List<User>> usersResponse = userControllerApi.getAllUsersWithourAuthorization().execute();

        assertEquals(403, usersResponse.code());
    }

    @Test
    void testExpiredJWTTokenFail() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException {
        String token = create2MicrosecondsValidJWTToken();
        Thread.sleep(4);
        UserControllerApi userControllerApi = createRetrofitClient(UserControllerApi.class, token);

        Response<List<User>> usersResponse = userControllerApi.getAllUsersWithourAuthorization().execute();
        assertEquals(401, usersResponse.code());
    }

    private String create2MicrosecondsValidJWTToken() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS512;
        Key privateKey = readPrivateKey();
        String userEmail = "test.registration@gmail.com";

        Instant instant = Instant.now();
        Instant expirationInstant = instant.plus(1, ChronoUnit.MICROS);

        JwtBuilder builder = Jwts.builder()
                .setId(userEmail)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(expirationInstant))
                .setSubject(userEmail)
                .setIssuer("some name")
                .signWith(signatureAlgorithm, privateKey);
        return builder.compact();
    }


    private Key readPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Resource resource = new ClassPathResource("security/jwt/private_key.der");
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getFile().toURI()));
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpecPKCS8);
    }

}
