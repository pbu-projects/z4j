#!/usr/bin/env bash
set -euo pipefail

has_gum() {
    command -v gum >/dev/null 2>&1
}

log_info() {
    if has_gum; then
        gum log --level info "$@"
    else
        echo "INFO: $*"
    fi
}

log_warn() {
    if has_gum; then
        gum log --level warn "$@"
    else
        echo "WARN: $*" >&2
    fi
}

log_error() {
    if has_gum; then
        gum log --level error "$@"
    else
        echo "ERROR: $*" >&2
    fi
}

if ! command -v git >/dev/null 2>&1; then
    log_error "git is not installed."
    exit 1
fi

repo_root="$(git rev-parse --show-toplevel 2>/dev/null || true)"
if [ -z "$repo_root" ]; then
    log_error "Not inside a git repository."
    exit 1
fi

if has_gum; then
    gum style --border rounded --padding "0 1" --border-foreground 212 "z4j: bump-version"
fi

if has_gum; then
    gum spin --spinner dot --title "Fetching git tags..." -- sh -c 'git fetch --tags 2>/dev/null' || log_warn "git fetch failed, using local tags."
else
    git fetch --tags 2>/dev/null || log_warn "git fetch failed, using local tags."
fi

tags=()
while IFS= read -r tag; do
    [ -n "$tag" ] && tags+=("$tag")
done < <(git tag -l --sort=v:refname)

count="${#tags[@]}"
if [ "$count" -lt 2 ]; then
    log_error "git is missing required tags."
    exit 1
fi

prev_tag="${tags[$((count - 2))]#v}"
next_tag="${tags[$((count - 1))]#v}"

if has_gum; then
    gum log --structured --level info "Bumping version" from "$prev_tag" to "$next_tag"
else
    echo "Bumping version from $prev_tag to $next_tag"
fi

for file in "$repo_root/README.md" "$repo_root/gradle.properties" "$repo_root/src/main/docs/header.svg"; do
    if [ ! -f "$file" ]; then
        log_warn "$file not found, skipping."
        continue
    fi
    escaped_prev="$(printf '%s\n' "$prev_tag" | sed 's/[.[\*^$()+?{|/]/\\&/g')"
    escaped_next="$(printf '%s\n' "$next_tag" | sed 's/[&/|\\]/\\&/g')"
    sed -i.bak "s/${escaped_prev}/${escaped_next}/g" "$file" && rm -f "${file}.bak"
    if has_gum; then
        gum log --level info "Updated $(basename "$file")"
    else
        echo "Updated $(basename "$file")"
    fi
done

if has_gum; then
    gum style --foreground 2 --bold "✓ Successfully bumped version: $prev_tag -> $next_tag"
else
    echo "Successfully bumped version: $prev_tag -> $next_tag"
fi
