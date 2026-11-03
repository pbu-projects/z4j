#!/usr/bin/env bash
set -euo pipefail

# Cache directory for google-java-format
CACHE_DIR="${HOME}/.cache"
GJF_VERSION="1.36.1"
GJF_JAR="${CACHE_DIR}/google-java-format-${GJF_VERSION}-all-deps.jar"
GJF_URL="https://repo1.maven.org/maven2/com/google/googlejavaformat/google-java-format/${GJF_VERSION}/google-java-format-${GJF_VERSION}-all-deps.jar"

ensure_gjf() {
    if [ ! -f "$GJF_JAR" ]; then
        mkdir -p "$CACHE_DIR"
        echo "Downloading google-java-format ${GJF_VERSION}..."
        curl -sL "$GJF_URL" -o "$GJF_JAR"
    fi
}

MODE="${1:-}"

if [ "$MODE" = "--staged" ] || [ $# -eq 0 ]; then
    JAVA_FILES=$(git diff --cached --name-only --diff-filter=ACMR | grep '\.java$' || true)
    GROOVY_FILES=$(git diff --cached --name-only --diff-filter=ACMR | grep '\.groovy$' || true)
    STAGED=true
else
    JAVA_FILES=""
    GROOVY_FILES=""
    for file in "$@"; do
        if [[ "$file" == *.java ]]; then
            JAVA_FILES="${JAVA_FILES} $file"
        elif [[ "$file" == *.groovy ]]; then
            GROOVY_FILES="${GROOVY_FILES} $file"
        fi
    done
    STAGED=false
fi

# Clean Java imports
if [ -n "$JAVA_FILES" ]; then
    ensure_gjf
    echo "Cleaning unused Java imports..."
    for file in $JAVA_FILES; do
        if [ -f "$file" ]; then
            java -jar "$GJF_JAR" --fix-imports-only --replace "$file"
            if [ "$STAGED" = true ]; then
                git add "$file"
            fi
        fi
    done
fi

# Clean Groovy imports
if [ -n "$GROOVY_FILES" ]; then
    echo "Cleaning unused Groovy imports..."
    for file in $GROOVY_FILES; do
        if [ -f "$file" ]; then
            npx --yes npm-groovy-lint --fix --fixrules UnnecessaryImports,UnusedImport "$file" > /dev/null 2>&1 || true
            if [ "$STAGED" = true ]; then
                git add "$file"
            fi
        fi
    done
fi
