package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringLiteralsTest {

    private StringLiterals stringLiterals;
    private AuthConfig authConfig;

    @BeforeEach
    void setUp() {
        authConfig = new AuthConfig("test-auth.properties");
        stringLiterals = new StringLiterals(authConfig);
    }

    // ---- authenticate() tests ----

    @Test
    void authenticateAdminWithCorrectKey() {
        assertTrue(stringLiterals.authenticate(AuthConstants.USERNAME_ADMIN, authConfig.getAdminKey()));
    }

    @Test
    void authenticateAdminWithWrongKey() {
        assertFalse(stringLiterals.authenticate(AuthConstants.USERNAME_ADMIN, "wrongkey"));
    }

    @Test
    void authenticateGuestIsAlwaysDenied() {
        assertFalse(stringLiterals.authenticate(AuthConstants.USERNAME_GUEST, "anykey"));
    }

    @Test
    void authenticateRootWithCorrectKey() {
        assertTrue(stringLiterals.authenticate(AuthConstants.USERNAME_ROOT, authConfig.getRootKey()));
    }

    @Test
    void authenticateRootWithWrongKey() {
        assertFalse(stringLiterals.authenticate(AuthConstants.USERNAME_ROOT, "wrongkey"));
    }

    @Test
    void authenticateUnknownUserIsDenied() {
        assertFalse(stringLiterals.authenticate("unknown", "somekey"));
    }

    // ---- getRole() tests ----

    @Test
    void getRoleReturnsAdminRoleForAdmin() {
        assertEquals(AuthConstants.ROLE_ADMIN, stringLiterals.getRole(AuthConstants.USERNAME_ADMIN));
    }

    @Test
    void getRoleReturnsGuestRoleForGuest() {
        assertEquals(AuthConstants.ROLE_GUEST, stringLiterals.getRole(AuthConstants.USERNAME_GUEST));
    }

    @Test
    void getRoleReturnsUserRoleForUnknownUser() {
        assertEquals(AuthConstants.ROLE_USER, stringLiterals.getRole("unknown"));
    }

    @Test
    void getRoleReturnsUserRoleForRoot() {
        assertEquals(AuthConstants.ROLE_USER, stringLiterals.getRole(AuthConstants.USERNAME_ROOT));
    }
}
