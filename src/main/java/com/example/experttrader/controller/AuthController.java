package com.example.experttrader.controller;

import com.example.experttrader.service.IgAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IgAuthService igAuthService;

    public AuthController(IgAuthService igAuthService) {
        this.igAuthService = igAuthService;
    }
    @GetMapping("/token")
    public ResponseEntity<String> getToken(){
        String accessToken = igAuthService.authenticate();
        return ResponseEntity.ok(accessToken);
    }
}
