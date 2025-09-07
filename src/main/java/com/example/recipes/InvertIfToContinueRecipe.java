//package com.example.recipes;
//
//import org.openrewrite.*;
//import org.openrewrite.java.*;
//import org.openrewrite.java.tree.*;
//import org.openrewrite.marker.*;
//
//import java.util.Collections;
//
//public class InvertIfToContinueRecipe extends Recipe {
//    @Override public String getDisplayName(){ return "Prefer early `continue` in loops"; }
//    @Override public String getDescription(){ return "Turns `if (cond) { body }` inside loops into `if (!cond) continue; body;` when safe."; }
//
//    @Override public TreeVisitor<?, ExecutionContext> getVisitor() {
//        return new JavaIsoVisitor<ExecutionContext>() {
//            @Override
//            public J.If visitIf(J.If iff, ExecutionContext ctx) {
//                iff = (J.If) super.visitIf(iff, ctx);
//                if (!(getCursor().getParentTreeCursor().getValue() instanceof J.ForLoop
//                        || getCursor().getParentTreeCursor().getValue() instanceof J.WhileLoop
//                        || getCursor().getParentTreeCursor().getValue() instanceof J.DoWhileLoop)) return iff;
//                if (iff.getElsePart() != null) return iff;
//                if (!(iff.getThenPart() instanceof J.Block)) return iff;
//
//                J.Unary notCond = new J.Unary(Space.EMPTY, J.Unary.Type.Not, iff.getIfCondition().getTree(),
//                        Space.EMPTY, Markers.EMPTY);
//                J.Continue cont = new J.Continue(Space.build(" ", Collections.emptyList(), Markers.EMPTY), null, Markers.EMPTY);
//
//                J.Block guardBlock = new J.Block(Tree.randomId(), Space.EMPTY, Markers.EMPTY,
//                        new JRightPadded<>(cont, Space.EMPTY, Markers.EMPTY), Collections.emptyList(),
//                        Space.build("\n", Collections.emptyList(), Markers.EMPTY));
//
//                // Build: if (!cond) continue; <original body>
//                return new J.Block(Tree.randomId(), Space.EMPTY, Markers.EMPTY,
//                        new JRightPadded<>(iff.withIfCondition(iff.getIfCondition().withTree(notCond))
//                                .withThenPart(guardBlock), Space.build("\n", Collections.emptyList(), Markers.EMPTY), Markers.EMPTY),
//                        ((J.Block) iff.getThenPart()).getStatements(),
//                        Space.EMPTY);
//            }
//        };
//    }
//}
