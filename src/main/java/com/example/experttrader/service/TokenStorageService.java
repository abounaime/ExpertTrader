package com.example.experttrader.service;

import com.example.experttrader.config.IgApiProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TokenStorageService {
    private final IgAuthService igAuthService;
    private final StringRedisTemplate redisTemplate;
    private final IgApiProperties igApiProperties;
    public TokenStorageService(IgAuthService igAuthService, StringRedisTemplate redisTemplate, IgApiProperties igApiProperties) {

        this.igAuthService = igAuthService;
        this.redisTemplate = redisTemplate;
        this.igApiProperties = igApiProperties;
    }

    public void storeTokens(String cst, String securityToken){
        redisTemplate.opsForValue().set(igApiProperties.getCstLabel(), cst, Duration.ofHours(1));
        redisTemplate.opsForValue().set(igApiProperties.getSecurityTokenLabel(), securityToken, Duration.ofHours(1));
    }

    public String getCstKey(){
        return redisTemplate.opsForValue().get(igApiProperties.getCstLabel());
    }

    public String getSecurityTokenKey(){ return redisTemplate.opsForValue().get(igApiProperties.getSecurityTokenLabel());}

    public boolean areTokensExpired(){
        return redisTemplate.opsForValue().get(igApiProperties.getCstLabel()) == null ||
                redisTemplate.opsForValue().get(igApiProperties.getSecurityTokenLabel()) == null;
    }

    public Mono<Void> refreshTokens(){
        return igAuthService.authenticate()
                .then();
    }
}
