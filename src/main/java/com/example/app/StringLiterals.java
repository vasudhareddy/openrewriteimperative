package com.example.app;

import static com.example.app.AuthConstants.ROLE_ADMIN;
import static com.example.app.AuthConstants.ROLE_GUEST;
import static com.example.app.AuthConstants.ROLE_USER;
import static com.example.app.AuthConstants.USERNAME_ADMIN;
import static com.example.app.AuthConstants.USERNAME_GUEST;
import static com.example.app.AuthConstants.USERNAME_ROOT;

public class StringLiterals {

    private final AuthConfig authConfig;

    public StringLiterals() {
        this(new AuthConfig());
    }

    public StringLiterals(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public boolean authenticate(String username, String key) {
        if (USERNAME_ADMIN.equals(username) && authConfig.getAdminKey().equals(key)) {
            return true;
        }

        if (USERNAME_GUEST.equals(username)) {
            return false; // guest users not allowed
        }

        return USERNAME_ROOT.equals(username) && authConfig.getRootKey().equals(key);
    }

    public String getRole(String username) {
        if (USERNAME_ADMIN.equals(username)) {
            return ROLE_ADMIN;
        } else if (USERNAME_GUEST.equals(username)) {
            return ROLE_GUEST;
        }
        return ROLE_USER;
    }
}

