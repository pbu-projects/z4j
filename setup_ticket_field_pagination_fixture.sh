#!/usr/bin/env bash
set -euo pipefail

if ! command -v curl >/dev/null 2>&1; then
  echo "curl is required"
  exit 1
fi

if ! command -v jq >/dev/null 2>&1; then
  echo "jq is required"
  exit 1
fi

: "${Z4J_URL:?Z4J_URL is required}"
: "${Z4J_TOKEN:?Z4J_TOKEN is required}"
: "${Z4J_ADMIN_EMAIL:?Z4J_ADMIN_EMAIL is required}"

target="${1:-110}"
if ! [[ "$target" =~ ^[0-9]+$ ]]; then
  echo "Target must be an integer"
  exit 1
fi

auth_user="${Z4J_ADMIN_EMAIL}/token"
base_url="${Z4J_URL%/}"
list_url="${base_url}/api/v2/ticket_fields?page[size]=100"

echo "Checking current ticket field count using cursor pagination..."

count=0
after=""
while true; do
  url="$list_url"
  if [[ -n "$after" ]]; then
    url="${url}&page[after]=${after}"
  fi

  response="$(curl -sS -u "${auth_user}:${Z4J_TOKEN}" "$url")"
  page_count="$(echo "$response" | jq '.ticket_fields | length')"
  count=$((count + page_count))

  has_more="$(echo "$response" | jq -r '.meta.has_more // false')"
  next_after="$(echo "$response" | jq -r '.meta.after_cursor // empty')"
  if [[ "$has_more" != "true" || -z "$next_after" ]]; then
    break
  fi
  after="$next_after"
done

echo "Current ticket fields: $count"

if (( count >= target )); then
  echo "No changes needed (already >= $target)."
  exit 0
fi

to_create=$((target - count))
echo "Creating $to_create text ticket fields..."

for ((i = 1; i <= to_create; i++)); do
  suffix="$(date +%s)-$RANDOM-$i"
  payload="$(jq -n --arg title "z4j-cursor-fixture-$suffix" \
    '{ticket_field: {title: $title, type: "text"}}')"

  curl -sS -u "${auth_user}:${Z4J_TOKEN}" \
    -H 'Content-Type: application/json' \
    -d "$payload" \
    "${base_url}/api/v2/ticket_fields" >/dev/null
done

echo "Created $to_create fields. Zendesk may take a short time to reflect all fields in list responses."
