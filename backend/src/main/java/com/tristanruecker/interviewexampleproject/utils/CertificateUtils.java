package com.tristanruecker.interviewexampleproject.utils;

import com.tristanruecker.interviewexampleproject.models.objects.User;
import com.tristanruecker.interviewexampleproject.models.objects.UserRole;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file
 * We can generate a new certificate from our private key which is currently not made
 */

@Component
public class CertificateUtils {


    @Value("${security.token.lifetime.in.days}")
    private int tokenLifetimeInDays;


    public Optional<String> createJWTToken(User user) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS512;
            Key privateKey = readPrivateKey();
            String userEmail = user.getEmail();

            Instant instant = Instant.now();
            Instant expirationInstant = instant.plus(tokenLifetimeInDays, ChronoUnit.SECONDS);


            JwtBuilder builder = Jwts.builder()
                    .setId(userEmail + "-" + UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(instant))
                    .setExpiration(Date.from(expirationInstant))
                    .setSubject(userEmail)
                    .setIssuer("Company name GmbH")
                    .claim("roles", getRoleNames(user.getUserRoles()))
                    .signWith(signatureAlgorithm, privateKey);

            return Optional.of(builder.compact());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    private Object[] getRoleNames(Set<UserRole> userRoles) {
        List<String> userRolesList = new ArrayList<>();
        userRoles.forEach(userRole -> userRolesList.add(userRole.getRoleName().toString()));
        return userRolesList.toArray();
    }


    public Optional<PublicKey> readPublicKey() {
        try {
            Resource resource = new ClassPathResource("security/jwt/public_key.der");
            byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getFile().toURI()));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return Optional.ofNullable(kf.generatePublic(spec));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Key readPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Resource resource = new ClassPathResource("security/jwt/private_key.der");
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getFile().toURI()));
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpecPKCS8);
    }

    /**
     * Maybe remove in future
     *
     * @return public key
     * @throws CertificateException if certificate is not valid or outdated
     * @throws IOException          when input can not be read
     */
    private PublicKey getPublicKeyOutOfCertificate() throws CertificateException, IOException {
        Resource resource = new ClassPathResource("security/jwt/public_certificate.cer");
        String certificateString = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);

        CertificateFactory f = CertificateFactory.getInstance("X.509");
        InputStream stream = new ByteArrayInputStream(certificateString.getBytes(StandardCharsets.UTF_8));
        X509Certificate certificate = (X509Certificate) f.generateCertificate(stream);
        return certificate.getPublicKey();
    }

}