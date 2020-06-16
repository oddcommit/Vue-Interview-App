package com.tristanruecker.interviewexampleproject.config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tristanruecker.interviewexampleproject.config.authentication.type.JWTParseResult;
import com.tristanruecker.interviewexampleproject.utils.CertificateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

/**
 * If JWT token is expired then it returns status code 401
 * If token is invalid status code 403 will be returned
 * If Authorization header is not visible status code 403 is returned by default
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final CertificateUtils certificateUtils;
    private final ObjectMapper objectMapper;

    JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                           CertificateUtils certificateUtils,
                           ObjectMapper objectMapper) {
        super(authenticationManager);
        this.certificateUtils = certificateUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String tokenHeader = request.getHeader("Authorization");
        boolean isAuthorizationHeaderAndTokenVisible = isAuthorizationHeaderAndTokenVisible(tokenHeader);
        if (!isAuthorizationHeaderAndTokenVisible) {
            filterChain.doFilter(request, response);
            return;
        }

        JWTParseResultObject jwtParseResultObject = getAuthentication(tokenHeader);

        switch (jwtParseResultObject.getJwtParseResult()) {
            case SUCCESS:
                SecurityContextHolder.getContext().setAuthentication(jwtParseResultObject.getUsernamePasswordAuthenticationToken());
                break;
            case EXPIRED:
                UnauthorizedException unauthorizedException = new UnauthorizedException(HttpStatus.UNAUTHORIZED, "Access denied.");
                String unauthorizedResponse = objectMapper.writeValueAsString(unauthorizedException);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(unauthorizedResponse);
                response.getWriter().flush();
                response.getWriter().close();
                return;
            case FAILED:
                filterChain.doFilter(request, response);
                return;
        }

        filterChain.doFilter(request, response);
    }


    private boolean isAuthorizationHeaderAndTokenVisible(String authorizationHeader) {
        return Strings.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ");
    }


    private JWTParseResultObject getAuthentication(String authorizationHeader) {
        JWTParseResultObject jwtParseResultObject = new JWTParseResultObject();
        Optional<PublicKey> publicKeyOptional = certificateUtils.readPublicKey();

        if (publicKeyOptional.isPresent()) {
            try {
                Jws<Claims> jwsClaims = Jwts.parser()
                        .setSigningKey(publicKeyOptional.get())
                        .parseClaimsJws(authorizationHeader.trim().substring(7));
                Claims claims = jwsClaims.getBody();
                String email = claims.get("sub", String.class);
                List<String> userRoles = claims.get("roles", List.class);
                List<GrantedAuthority> grantedAuths = AuthorityUtils.createAuthorityList(String.join(",", userRoles));

                jwtParseResultObject.setJwtParseResult(JWTParseResult.SUCCESS);
                jwtParseResultObject.setUsernamePasswordAuthenticationToken(new UsernamePasswordAuthenticationToken(email, null, grantedAuths));
                return jwtParseResultObject;
            } catch (ExpiredJwtException e) {
                jwtParseResultObject.setJwtParseResult(JWTParseResult.EXPIRED);
                return jwtParseResultObject;
            } catch (JwtException e) {
                jwtParseResultObject.setJwtParseResult(JWTParseResult.FAILED);
                return jwtParseResultObject;
            }
        }

        jwtParseResultObject.setJwtParseResult(JWTParseResult.FAILED);
        return jwtParseResultObject;
    }
}
