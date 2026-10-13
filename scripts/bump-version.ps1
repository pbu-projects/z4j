git -v | Out-Null
if (!$?){
    Write-Error "git is not installed."
    return
}
git fetch --tags
$tags = git tag -l

@( ".\README.md", ".\gradle.properties" ) | % {
    (Get-Content -Raw $_) -replace $tags[-2], $tags[-1] | Set-Content -NoNewLine $_
}