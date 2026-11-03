$ErrorActionPreference = 'Stop'

git -v | Out-Null
if (!$?) {
    Write-Error "git is not installed."
    exit 1
}

$repoRoot = git rev-parse --show-toplevel 2>$null
if (!$repoRoot) {
    Write-Error "Not inside a git repository."
    exit 1
}

try {
    git fetch --tags
} catch {
    Write-Warning "git fetch failed, using local tags."
}

$tags = @(git tag -l --sort=v:refname | ForEach-Object { $_ -replace '^v', '' })

if ($tags.Count -lt 2) {
    Write-Error "git is missing required tags."
    exit 1
}

$prevTag = $tags[-2]
$nextTag = $tags[-1]

@( "$repoRoot/README.md", "$repoRoot/gradle.properties" ) | ForEach-Object {
    if (Test-Path $_) {
        $content = Get-Content -Raw $_
        $updated = $content.Replace($prevTag, $nextTag)
        [System.IO.File]::WriteAllText($_, $updated, [System.Text.UTF8Encoding]::new($false))
    } else {
        Write-Warning "File not found: $_"
    }
}
