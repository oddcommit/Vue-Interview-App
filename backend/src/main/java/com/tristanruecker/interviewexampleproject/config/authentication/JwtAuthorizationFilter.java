package com.tristanruecker.interviewexampleproject.config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tristanruecker.interviewexampleproject.utils.JwtUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * If JWT token is expired then it returns status code 401
 * If token is invalid status code 403 will be returned
 * If Authorization header is not visible status code 403 is returned by default
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                           ObjectMapper objectMapper,
                           JwtUtils jwtUtils) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtUtils = jwtUtils;
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

        if (request.getRequestURI().equals("/api/renewToken")) {
            filterChain.doFilter(request, response);
            return;
        }

        JWTParseResultObject jwtParseResultObject = jwtUtils.parseAuthentication(tokenHeader);

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
}
