package com.example.AudriusSadaunykas.DontBeInDebtly.registration;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUser;
import com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final SignUpService applicationUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return applicationUserService.signUpUser(
                new ApplicationUser(
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        ApplicationUserRole.USER
                )
        );
    }
}
