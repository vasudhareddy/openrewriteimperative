package com.example.recipes;

import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.ListUtils;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.tree.J;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.java.tree.Space;
import org.openrewrite.java.tree.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuardClauseReturnRecipe extends Recipe {

    @Override
    public String getDisplayName() {
        return "Use guard clause instead of nested `if` (void methods only)";
    }

    @Override
    public String getDescription() {
        return "Transforms `if (cond) { body }` into `if (!cond) return; body;` in void methods to reduce nesting.";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {

            // Build the guard statement once; supply the cursor at apply-time.
            private final JavaTemplate guardTemplate = JavaTemplate.builder(
                    "if (!#{any(boolean)}) return;"
            ).build();

            private boolean insideVoidMethodWithAttribution() {
                J.MethodDeclaration md = getCursor().firstEnclosing(J.MethodDeclaration.class);
                if (md == null || md.getMethodType() == null) {
                    return false; // require type attribution; avoid syntax-based checks
                }
                JavaType rt = md.getMethodType().getReturnType();
                return rt == JavaType.Primitive.Void;
            }

            @Override
            public J.Block visitBlock(J.Block block, ExecutionContext ctx) {
                block = (J.Block) super.visitBlock(block, ctx);

                if (!insideVoidMethodWithAttribution()) {
                    return block;
                }

                // Replace eligible `if` with: [guard, then-body...]
                List<Statement> rewritten = ListUtils.flatMap(block.getStatements(), (i, stmt) -> {
                    if (!(stmt instanceof J.If)) return Collections.singletonList(stmt);
                    J.If iff = (J.If) stmt;

                    // Must have no else and then-part must be a block
                    if (iff.getElsePart() != null || !(iff.getThenPart() instanceof J.Block)) {
                        return Collections.singletonList(stmt);
                    }

                    // Build guard from original condition
                    Statement guardStmt = (Statement) guardTemplate.apply(
                            getCursor(),
                            iff.getCoordinates().replace(),
                            iff.getIfCondition().getTree()
                    );
                    // Preserve leading whitespace/comments from the original if
                    guardStmt = guardStmt.withPrefix(iff.getPrefix());

                    // Lift the original body statements
                    List<Statement> thenStmts = ((J.Block) iff.getThenPart()).getStatements();

                    ArrayList<Statement> out = new ArrayList<>();
                    out.add(guardStmt);

                    if (!thenStmts.isEmpty()) {
                        // Ensure the first lifted statement starts on a new line
                        Statement first = thenStmts.get(0)
                                .withPrefix(withLeadingNewline(thenStmts.get(0).getPrefix()));
                        out.add(first);
                        for (int k = 1; k < thenStmts.size(); k++) {
                            out.add(thenStmts.get(k));
                        }
                    }
                    return out;
                });

                return block.withStatements(rewritten);
            }

            private Space withLeadingNewline(Space p) {
                String ws = p.getWhitespace();
                if (ws == null) ws = "";
                if (!ws.startsWith("\n")) {
                    ws = "\n" + ws;
                }
                return p.withWhitespace(ws);
            }
        };
    }
}
