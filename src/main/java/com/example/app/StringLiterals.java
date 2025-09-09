package com.example.app;

public class StringLiterals {

    public boolean authenticate(String username, String key) {
        if (username.equals("admin") && key.equals("key")) {
            return true;
        }

        if (username.equals("guest")) {
            return false; // guest users not allowed
        }

        return username.equals("root") && key.equals("toor");
    }

    public String getRole(String username) {
        if (username.equals("admin")) {
            return "ADMIN_ROLE";
        } else if (username.equals("guest")) {
            return "GUEST_ROLE";
        }
        return "USER_ROLE";
    }
}

