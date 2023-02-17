package com.example.AudriusSadaunykas.DontBeInDebtly.security.registration;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.Users.ApplicationUser;
import com.example.AudriusSadaunykas.DontBeInDebtly.security.Users.ApplicationUserRepository;
import com.example.AudriusSadaunykas.DontBeInDebtly.security.Users.ApplicationUserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return signUpUser(
                new ApplicationUser(
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        ApplicationUserRole.USER
                )
        );
    }
    private String signUpUser(ApplicationUser applicationUser) {
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
