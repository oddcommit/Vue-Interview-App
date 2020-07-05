package com.tristanruecker.interviewexampleproject.utils;

import com.tristanruecker.interviewexampleproject.config.authentication.JWTParseResultObject;
import com.tristanruecker.interviewexampleproject.config.authentication.type.JWTParseResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Component
public class JwtUtils {

    @Autowired
    CertificateUtils certificateUtils;

    public JWTParseResultObject parseAuthentication(String authorizationHeader) {
        Optional<PublicKey> publicKeyOptional = certificateUtils.readPublicKey();

        if (publicKeyOptional.isPresent()) {
            try {
                Jws<Claims> jwsClaims = Jwts.parser()
                        .setSigningKey(publicKeyOptional.get())
                        .parseClaimsJws(authorizationHeader.trim().substring(7));
                Claims claims = jwsClaims.getBody();
                return buildJWTParseResultObject(claims, JWTParseResult.SUCCESS);
            } catch (ExpiredJwtException e) {
                Claims claims = e.getClaims();
                return buildJWTParseResultObject(claims, JWTParseResult.EXPIRED);
            } catch (JwtException e) {
                return buildJWTParseResultObject(null, JWTParseResult.FAILED);
            }
        }

        return buildJWTParseResultObject(null, JWTParseResult.FAILED);
    }


    private JWTParseResultObject buildJWTParseResultObject(Claims claims, JWTParseResult jwtParseResult) {
        JWTParseResultObject jwtParseResultObject = new JWTParseResultObject();
        jwtParseResultObject.setJwtParseResult(jwtParseResult);
        if (jwtParseResult == JWTParseResult.FAILED) {
            return jwtParseResultObject;
        }

        List<String> userRoles = claims.get("roles", List.class);
        String email = claims.get("sub", String.class);

        if (CollectionUtils.isNotEmpty(userRoles)) {
            List<GrantedAuthority> grantedAuths = AuthorityUtils
                    .createAuthorityList(String.join(",", userRoles));
            jwtParseResultObject.setUsernamePasswordAuthenticationToken(
                    new UsernamePasswordAuthenticationToken(email, null, grantedAuths));
        }

        return jwtParseResultObject;
    }
}
