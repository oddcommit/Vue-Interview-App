package com.tristanruecker.interviewexampleproject.config.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tristanruecker.interviewexampleproject.models.objects.entities.types.Roles;
import com.tristanruecker.interviewexampleproject.utils.CertificateUtils;
import com.tristanruecker.interviewexampleproject.utils.EnvironmentUtils;
import com.tristanruecker.interviewexampleproject.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration  {

    @Value("${security.disabled.exclude.pattern}")
    private String[] securityExclusionPattern;

    @Autowired
    private CertificateUtils certificateUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EnvironmentUtils environmentUtils;

    @Autowired AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] roles = {Roles.USER.toString(), Roles.SUPERADMIN.toString()};

        http.cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(e -> e.requestMatchers(securityExclusionPattern).permitAll())
                .authorizeHttpRequests(e -> e.requestMatchers("/**").hasAnyAuthority(roles).anyRequest().authenticated())
                .addFilter(new JwtAuthorizationFilter(authenticationConfiguration.getAuthenticationManager(),
                        objectMapper,
                        jwtUtils, environmentUtils));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
