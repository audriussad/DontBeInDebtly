package com.example.AudriusSadaunykas.DontBeInDebtly.security;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.UserPrincipal;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.UserPrincipalAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTDecode jwtDecode;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = extractJWTFromRequest(request);
            if (token != null) {
                var decodedToken = jwtDecode.decodedJWT(token);
                String id = decodedToken.getSubject();
                String[] roles = decodedToken.getClaim("roles").asArray(String.class);
                var authorities = stream(roles).map(SimpleGrantedAuthority::new).toList();
                Authentication authentication = new UserPrincipalAuthentication(UserPrincipal.builder()
                        .userId(Long.valueOf(id))
                        .authorities(authorities)
                        .build());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("pzda");
        }
        filterChain.doFilter(request, response);
    }

    private String extractJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return request.getParameter("auth_token");
    }
}
