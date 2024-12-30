package com.example.experttrader.controller;

import com.example.experttrader.service.IgAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ig-auth")
public class AuthController {
    private final IgAuthService igAuthService;

    public AuthController(IgAuthService igAuthService) {
        this.igAuthService = igAuthService;
    }
    @PostMapping("/token")
    public ResponseEntity<Mono<String>> getToken(){
        Mono<String> accessToken = igAuthService.authenticate();
        return ResponseEntity.ok(accessToken);
    }
}
