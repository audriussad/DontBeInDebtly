package com.example.AudriusSadaunykas.DontBeInDebtly.security.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String accessToken;
}
