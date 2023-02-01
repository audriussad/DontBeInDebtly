package com.example.AudriusSadaunykas.DontBeInDebtly.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JWTDecode {
    private Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());


    public DecodedJWT decodedJWT(String JWTHeader) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(JWTHeader);
    }
}
