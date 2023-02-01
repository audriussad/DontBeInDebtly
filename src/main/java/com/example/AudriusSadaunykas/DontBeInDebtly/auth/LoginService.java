package com.example.AudriusSadaunykas.DontBeInDebtly.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    public LoginResponse attemptLogin(String rawEmail, String password) throws Exception{
        String email = rawEmail.toLowerCase();
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        return LoginResponse.builder()
                .accessToken(issueJwt(principal))
                .build();
    }

    private String issueJwt(UserPrincipal principal) {
        return JWT.create()
                .withSubject(principal.getUserId().toString())
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .withClaim("roles", principal.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .sign(Algorithm.HMAC256("secret".getBytes()));
    }
}
