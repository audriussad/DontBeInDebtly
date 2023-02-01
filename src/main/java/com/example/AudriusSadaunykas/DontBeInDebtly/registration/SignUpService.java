package com.example.AudriusSadaunykas.DontBeInDebtly.registration;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

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
