package com.example.securitypractice.security;

public enum UserPermission {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_WRITE("transaction:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
