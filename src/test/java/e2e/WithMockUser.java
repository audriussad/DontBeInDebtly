package e2e;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Run test with a fake logged-in user with your custom user ID.
 * Allows to pass in custom authorities if needed
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockUser {
    long userId() default 1L;
    String[] authorities() default "USER";
}
