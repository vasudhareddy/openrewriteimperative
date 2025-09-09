package com.example.app;


public class OneStatementPerLineDemo {

    public String mapValue(String input) {
        // 🚨 Sonar S122 violation: two statements in one line
        if (input == null) return null;

        String result = input.trim();
        if (result.isEmpty()) return "EMPTY";

        return result;
    }
}

