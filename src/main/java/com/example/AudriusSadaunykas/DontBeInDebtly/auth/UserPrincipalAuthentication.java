package com.example.AudriusSadaunykas.DontBeInDebtly.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipalAuthentication extends AbstractAuthenticationToken {
    private UserPrincipal userPrincipal;

    public UserPrincipalAuthentication() {
        super(null);
        setAuthenticated(false);
    }

    public UserPrincipalAuthentication(UserPrincipal principal) {
        super(principal.getAuthorities());
        setAuthenticated(true);
        this.userPrincipal = principal;
    }

    @Override
    public String getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }
}
