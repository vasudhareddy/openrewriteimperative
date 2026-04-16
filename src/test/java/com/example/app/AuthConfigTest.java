package com.example.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthConfigTest {

    @Test
    void loadsTestPropertiesFile() {
        AuthConfig config = new AuthConfig("test-auth.properties");
        assertEquals("testadminkey", config.getAdminKey());
        assertEquals("testrootkey", config.getRootKey());
    }

    @Test
    void missingFileReturnsEmptyStrings() {
        AuthConfig config = new AuthConfig("nonexistent.properties");
        assertNotNull(config.getAdminKey());
        assertNotNull(config.getRootKey());
        assertEquals("", config.getAdminKey());
        assertEquals("", config.getRootKey());
    }
}
