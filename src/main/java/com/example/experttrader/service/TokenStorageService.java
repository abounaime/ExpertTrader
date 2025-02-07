package com.example.experttrader.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenStorageService {
    private final StringRedisTemplate redisTemplate;
    private static final String CST_KEY = "IG_CST";
    private static final String SECURITY_TOKEN_KEY = "IG_SECURITY_TOKEN";

    public TokenStorageService(StringRedisTemplate redisTemplate) {
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
}
