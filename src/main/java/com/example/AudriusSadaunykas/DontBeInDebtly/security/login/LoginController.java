package com.example.AudriusSadaunykas.DontBeInDebtly.security.login;

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
