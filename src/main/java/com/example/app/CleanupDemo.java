package com.example.app;


import java.util.List;   // 🚨 unused import
import java.util.ArrayList; // 🚨 unused import

public class CleanupDemo {

    private String unusedField;  // 🚨 unread private field

    public void doWork(String input, String unusedParam) {  // 🚨 unused parameter
        int unusedLocal = 42;   // 🚨 unused local variable
        System.out.println(input);
    }
}
