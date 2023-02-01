package com.example.AudriusSadaunykas.DontBeInDebtly.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String accessToken;
}
