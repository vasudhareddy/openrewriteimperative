package com.example.recipes;

import com.google.auto.service.AutoService;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.Space;
import org.openrewrite.marker.Markers;

import java.util.Collections;// Added by AddCommentRecipe



@AutoService(Recipe.class)
public class AddCommentRecipe extends Recipe {

    @Override
    public String getDisplayName() {
        return "Add header comment to classes";
    }

    @Override
    public String getDescription() {
        return "Adds a one-line header comment above each class declaration.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            @Override
            public J.ClassDeclaration visitClassDeclaration(J.ClassDeclaration cd, ExecutionContext ctx) {
                cd = (J.ClassDeclaration) super.visitClassDeclaration(cd, ctx);

                String header = "// Added by AddCommentRecipe\n";
                String existing = cd.getPrefix().getWhitespace();

                if (!existing.startsWith(header)) {
                    cd = cd.withPrefix(
                        Space.build(header + existing, Collections.emptyList())
                    );
                }
                return cd;
            }
        };
    }
}
