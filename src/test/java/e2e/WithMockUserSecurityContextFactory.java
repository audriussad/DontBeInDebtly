package e2e;

import com.example.AudriusSadaunykas.DontBeInDebtly.auth.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.example.AudriusSadaunykas.DontBeInDebtly.auth.ApplicationUserRole.USER;

/**
 * Builds a custom {@link SecurityContext} with a fake user for endpoint tests
 */
public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        var context = SecurityContextHolder.createEmptyContext();

        var authorities = Arrays.stream(annotation.authorities())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());

        var principal = UserPrincipal.builder()
                .userId( annotation.userId() )
                .email("test@skatteguiden.dk")
                .name("Test User")
//                .authMethod("BIOMETRIC")
                .authorities( authorities )
                .build();

        var user = new ApplicationUser();
        user.setId(annotation.userId());
        user.setEmail("loxas@momakas.cum");
        user.setFirstName("Gustas");
        user.setLastName("Liaugminas");
        user.setPassword("asdhashddf");
        user.setApplicationUserRole(USER);

//        var auth = new UserPrincipalOld(user.getId().toString(), null, authorities);

        var auth = new UserPrincipalAuthentication(principal);
        context.setAuthentication(auth);
        return context;
    }
}
