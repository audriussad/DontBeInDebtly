package com.example.AudriusSadaunykas.DontBeInDebtly.security;

public enum ApplicationUserPermission {
    BUDGET_ITEM_READ("budgetItem:read"),
    BUDGET_ITEM_WRITE("budgetItem:write"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_ITEM_ENTITY_WRITE("transactionItemEntity:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
