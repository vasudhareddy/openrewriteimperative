package com.example.app;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthConstantsTest {

    @Test
    void classIsFinal() {
        assertTrue(Modifier.isFinal(AuthConstants.class.getModifiers()));
    }

    @Test
    void constructorIsPrivate() throws NoSuchMethodException {
        Constructor<AuthConstants> constructor = AuthConstants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void usernameConstantsAreNotNull() {
        assertNotNull(AuthConstants.USERNAME_ADMIN);
        assertNotNull(AuthConstants.USERNAME_GUEST);
        assertNotNull(AuthConstants.USERNAME_ROOT);
    }

    @Test
    void roleConstantsHaveExpectedValues() {
        assertEquals("ADMIN_ROLE", AuthConstants.ROLE_ADMIN);
        assertEquals("GUEST_ROLE", AuthConstants.ROLE_GUEST);
        assertEquals("USER_ROLE", AuthConstants.ROLE_USER);
    }
}
