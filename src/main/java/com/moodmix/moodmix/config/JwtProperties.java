package com.moodmix.moodmix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "moodmix.jwt")
public record JwtProperties(
        String secret,
        String issuer,
        long accessTtlSeconds
) {}