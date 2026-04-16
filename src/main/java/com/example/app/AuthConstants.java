package com.example.app;

/**
 * Centralized constants for authentication usernames and role names.
 * Keeping these in one place makes them easy to audit and update.
 * <p>
 * Credentials are loaded from configuration via {@link AuthConfig} and are
 * not stored as compile-time constants.
 */
public final class AuthConstants {

    private AuthConstants() {
        // prevent instantiation
    }

    // ---- Usernames ----
    public static final String USERNAME_ADMIN = "admin";
    public static final String USERNAME_GUEST = "guest";
    public static final String USERNAME_ROOT  = "root";

    // ---- Role names ----
    public static final String ROLE_ADMIN = "ADMIN_ROLE";
    public static final String ROLE_GUEST = "GUEST_ROLE";
    public static final String ROLE_USER  = "USER_ROLE";
}
