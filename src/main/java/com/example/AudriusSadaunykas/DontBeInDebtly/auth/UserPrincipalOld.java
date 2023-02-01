//package com.example.AudriusSadaunykas.DontBeInDebtly.auth;
//
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.util.Assert;
//import java.util.Collection;
//
//public class UserPrincipalOld extends AbstractAuthenticationToken {
//
//    private final String id;
//    private Object credentials;
//
//    public UserPrincipalOld(String id, Object credentials, Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.id = id;
//        this.credentials = credentials;
//        super.setAuthenticated(true);
//    }
//
//    public UserPrincipalOld(String id, Object credentials) {
//        super(null);
//        this.id = id;
//        this.credentials = credentials;
//        setAuthenticated(false);
//    }
//
//    @Override
//    public Object getCredentials() {
//        return credentials;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return id;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        Assert.isTrue(!isAuthenticated,
//                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        super.setAuthenticated(false);
//    }
//
//    @Override
//    public void eraseCredentials() {
//        super.eraseCredentials();
//        this.credentials = null;
//    }
//
//    public static UserPrincipalOld authenticated(String id, Object credentials,
//                                                 Collection<? extends GrantedAuthority> authorities) {
//        return new UserPrincipalOld(id, credentials, authorities);
//    }
//
//    public static UserPrincipalOld unauthenticated(String id, Object credentials) {
//        return new UserPrincipalOld(id, credentials);
//    }
//
//}
