package com.example.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads authentication credentials from {@code auth.properties} on the
 * classpath. Property values that look like {@code ${ENV_VAR}} are resolved
 * from the environment so that real secrets never need to live in the file.
 */
public final class AuthConfig {

    private static final String PROPERTIES_FILE = "auth.properties";

    static final String PROP_ADMIN_KEY = "auth.admin.key";
    static final String PROP_ROOT_KEY  = "auth.root.key";

    private final Properties properties;

    public AuthConfig() {
        this(PROPERTIES_FILE);
    }

    AuthConfig(String resourceName) {
        properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + resourceName, e);
        }
    }

    public String getAdminKey() {
        return resolve(properties.getProperty(PROP_ADMIN_KEY, ""));
    }

    public String getRootKey() {
        return resolve(properties.getProperty(PROP_ROOT_KEY, ""));
    }

    /**
     * If the value matches the pattern {@code ${VAR_NAME}}, look up the
     * environment variable. Otherwise return the literal value.
     */
    private static String resolve(String value) {
        if (value != null && value.startsWith("${") && value.endsWith("}")) {
            String envVar = value.substring(2, value.length() - 1);
            String envValue = System.getenv(envVar);
            return envValue != null ? envValue : "";
        }
        return value != null ? value : "";
    }
}
