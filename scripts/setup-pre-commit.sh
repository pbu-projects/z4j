#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
HOOK_PATH="${REPO_ROOT}/.git/hooks/pre-commit"

cat << 'EOF' > "$HOOK_PATH"
#!/usr/bin/env bash
set -e

REPO_ROOT="$(git rev-parse --show-toplevel)"
"${REPO_ROOT}/scripts/clean-imports.sh" --staged
EOF

chmod +x "$HOOK_PATH"
chmod +x "${REPO_ROOT}/scripts/clean-imports.sh"

echo "Pre-commit hook installed successfully to ${HOOK_PATH}"
