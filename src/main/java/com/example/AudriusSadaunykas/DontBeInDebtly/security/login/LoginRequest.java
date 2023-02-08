package com.example.AudriusSadaunykas.DontBeInDebtly.security.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
