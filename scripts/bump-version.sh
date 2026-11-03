#!/usr/bin/env bash
set -euo pipefail

if ! command -v git >/dev/null 2>&1; then
    echo "git is not installed." >&2
    exit 1
fi

repo_root="$(git rev-parse --show-toplevel 2>/dev/null || true)"
if [ -z "$repo_root" ]; then
    echo "Not inside a git repository." >&2
    exit 1
fi

git fetch --tags 2>/dev/null || echo "Warning: git fetch failed, using local tags." >&2

tags=()
while IFS= read -r tag; do
    [ -n "$tag" ] && tags+=("$tag")
done < <(git tag -l --sort=v:refname)

count="${#tags[@]}"
if [ "$count" -lt 2 ]; then
    echo "git is missing required tags." >&2
    exit 1
fi

prev_tag="${tags[$((count - 2))]#v}"
next_tag="${tags[$((count - 1))]#v}"

for file in "$repo_root/README.md" "$repo_root/gradle.properties"; do
    if [ ! -f "$file" ]; then
        echo "Warning: $file not found, skipping." >&2
        continue
    fi
    escaped_prev="$(printf '%s\n' "$prev_tag" | sed 's/[.[\*^$()+?{|/]/\\&/g')"
    escaped_next="$(printf '%s\n' "$next_tag" | sed 's/[&/|\\]/\\&/g')"
    sed -i.bak "s/${escaped_prev}/${escaped_next}/g" "$file" && rm -f "${file}.bak"
done
