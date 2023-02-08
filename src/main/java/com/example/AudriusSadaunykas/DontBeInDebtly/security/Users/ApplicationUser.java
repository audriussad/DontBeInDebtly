package com.example.AudriusSadaunykas.DontBeInDebtly.security.Users;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole applicationUserRole;
    private Boolean locked = false;
    private Boolean enabled = true;

    public ApplicationUser(String password,
                           String firstName,
                           String lastName,
                           String email,
                           ApplicationUserRole applicationUserRole)
    {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.applicationUserRole = applicationUserRole;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(applicationUserRole.name());
//        return Collections.singletonList(authority);
//    }

    public ApplicationUserRole getRole() {
        return applicationUserRole;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }


}
