package com.example.experttrader.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TokenStorageService {
    private final IgAuthService igAuthService;
    private final StringRedisTemplate redisTemplate;
    private static final String CST_KEY = "IG_CST";
    private static final String SECURITY_TOKEN_KEY = "IG_SECURITY_TOKEN";

    public TokenStorageService(IgAuthService igAuthService, StringRedisTemplate redisTemplate) {
        this.igAuthService = igAuthService;
        this.redisTemplate = redisTemplate;
    }

    public void storeTokens(String cst, String securityToken){
        redisTemplate.opsForValue().set(CST_KEY, cst, Duration.ofHours(1));
        redisTemplate.opsForValue().set(SECURITY_TOKEN_KEY, securityToken, Duration.ofHours(1));
    }

    public String getCstKey(){
        return redisTemplate.opsForValue().get(CST_KEY);
    }

    public String getSecurityTokenKey(){
        return redisTemplate.opsForValue().get(SECURITY_TOKEN_KEY);
    }

    public boolean areTokensExpired(){
        return redisTemplate.opsForValue().get(CST_KEY) == null || redisTemplate.opsForValue().get(SECURITY_TOKEN_KEY) == null;
    }

    public Mono<Void> refreshTokens(){
        return igAuthService.authenticate()
                .then();
    }
}
