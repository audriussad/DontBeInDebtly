package com.example.AudriusSadaunykas.DontBeInDebtly.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.AudriusSadaunykas.DontBeInDebtly.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(BUDGET_ITEM_WRITE, BUDGET_ITEM_READ, TRANSACTION_READ, TRANSACTION_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
