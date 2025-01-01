package com.example.experttrader.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotNull String identifier, @NotNull String password
) { }
