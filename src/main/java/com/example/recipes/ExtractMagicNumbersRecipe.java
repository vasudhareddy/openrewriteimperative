package com.example.recipes;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.java.JavaVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.tree.J;

import java.util.Set;

public class ExtractMagicNumbersRecipe extends Recipe {

    @Override
    public String getDisplayName() {
        return "Extract magic numbers into constants";
    }

    @Override
    public String getDescription() {
        return "Replaces numeric literals with static final constants declared at class level.";
    }

    @Override
    public JavaVisitor<ExecutionContext> getVisitor() {
        return new JavaVisitor<ExecutionContext>() {

            private final Set<String> trivialNumbers = Set.of("0", "1", "-1");

            @Override
            public J visitLiteral(J.Literal literal, ExecutionContext ctx) {
                if (literal.getValue() instanceof Number) {
                    String valueStr = literal.getValue().toString();

                    // skip common trivial numbers
                    if (trivialNumbers.contains(valueStr)) {
                        return super.visitLiteral(literal, ctx);
                    }

                    // create constant name
                    String constName = "CONST_" + valueStr
                            .replace("-", "NEG_")
                            .replace(".", "_");

                    J.ClassDeclaration enclosingClass = getCursor().firstEnclosing(J.ClassDeclaration.class);
                    if (enclosingClass == null) {
                        return super.visitLiteral(literal, ctx);
                    }

                    // add constant declaration if missing
                    maybeAddConstant(enclosingClass, constName, valueStr, literal);

                    // replace literal with identifier
                    return literal.withValue(null).withValueSource(constName);
                }
                return super.visitLiteral(literal, ctx);
            }

            private void maybeAddConstant(J.ClassDeclaration enclosingClass,
                                          String constName,
                                          String value,
                                          J.Literal literal) {

                // avoid duplicate insertion
                boolean alreadyExists = enclosingClass.getBody().getStatements().stream()
                        .anyMatch(s -> s.printTrimmed(getCursor()).contains(constName));
                if (alreadyExists) return;

                // infer type
                String type = (literal.getValue() instanceof Double || value.contains(".")) ? "double" : "int";

                // build template
                JavaTemplate template = JavaTemplate.builder(
                        "private static final " + type + " " + constName + " = " + value + ";"
                ).build();

                // schedule insertion at the top of the class body
                doAfterVisit(new JavaVisitor<ExecutionContext>() {
                    @Override
                    public J visitClassDeclaration(J.ClassDeclaration cd, ExecutionContext ctx) {
                        if (cd.isScope(enclosingClass)) {
                            // use template.apply instead of withTemplate()
                            return template.apply(
                                    updateCursor(cd),
                                    cd.getBody().getCoordinates().firstStatement()
                            );
                        }
                        return super.visitClassDeclaration(cd, ctx);
                    }
                });
            }
        };
    }
}
