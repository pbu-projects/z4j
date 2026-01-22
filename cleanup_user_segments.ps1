#Requires -Version 5.1

<#
.SYNOPSIS
    This script paginates through all pages of the GET /api/v2/help_center/user_segments endpoint and deletes any user segment that is not built-in.
.DESCRIPTION
    This script will first check for the presence of the following environment variables:
    - ZENDESK_URL: The URL of your Zendesk instance (e.g., https://your-subdomain.zendesk.com)
    - ZENDESK_API_TOKEN: Your Zendesk API token.
    - ZENDESK_ADMIN_EMAIL: The email address of a Zendesk admin.

    If all environment variables are present, it will then:
    1. Paginate through all user segments.
    2. Identify user segments that are not built-in.
    3. Delete each of those user segments.
.NOTES
    Author: Jonathan
    Date: $(Get-Date -Format "yyyy-MM-dd")
#>

param()

#region Environment Variable Validation
$requiredEnvVars = @("ZENDESK_URL", "ZENDESK_API_TOKEN", "ZENDESK_ADMIN_EMAIL")
$missingEnvVars = @()

foreach ($var in $requiredEnvVars) {
    if (-not (Test-Path "env:$var")) {
        $missingEnvVars += $var
    }
}

if ($missingEnvVars.Count -gt 0) {
    Write-Error "The following environment variables are not set: $($missingEnvVars -join ', ')"
    exit 1
}
#endregion

$zendeskUrl = $env:ZENDESK_URL
$apiToken = $env:ZENDESK_API_TOKEN
$adminEmail = $env:ZENDESK_ADMIN_EMAIL

$credentials = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes("${adminEmail}/token:${apiToken}"))
$headers = @{
    "Authorization" = "Basic $credentials"
    "Content-Type"  = "application/json"
}

$nextPage = "$zendeskUrl/api/v2/help_center/user_segments"

do {
    try {
        Write-Host "Fetching user segments from $nextPage"
        $response = Invoke-RestMethod -Uri $nextPage -Method Get -Headers $headers

        if ($null -ne $response.user_segments) {
            foreach ($segment in $response.user_segments) {
                if ($segment.built_in -eq $false) {
                    Write-Host "Deleting user segment: $($segment.name) (ID: $($segment.id))"
                    $deleteUrl = "$zendeskUrl/api/v2/help_center/user_segments/$($segment.id)"
                    try {
                        Invoke-RestMethod -Uri $deleteUrl -Method Delete -Headers $headers
                        Write-Host "Successfully deleted user segment: $($segment.name)"
                    }
                    catch {
                        Write-Error "Error deleting user segment with ID $($segment.id): $_"
                    }
                }
            }
        }

        $nextPage = $response.next_page
    }
    catch {
        Write-Error "Error fetching user segments: $_"
        exit 1
    }
} while ($null -ne $nextPage)

Write-Host "User segment cleanup complete."