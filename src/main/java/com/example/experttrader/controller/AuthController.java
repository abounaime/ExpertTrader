package com.example.experttrader.controller;

import com.example.experttrader.service.IgAuthService;
import com.example.experttrader.dto.LoginResponse;
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
    public Mono<LoginResponse> getToken(){
        return igAuthService.authenticate();
    }
}
