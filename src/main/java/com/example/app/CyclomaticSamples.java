package com.example.app;


import java.util.ArrayList;
import java.util.List;

public class CyclomaticSamples {

    // --- 1) MergeNestedIfsRecipe target ------------------------------
    // Before: nested ifs
    // After : if (a > 0 && b > 0) { ... }
    public void nestedIfs(int a, int b) {
        if (a > 0) {
            if (b > 0) {
                doWork("nestedIfs");
            }
        }
    }

    // Deeper nesting (merge can happen more than once if you run the recipe multiple times)
    public void deeplyNestedIfs(int x, int y, int z) {
        if (x > 10) {
            if (y % 2 == 0) {
                if (z != 0) {
                    doWork("deeplyNestedIfs");
                }
            }
        }
    }

    // NOTE: this one should be left unchanged by MergeNestedIfsRecipe because of else
    public void nestedIfsWithElse(int a, int b) {
        if (a > 0) {
            if (b > 0) {
                doWork("shouldNotBreak");
            } else {
                doWork("hasElse");
            }
        }
    }


    // --- 2) GuardClauseReturnRecipe target ---------------------------
    // Before: if (ok) { body }
    // After : if (!ok) return; body
    public void guardClauseVoid(boolean ok) {
        if (ok) {
            doWork("guardClauseVoid-1");
            doWork("guardClauseVoid-2");
        }
    }

    // Another guard clause case: early-return before long body
    public void guardClauseWithMultipleStmts(boolean authenticated) {
        if (authenticated) {
            audit("entry");
            doWork("secure-action");
            audit("exit");
        }
    }


    // --- 3) InvertIfToContinueRecipe target --------------------------
    // Before: if (isValid(i)) { process(i); }
    // After : if (!isValid(i)) continue; process(i);
    public void loopInvertToContinue(List<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            String s = inputs.get(i);
            if (isValid(s)) {
                process(s);
            }
        }
    }

    // Works similarly for enhanced-for
    public void loopInvertToContinueEnhanced(List<String> inputs) {
        for (String s : inputs) {
            if (isValid(s)) {
                process(s);
            }
        }
    }

    // NOTE: should remain unchanged because it has an else
    public void loopWithElse(List<String> inputs) {
        for (String s : inputs) {
            if (isValid(s)) {
                process(s);
            } else {
                fallback(s);
            }
        }
    }


    // --- 4) ReturnBooleanDirectlyRecipe target -----------------------
    // Before: if (cond) return true; else return false;
    // After : return cond;
    public boolean returnBooleanDirectly(boolean cond) {
        if (cond) {
            return true;
        } else {
            return false;
        }
    }

    // Negated variant
    public boolean returnBooleanNegated(boolean cond) {
        if (cond) {
            return false;
        } else {
            return true;
        }
    }

    // Slightly different shape but still reducible:
    public boolean returnBooleanWithBlocks(boolean cond) {
        if (cond) {
            { return true; }
        } else {
            { return false; }
        }
    }


    // --- Bonus: a mixed method that hits multiple patterns -----------
    public void mixedSample(int x, int y, List<String> items) {
        // MergeNestedIfs target
        if (x > 0) {
            if (y > 5) {
                doWork("mixed-merge");
            }
        }

        // GuardClauseReturn target
        boolean ready = isReady(x, y);
        if (ready) {
            doWork("mixed-guard-1");
            doWork("mixed-guard-2");
        }

        // InvertIfToContinue target
        for (String it : items) {
            if (isValid(it)) {
                process(it);
            }
        }
    }


    // --- Simple helpers to keep the sample realistic -----------------
    private void doWork(String tag) {
        // pretend to do something important
        new ArrayList<>().add(tag);
    }

    private void process(String s) {
        // pretend processing
        if (s.length() > 3) {
            // no-op
        }
    }

    private void fallback(String s) {
        // alternate path
    }

    private void audit(String msg) {
        // log/audit
    }

    private boolean isValid(String s) {
        return s != null && !s.isBlank();
    }

    private boolean isReady(int x, int y) {
        return x + y > 10;
    }
}

