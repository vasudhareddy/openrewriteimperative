# OpenRewrite Custom Recipe Sample (v4)

**Key fix:** we explicitly add the project's compiled classes/resources to the plugin's `rewrite` configuration:
```
dependencies { rewrite sourceSets.main.output }
```
This guarantees the plugin can load your custom recipe, even in environments where only `rewrite` deps are scanned.

## Run
gradle clean build
gradle rewriteDiscover
gradle rewriteDryRun
gradle rewriteRun
