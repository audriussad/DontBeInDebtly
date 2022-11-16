package com.example.AudriusSadaunykas.DontBeInDebtly.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(s)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with email  %s not found", s)));
    }

    public String signUpUser(ApplicationUser applicationUser) {
        boolean userExists = applicationUserRepository
                .findByEmail(applicationUser.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode(applicationUser.getPassword());

        applicationUser.setPassword(encodedPassword);

        applicationUserRepository.save(applicationUser);
        //TODO: Send email conformation token
        return applicationUser.getFirstName() + " registered";
    }

}
