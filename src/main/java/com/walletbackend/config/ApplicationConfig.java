package com.walletbackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Application config class that contains environment variables
 */
@Getter
@Configuration
public class ApplicationConfig {
    @Value("${FRONTEND_URL}")
    private String frontendUrl;
}