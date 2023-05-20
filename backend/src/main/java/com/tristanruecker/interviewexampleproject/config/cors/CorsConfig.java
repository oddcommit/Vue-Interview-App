package com.tristanruecker.interviewexampleproject.config.cors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Slf4j
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.debug("Setup Spring Cors configuration");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000",
                                "http://interview.test");
    }
}
