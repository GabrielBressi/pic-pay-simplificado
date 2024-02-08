package com.picpaysimplificado.models.users;

public enum UserRole {
    SHOPKEEPER("shopkeeper"),
    COMMON("common");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
