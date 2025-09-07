//package com.example.recipes;
//
//
//import org.openrewrite.ExecutionContext;
//import org.openrewrite.Recipe;
//import org.openrewrite.TreeVisitor;
//import org.openrewrite.java.JavaIsoVisitor;
//import org.openrewrite.java.tree.J;
//import org.openrewrite.java.tree.Space;
//
//public class MergeNestedIfsRecipe extends Recipe {
//    @Override public String getDisplayName() { return "Merge nested ifs into conjunctive conditions"; }
//    @Override public String getDescription() { return "Transforms `if(a){ if(b){ ... }}` into `if(a && b){ ... }` when safe."; }
//
//    @Override
//    public TreeVisitor<?, ExecutionContext> getVisitor() {
//        return new JavaIsoVisitor<ExecutionContext>() {
//            @Override
//            public J.If visitIf(J.If outerIf, ExecutionContext ctx) {
//                outerIf = (J.If) super.visitIf(outerIf, ctx);
//                if (!(outerIf.getThenPart() instanceof J.Block)) return outerIf;
//                J.Block then = (J.Block) outerIf.getThenPart();
//
//                // single statement in 'then' and it is an if, with no else branches
//                if (then.getStatements().size() == 1 && then.getStatements().get(0) instanceof J.If) {
//                    J.If innerIf = (J.If) then.getStatements().get(0);
//                    if (innerIf.getElsePart() != null) return outerIf;
//
//                    // Merge: if (cond1 && cond2) { innerThen }
//                    J.Binary merged = new J.Binary(
//                            Space.EMPTY, outerIf.getIfCondition().getTree(), J.Binary.Type.And, innerIf.getIfCondition().getTree(),
//                            Space.build(" ", java.util.Collections.emptyList(), org.openrewrite.marker.Markers.EMPTY),
//                            org.openrewrite.marker.Markers.EMPTY, null);
//                    J.If newIf = outerIf.withIfCondition(outerIf.getIfCondition().withTree(merged))
//                            .withThenPart(innerIf.getThenPart().withPrefix(then.getPrefix()));
//                    return newIf;
//                }
//                return outerIf;
//            }
//        };
//    }
//}
//
