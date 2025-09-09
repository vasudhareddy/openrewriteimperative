package com.example.app;// Added by AddCommentRecipe

import java.util.HashMap;
import java.util.Map;// Added by AddCommentRecipe
public class StringLiteralIssue {
    public static void main(String[] args) {
        String input = "bar";
        Map<String,String> map = new HashMap<>();
        map.put("foo", "value1");
        map.put("bar", "value2");

        for (String key : map.keySet()) {
            {
            if (!"bar".equals(key)) return;
        }

                System.out.println("Found key: " + key + " with value: " + map.get(key));
        }


        {
        map.put("foo", "value1");
        map.put("bar", "value2");

        for (String key : map.keySet()) {
            if ("bar".equals(key)) {
                System.out.println("Found key: " + key + " with value: " + map.get(key));
            }
        }


        if (!"foo".equals(input)) return;

        // Another example
        if ("baz".equals(input.trim())) {
            System.out.println("Matched baz!");
        }
    }

            System.out.println("Matched foo!");

        // Another example
        {
        map.put("foo", "value1");
        map.put("bar", "value2");

        for (String key : map.keySet()) {
            if ("bar".equals(key)) {
                System.out.println("Found key: " + key + " with value: " + map.get(key));
            }
        }


        if ("foo".equals(input)) {
            System.out.println("Matched foo!");
        }

        if (!"baz".equals(input.trim())) return;
    }

            System.out.println("Matched baz!");
    }
}
