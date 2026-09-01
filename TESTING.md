# Testing z4j

This project utilizes extensive integration testing to guarantee compatibility with Zendesk's live sandbox environment. Because the test suite makes genuine HTTP requests, please wait **1 minute** between running individual suites (`./gradlew ticketingTest`, `./gradlew userTest`, etc.) to avoid 429 Too Many Requests errors.

**⚠️ Do Not Run `./gradlew test`**: Running the monolithic `test` task blasts the sandbox with 1,200+ concurrent requests. While the project includes a `RateLimitTestFilter` to intelligently wait out Zendesk API rate limits (HTTP 429), this massive concurrent load will inevitably trigger Zendesk's edge firewall (Cloudflare) DDoS protection. This results in your IP being temporarily blacklisted, causing widespread `403 Forbidden` and `UnknownHostException` test failures. Always use the partitioned domain tasks listed below.

## Coverage Reports

### 1. `clientCoverageReport` (Zendesk API Coverage)
We use a custom task to evaluate how much of the Zendesk API we actually test. It statically analyzes all `@Client` interfaces and Spock specs to generate a role-based coverage matrix located at `src/test/README.md`.

**Current Project Status (API Coverage):**
* **Total Endpoints Declared:** 629
* **Total Endpoints Tested:** 265 (42.1%)
* **Uncovered Endpoints:** 364

To regenerate this report, run:
```bash
./gradlew clientCoverageReport
```

### 2. `jacocoTestReport` (Code Coverage)
Standard JaCoCo code coverage is generated automatically after tests run successfully. You can find the HTML report in `build/reports/jacoco/test/html/index.html`. 

_Note: Due to rate limiting truncating full suite runs, current JaCoCo line coverage sits at an artificially low ~24%, but will reflect accurately when tests run to completion._

## Known Zendesk API Defects

While testing the integration, we've encountered several mismatches between the live Zendesk API behavior and their officially published OpenAPI specifications. We patch these in our generated models locally. 

<details>
<summary><b>Ticket Metrics: <code>showTicketMetricsByTicket</code> returns Object instead of Array</b></summary>
<br>

**Endpoint:** `GET /api/v2/tickets/{ticket_id}/metrics`
**Description:** The Zendesk OpenAPI specification defines the `ticket_metric` response field as a JSON Array (e.g., `List<TicketMetricObject>`). However, the live API actually returns a single JSON Object.
**Impact:** Causes Jackson `CodecException` during strict deserialization (`Unexpected token START_OBJECT, expected START_ARRAY`).
**Resolution:** Manually patched the `ticketMetric` field in `TicketMetricsByTicketMetricIdResponse.java` from `List<@Valid TicketMetricObject>` to `@Valid TicketMetricObject`.
</details>
