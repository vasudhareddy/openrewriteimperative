package com.example.recipes;


import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.Expression;
import org.openrewrite.java.tree.J;

import java.util.Collections;

public class StringLiteralEqualityRecipe extends Recipe {
    @Override
    public String getDisplayName() {
        return "Move string literal to left side of equals";
    }

    @Override
    public String getDescription() {
        return "Fixes Sonar rule S1132 by ensuring string literals are on the left side of equals().";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.MethodInvocation visitMethodInvocation(J.MethodInvocation mi, ExecutionContext ctx) {
                mi = super.visitMethodInvocation(mi, ctx);

                if (mi.getSimpleName().equals("equals") && mi.getArguments().size() == 1) {
                    Expression arg = mi.getArguments().get(0);
                    // if argument is a string literal and the select is not a string literal
                    if (arg instanceof J.Literal && ((J.Literal) arg).getValue() instanceof String
                            && !(mi.getSelect() instanceof J.Literal)) {
                        // swap sides: "literal".equals(variable)
                        return mi.withSelect(arg)
                                .withArguments(Collections.singletonList(mi.getSelect()));
                    }
                }
                return mi;
            }
        };
    }
}

