package com.tristanruecker.interviewexampleproject.config.authentication;

import com.tristanruecker.interviewexampleproject.config.authentication.type.JWTParseResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Setter
@Getter
public class JWTParseResultObject {

    private JWTParseResult jwtParseResult;
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

}
