package com.example.AudriusSadaunykas.DontBeInDebtly.controllers;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.LoginResponse;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.LoginService;
import com.example.AudriusSadaunykas.DontBeInDebtly.requests.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;


  @PostMapping
    public LoginResponse login(@RequestBody @Validated LoginRequest request) throws Exception{
      return loginService.attemptLogin(request.getEmail(), request.getPassword());
  }
}
